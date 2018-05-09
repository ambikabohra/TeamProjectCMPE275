package com.surveyapp.web.controllers;

import com.surveyapp.backend.persistence.domain.backend.Plan;
import com.surveyapp.backend.persistence.domain.backend.Role;
import com.surveyapp.backend.persistence.domain.backend.User;
import com.surveyapp.backend.persistence.domain.backend.UserRole;
import com.surveyapp.backend.persistence.repositories.UserRepository;
import com.surveyapp.backend.service.*;
import com.surveyapp.enums.PlanEnum;
import com.surveyapp.enums.RolesEnum;
import com.surveyapp.exceptions.S3Exception;
import com.surveyapp.utils.UserUtils;
import com.surveyapp.web.domain.frontend.BasicAccountPayload;
import com.surveyapp.web.domain.frontend.ProAccountPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.util.*;


@Controller
public class SignupController {

    @Autowired
    private PlanService planService;

    @Autowired
    private UserService userService;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private I18NService i18NService;

    @Value("${webmaster.email}")
    private String webMasterEmail;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    /**
     * The application logger
     */
    private static final Logger LOG = LoggerFactory.getLogger(SignupController.class);

    public static final String SIGNUP_URL_MAPPING = "/signup";

    public static final String VERIFY_ACCOUNT_URL = "/verifycode";

    public static final String PAYLOAD_MODEL_KEY_NAME = "payload";

    public static final String SUBSCRIPTION_VIEW_NAME = "registration/signup";

    public static final String VERIFICATION_VIEW_NAME = "registration/accountVerificationForm";

    public static final String DUPLICATED_USERNAME_KEY = "duplicatedUsername";

    public static final String DUPLICATED_EMAIL_KEY = "duplicatedEmail";

    public static final String SIGNED_UP_MESSAGE_KEY = "signedUp";

    public static final String ERROR_MESSAGE_KEY = "message";

    public static final String GENERIC_ERROR_VIEW_NAME = "error/genericError";

    public static final String EMAIL_MESSAGE_TEXT_PROPERTY_NAME = "verify.account.email.text";

    @RequestMapping(value = SIGNUP_URL_MAPPING, method = RequestMethod.GET)
    public String signupGet(@RequestParam("planId") int planId, ModelMap model) {

        if (planId != PlanEnum.BASIC.getId() && planId != PlanEnum.PRO.getId()) {
            throw new IllegalArgumentException("Plan id is not valid");
        }
        model.addAttribute(PAYLOAD_MODEL_KEY_NAME, new ProAccountPayload());

        return SUBSCRIPTION_VIEW_NAME;
    }


