package com.surveyapp.web.controllers;


import java.util.List;

import com.surveyapp.backend.service.*;
import com.surveyapp.backend.persistence.domain.backend.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;


@Controller
public class TokenController {
    public static final String SURVEYOR_HOME = "surveyor/surveyorHome";
    private static final Logger LOG = LoggerFactory.getLogger(ForgotMyPasswordController.class);
    public static final String EMAIL_MESSAGE_TEXT_PROPERTY_NAME = "surveytoken.email.text";
    public static final String TOKEN_VIEW_NAME =  "surveyor/tokenDisplay";

    @Value("${webmaster.email}")
    private String webMasterEmail;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private I18NService i18NService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value="/token", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getToken(@RequestParam(value = "sId") int sId){

        SurveyEntity survey = surveyService.getSurveyObject(sId);

        return new ResponseEntity<>(survey.getTokens(), HttpStatus.OK);
    }


    @RequestMapping(value="/token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createToken(@RequestParam(name = "surveyId") int sId,
                                         @RequestParam(name="emailList", required = false) String[] emailList) {
        SurveyEntity survey = surveyService.getSurveyObject(sId);



        String type = survey.getSurveyType();

        //check survey type
        if (type.equals("closed"))
            {
                /**
                 * for closed survey,  email list will be passed
                 * add new token for each email id
                 */
                for (String email : emailList) {
                    Token token = tokenService.generateToken(survey);
                    token.getParticipants().add(new Participant(email, null, true));
                    }
            }
            else
            {
                //for open survey, no email list
                Token token = tokenService.generateToken(survey);
            }

        return new ResponseEntity<>("Done!!", HttpStatus.OK);
    }

//    @RequestMapping(value="/publishsurvey", method=RequestMethod.GET)
//    public String publishSurvey(HttpServletRequest servletRequest, ModelMap model){
//        // save survey
//        // get survey id
//        // create a token
//
//
//        model.addAttribute("survey", new SurveyEntity());
//
////        SurveyEntity surveyObj= surveyService.createSurvey("lavanya.k9110@gmail.com",null,"general", null);
//
////        int surveyId = surveyObj.getSurveyId();
////        String type = surveyObj.getSurveyType();
////
////        String newToken = null;
////        //check survey type
////        if (type.equals("closed"))
////        {
////            /**
////             * for closed survey,  email list will be passed
////             * add new token for each email id
////             */
//////            for (String email : emailList) {
//////                Token token = tokenService.generateToken(survey);
//////                token.getParticipants().add(new Participant(email, null, true));
//////            }
////        }
////        else
////        {
////            //for open survey, no email list
////            Token token = tokenService.generateToken(surveyObj);
////            newToken = token.getTokenId();
////        }
////
////        String tokenURL = "localhost:8080/takesurvey?surveyId="+surveyId+"&token="+newToken;
////        System.out.println(tokenURL);
////        LOG.debug("Generated token url: {}", tokenURL);
//////
//////        if(type.equals("closed")) {
////            List<String> emailList = new ArrayList<>();
////            emailList.add("lavanya.k9110@gmail.com");
////            emailList.add("mb.bohra15@gmail.com");
////
////
////             sendEmail(servletRequest, emailList, tokenURL);
//////        }
////        model.addAttribute("tokenURL", tokenURL);
//
//        return TOKEN_VIEW_NAME;
//    }


    public void sendEmail(HttpServletRequest servletRequest, List<String> emailList, String tokenURL){

        String emailText = i18NService.getMessage( EMAIL_MESSAGE_TEXT_PROPERTY_NAME , servletRequest.getLocale());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        for(String email: emailList){
            mailMessage.setTo(email);
            mailMessage.setSubject("[Devopsbuddy]: Click the link to take this survey");
            mailMessage.setText(emailText + "\r\n" + tokenURL);
            mailMessage.setFrom(webMasterEmail);
            emailService.sendGenericEmailMessage(mailMessage);
        }




    }

}
