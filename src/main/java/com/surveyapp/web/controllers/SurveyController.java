package com.surveyapp.web.controllers;

import com.surveyapp.backend.persistence.domain.backend.SurveyEntity;
import com.surveyapp.backend.persistence.domain.backend.Token;
import com.surveyapp.backend.persistence.domain.backend.User;
import com.surveyapp.backend.persistence.repositories.TokenRepository;
import com.surveyapp.backend.service.*;
import com.surveyapp.utils.SurveyUtils;
import com.surveyapp.web.domain.frontend.EmailAddresses;
import com.surveyapp.web.domain.frontend.Question;
import com.surveyapp.web.domain.frontend.Survey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
public class SurveyController {


    @Autowired
    private QuestionService questionService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionOptionService questionOptionService;


    private static final String EMAIL_MESSAGE_TEXT_PROPERTY_NAME = "surveytoken.email.text" ;
    public static final String SHOW_SURVEY = "surveyee/showSurvey";


    @Autowired
    private I18NService i18NService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenRepository tokenRepository;


    @Value("${webmaster.email}")
    private String webMasterEmail;
    /* The Application Logger */
    private static final Logger LOG = LoggerFactory.getLogger(SurveyController.class);

    public static final String ADD_QUESTION = "surveyor/createSurvey";


    @RequestMapping(value = "/setsurvey", method = RequestMethod.POST)
    public String setSurveyPost(@ModelAttribute Survey newsurvey, BindingResult bindingResult, ModelMap model) {

        System.out.println(newsurvey.getSurveyName());
        System.out.println(newsurvey.getSurveyType());
        System.out.println(newsurvey.getDays());
        System.out.println(newsurvey.getHours());
        System.out.println(newsurvey.getMinutes());
        System.out.println(newsurvey.getSurveyDesc());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        System.out.println(name);

        User user = userService.findByUserName(name);
        System.out.println(user);

        SurveyEntity surveyEntity = SurveyUtils.webToDomainSurvey(newsurvey, user);

        surveyService.addSurvey(surveyEntity);
        surveyEntity = surveyService.getSurvey(newsurvey.getSurveyName());

        model.addAttribute("question", new Question());
        model.addAttribute("surveyId", surveyEntity.getSurveyId());
        return SurveyorController.CREATE_SURVEY;
    }

    @RequestMapping(value = "/savequestion", method = RequestMethod.POST)
    public String saveQuestionPost(@ModelAttribute Question question, BindingResult bindingResult,
                                   @RequestParam("surveyId") int surveyId,
                                   ModelMap model) {

        System.out.println("surveyId :" + surveyId);
        SurveyEntity survey = surveyService.getSurveyById(surveyId);

        com.surveyapp.backend.persistence.domain.backend.Question question1 = SurveyUtils.webToDomainQues(question, survey);

        questionService.addQuestion(question1);

        SurveyorController.createQuestionOptions(question, question1, questionOptionService);

        model.addAttribute("question", new Question());
        model.addAttribute("surveyId", surveyId);
        return SurveyorController.CREATE_SURVEY;
    }

    @RequestMapping(value = "/publishsurvey", method = RequestMethod.GET)
    public String publishSurveyGet(@RequestParam("surveyId") int surveyId,
                                   ModelMap model) {

        SurveyEntity surveyEntity = surveyService.getSurveyById(surveyId);
        surveyEntity.setPublished(true);
        model.addAttribute("survey", surveyEntity);

        if (surveyEntity.getSurveyType().equals("Open Survey")) {

            return "survey/publishGeneralSurvey";

        } else if (surveyEntity.getSurveyType().equals("Close Survey")) {

            model.addAttribute("surveyId", surveyId);
            model.addAttribute("emailaddressesObject", new EmailAddresses());
            return "survey/publishCloseSurvey";

        } else {
            //generate token
            Token token = tokenRepository.save(new Token(surveyEntity));

//            String token = UUID.randomUUID().toString();

            String urlForSurvey = "http://ec2-52-36-9-6.us-west-2.compute.amazonaws.com:8080/survey?surveyId=" + surveyId +"&token=" + token.getTokenId();
            surveyEntity.setUrl(urlForSurvey);
            surveyService.addSurvey(surveyEntity);

            model.addAttribute("url", urlForSurvey);
            model.addAttribute("surveyId", surveyId);
            return "survey/publishGeneralSurvey";
        }
    }
    @RequestMapping(value = "/closesurveyemails", method = RequestMethod.GET)
    public String closeSurveyEmailsPost(@RequestParam("surveyId") int surveyId,
                                        @ModelAttribute EmailAddresses emailAddresses, HttpServletRequest servletRequest){

        System.out.println("emailAddresses "+emailAddresses.getEmailAddresses());
        List<String> listOfEmails = Arrays.asList(emailAddresses.getEmailAddresses().split("\\s*,\\s*"));

        for (String s:listOfEmails) {
            System.out.println(s);
        }
        SurveyEntity survey = surveyService.getSurveyById(surveyId);


        for(int i = 0; i < listOfEmails.size(); i++){
//            String token = UUID.randomUUID().toString();
            Token token = tokenRepository.save(new Token(survey));
            String tokenURL = "http://ec2-52-36-9-6.us-west-2.compute.amazonaws.com:8080/survey?surveyId="+ surveyId+"&token=" + token.getTokenId();

           /* SurveyEntity surveyEntity = new SurveyEntity();

            surveyEntity.setSurveyName(survey.getSurveyName());
            surveyEntity.setUrl(tokenURL);
            surveyEntity.setPublished(true);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            System.out.println(name);
            User user = userService.findByUserName(name);
            surveyEntity.setUser(user);
            surveyEntity.setSurveyType(survey.getSurveyType());
            surveyEntity.setEndTime(survey.getEndTime());
            surveyEntity.setDescription(survey.getDescription());
            surveyEntity.setQuestions(survey.getQuestions());

            surveyService.addSurvey(surveyEntity);*/

            sendEmail(servletRequest, listOfEmails.get(i), tokenURL);
        }

        return "survey/closeSurveyEmailSent";
    }


    public void sendEmail(HttpServletRequest servletRequest, String email, String tokenURL) {

        String emailText = i18NService.getMessage(EMAIL_MESSAGE_TEXT_PROPERTY_NAME, servletRequest.getLocale());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //for(String email: emailList){
        mailMessage.setTo(email);
        mailMessage.setSubject("[Devopsbuddy]: Click the link to take this survey");
        mailMessage.setText(emailText + "\r\n" + tokenURL);
        mailMessage.setFrom(webMasterEmail);
        emailService.sendGenericEmailMessage(mailMessage);
        //}
    }

    @RequestMapping(value="/survey", method = RequestMethod.GET)
    public String getSurvey(@RequestParam(value="surveyId") int surveyId,
                            @RequestParam(value="token") String token ,
                            ModelMap m){

        SurveyEntity survey =  surveyService.getSurvey(surveyId);

        if(token.isEmpty()){

        }else{

        }

        System.out.println(survey.getSurveyType());
        List<com.surveyapp.backend.persistence.domain.backend.Question> questions = questionService.getQuestions(survey);
        m.addAttribute("survey", survey);
        m.addAttribute("questions",questions);
        m.addAttribute("token", token);
        m.addAttribute("surveyId", surveyId);
        return SHOW_SURVEY;

    }
}