    @RequestMapping(value = SIGNUP_URL_MAPPING, method = RequestMethod.POST)
    public String signUpPost(HttpServletRequest servletRequest,
                             @RequestParam(name = "planId", required = true) int planId,
                             @RequestParam(name = "file", required = false) MultipartFile file,
                             @ModelAttribute(PAYLOAD_MODEL_KEY_NAME) @Valid ProAccountPayload payload,
                             ModelMap model) throws IOException {

        if (planId != PlanEnum.BASIC.getId() && planId != PlanEnum.PRO.getId()) {
            model.addAttribute(SIGNED_UP_MESSAGE_KEY, "false");
            model.addAttribute(ERROR_MESSAGE_KEY, "Plan id does not exist");
            return SUBSCRIPTION_VIEW_NAME;
        }

        this.checkForDuplicates(payload, model);

        boolean duplicates = false;

        List<String> errorMessages = new ArrayList<>();

        if (model.containsKey(DUPLICATED_USERNAME_KEY)) {
            LOG.warn("The username already exists. Displaying error to the user");
            model.addAttribute(SIGNED_UP_MESSAGE_KEY, "false");
            errorMessages.add("Username already exist");
            duplicates = true;
        }

        if (model.containsKey(DUPLICATED_EMAIL_KEY)) {
            LOG.warn("The email already exists. Displaying error to the user");
            model.addAttribute(SIGNED_UP_MESSAGE_KEY, "false");
            errorMessages.add("Email already exist");
            duplicates = true;
        }

        if (duplicates) {
            model.addAttribute(ERROR_MESSAGE_KEY, errorMessages);
            return SUBSCRIPTION_VIEW_NAME;
        }


        // There are certain info that the user doesn't set, such as profile image URL, Stripe customer id,
        // plans and roles
        LOG.debug("Transforming user payload into User domain object");
        User user = UserUtils.fromWebUserToDomainUser(payload);

        // Stores the profile image on Amazon S3 and stores the URL in the user's record
        if (file != null && !file.isEmpty()) {

            String profileImageUrl = s3Service.storeProfileImage(file, payload.getUsername());
            if (profileImageUrl != null) {
                user.setProfileImageUrl(profileImageUrl);
            } else {
                LOG.warn("There was a problem uploading the profile image to S3. The user's profile will" +
                        " be created without the image");
            }

        }

        // Sets the Plan and the Roles (depending on the chosen plan)
        LOG.debug("Retrieving plan from the database");
        Plan selectedPlan = planService.findPlanById(planId);
        if (null == selectedPlan) {
            LOG.error("The plan id {} could not be found. Throwing exception.", planId);
            model.addAttribute(SIGNED_UP_MESSAGE_KEY, "false");
            model.addAttribute(ERROR_MESSAGE_KEY, "Plan id not found");
            return SUBSCRIPTION_VIEW_NAME;
        }
        user.setPlan(selectedPlan);

        User registeredUser = null;

        //Karan - generate a random string and save it with user
        String code = UUID.randomUUID().toString();
        user.setCode(code);


        // By default users get the BASIC ROLE
        Set<UserRole> roles = new HashSet<>();
        if (planId == PlanEnum.BASIC.getId()) {
            roles.add(new UserRole(user, new Role(RolesEnum.BASIC)));
            registeredUser = userService.createUser(user, PlanEnum.BASIC, roles);
        } else {
            roles.add(new UserRole(user, new Role(RolesEnum.PRO)));
            registeredUser = userService.createUser(user, PlanEnum.PRO, roles);
            LOG.debug(payload.toString());
        }



        /*// Auto logins the registered user
        Authentication auth = new UsernamePasswordAuthenticationToken(
                registeredUser, null, registeredUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);*/

        LOG.info("User created successfully");

        //now send user an email with the code to verify the account
        String emailText = i18NService.getMessage(EMAIL_MESSAGE_TEXT_PROPERTY_NAME, servletRequest.getLocale());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("[Devopsbuddy]: How to Reset Your Password");
        mailMessage.setText(emailText + "\r\n" + code);
        mailMessage.setFrom(webMasterEmail);

        emailService.sendGenericEmailMessage(mailMessage);

        model.addAttribute(SIGNED_UP_MESSAGE_KEY, "true");
        //send the id of the user to assign it in hidden tag and use it to verify on next page
        model.addAttribute("principalId", user.getId());

        return SUBSCRIPTION_VIEW_NAME;
    }

    @RequestMapping(value = VERIFY_ACCOUNT_URL, method = RequestMethod.GET)
    public String verifyAccountGet(@RequestParam(name = "principal_id") long principalId,
                                   ModelMap model) throws Exception {

        LOG.info("Inside get call of verify account. id is {}", principalId);
        model.addAttribute("principalId", principalId);
        return VERIFICATION_VIEW_NAME;
    }


    @RequestMapping(value = VERIFY_ACCOUNT_URL, method = RequestMethod.POST)
    public String verifyAccountPost(@RequestParam(name = "code") String code,
                                    @RequestParam(name = "principal_id") long principalId,
                                    ModelMap model) throws Exception {

        LOG.info("Inside post call of verify account. code recieved is {} and id is {}", code, principalId);

        if (code.isEmpty() || code == null) {
            LOG.info("code is empty");
            throw new Exception("code not found");
        }

        User user = userService.findById(principalId);

        if (user == null) {
            LOG.info("user not found");
            throw new Exception("user not found");
        }

        String userCodeInDb = user.getCode();
        LOG.info("code for user {} is {} ", user.getUsername(), userCodeInDb);

        if (userCodeInDb.isEmpty() || userCodeInDb == null) {
            LOG.info("code for user {} is {} ", user.getUsername(), userCodeInDb);
            throw new Exception("user code not found");
        }

        boolean verified = false;
        if (userCodeInDb.equals(code)) {
            LOG.info("code is verified");
            user.setVerified(true);
            userRepository.save(user);
            verified = true;
        } else {

        }

        model.addAttribute("verified", verified);

        return "user/login";
    }


    @ExceptionHandler({S3Exception.class})
    public ModelAndView signupException(HttpServletRequest request, Exception exception) {

        LOG.error("Request {} raised exception {}", request.getRequestURL(), exception);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("url", request.getRequestURL());
        mav.addObject("timestamp", LocalDate.now(Clock.systemUTC()));
        mav.setViewName(GENERIC_ERROR_VIEW_NAME);
        return mav;
    }


    //--------------> Private methods

    /**
     * Checks if the username/email are duplicates and sets error flags in the model.
     * Side effect: the method might set attributes on Model
     **/
    private void checkForDuplicates(BasicAccountPayload payload, ModelMap model) {

        // Username
        if (userService.findByUserName(payload.getUsername()) != null) {
            model.addAttribute(DUPLICATED_USERNAME_KEY, true);
        }
        if (userService.findByEmail(payload.getEmail()) != null) {
            model.addAttribute(DUPLICATED_EMAIL_KEY, true);
        }

    }
}