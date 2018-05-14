package com.surveyapp.web.controllers;


import com.surveyapp.backend.persistence.repositories.QuestionRepository;
import com.surveyapp.backend.service.*;
import com.surveyapp.backend.persistence.domain.backend.*;

import com.surveyapp.web.domain.frontend.EmailAddresses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

//import javax.transaction.Transactional;

@Controller
public class ResponseController {

    public static final String SURVEYEE_EMAIL_SUBMIT = "survey/verified";

    public static final String SURVEYEE_SURVEY_SUBMIT = "surveyee/submitEmail";

    public static final String EMAIL_MESSAGE_TEXT_PROPERTY_NAME = "surveytoken.email.text";

    @Autowired
    private ResponseService responseService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private ParticipantService participantService;


    @Autowired
    private I18NService i18NService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private QuestionRepository questionRepository;

    @Value("${webmaster.email}")
    private String webMasterEmail;

    @Autowired
    private TokenService tokenService;

   //  Get All responses of the survey
    @RequestMapping(value = "/survey/responses/{sId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
   // @ResponseBody
    public HashMap<String, List<Response>>  getResponses(@PathVariable(value = "sId") int sId) {

        SurveyEntity surveyObj = surveyService.getSurveyObject(sId);

        HashMap<String, List<Response>> hmap = new HashMap<>();
        //keep question , and its answers
        for(Question question: surveyObj.getQuestions()) {
            hmap.put(question.getDescription(), question.getResponses());
        }

        return hmap;
    }

// when user clicks on "submit" button ,  request ex: localhost:8080/survey/{sId}/responses?pId=123answers=aaa,bbrequest ex: localhost:8080/survey/response/{sId}/answer1=""&answer2=""
    @RequestMapping(value="/survey/responses",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createResponse(@RequestParam(value="sId") int sId,  //survey id
                                            @RequestParam(value = "userId") int userId,
                                            @RequestParam(value = "answers") String[] answers){

        try {
            // add answers in questions list of the survey
            System.out.println(sId);
            SurveyEntity surveyObj = surveyService.getSurveyObject(sId);

            System.out.println(surveyObj.getSurveyType());
            //get list of questions
            List<Question> questions = surveyObj.getQuestions();

            //get Participant object by survey Id
            Participant participant = participantService.getParticipant(userId);

           // responseService.saveResponses(questions, answers, user);
            //select each question and update response list
            int i = 0;
            Question question;
            while (i < answers.length) {

                question = questions.get(i); //select each question from questions list of the survey

                //save in response table
               // Response response = responseService.save(new Response(answers[i], question, user));
                Response response= responseService.saveResponse(answers[i],question,participant);

                //add response in question table
                question.getResponses().add(response);

                i++;
            }

            return new ResponseEntity<>("Done", HttpStatus.OK);
        }

        catch (Exception ue)   ///exception for unique constraint exception in mysql
        {
            return new ResponseEntity<>("error occured", HttpStatus.BAD_REQUEST);
        }

    }


    @RequestMapping(value = "/submitSurvey", method = RequestMethod.GET)
        public String submitSurvey( ModelMap model){

//        Token tokenObj = tokenService.findByToken(token);


        Participant participant = participantService.saveParticipant(null,null, true);
//        tokenObj.getParticipants().add(participant);
        Question que = questionRepository.findByqId(5);
        System.out.println(que.getqId());
        Response response= responseService.saveResponse("5",que,participant);
        //save response of the participant
        System.out.println(participant.getpId());
        model.addAttribute("emailaddresses",new EmailAddresses());

        return SURVEYEE_SURVEY_SUBMIT;

        }



    @RequestMapping(value = "/sendEmail", method = RequestMethod.GET)
//    public String submitSurvey(@RequestParam("email")  String email, HttpServletRequest servletRequest, ModelMap model) {
    public String submitSurvey( HttpServletRequest servletRequest, @ModelAttribute EmailAddresses emailaddresses) {
        String email =  emailaddresses.getEmailAddresses();
      sendEmail(servletRequest, email);

      return SURVEYEE_EMAIL_SUBMIT;
    }

    public void sendEmail(HttpServletRequest servletRequest, String email){

        String emailText = i18NService.getMessage( EMAIL_MESSAGE_TEXT_PROPERTY_NAME , servletRequest.getLocale());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject("[Devopsbuddy]: Your response");
            mailMessage.setText(emailText );
            mailMessage.setFrom(webMasterEmail);
            emailService.sendGenericEmailMessage(mailMessage);
    }

//    @GetMapping(value="/response")
//    public String greetingForm(ModelMap model) {
//        model.addAttribute("response", new Response());
//        return "response";
//    }
//
//    @PostMapping(value = "/response")
//    public String submitSurvey(@RequestParam("token")  String token, @ModelAttribute Response response,
//                               BindingResult bindingResult,
//                               Model model){
//
//        Token tokenObj = tokenService.findByToken(token);
//
//        System.out.println(response);
//        Participant participant = participantService.saveParticipant(null,null, true);
//        tokenObj.getParticipants().add(participant);
//
//        //save response of the participant
//        System.out.println(participant.getpId());
//
//
//        return SURVEYEE_SURVEY_SUBMIT;
//
//    }



//    @PostMapping("/submitsurveyresponse")
//    public ResponseEntity<?> getSearchResultViaAjax(
//            @Valid @RequestBody Response response, Errors errors) {
//
//        AjaxResponseBody result = new AjaxResponseBody();
//
//        //If error, just return a 400 bad request, along with the error message
//        if (errors.hasErrors()) {
//
//            result.setMsg(errors.getAllErrors()
//                    .stream().map(x -> x.getDefaultMessage())
//                    .collect(Collectors.joining(",")));
//
//            return ResponseEntity.badRequest().body(result);
//
//        }

}
