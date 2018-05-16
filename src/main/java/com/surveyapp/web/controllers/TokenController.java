package com.surveyapp.web.controllers;


import java.io.IOException;
import java.util.List;

import com.google.zxing.WriterException;
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
import org.springframework.web.bind.annotation.PathVariable;
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


    private static final String QR_CODE_IMAGE_PATH = "MyQRCode.png";

    @Autowired
    private QRCodeGenerator QRSevice;

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
                    token.getParticipants().add(new Participant(null, email, null, true));
                    }
            }
            else
            {
                //for open survey, no email list
                Token token = tokenService.generateToken(survey);
            }

        return new ResponseEntity<>("Done!!", HttpStatus.OK);
    }

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

    @RequestMapping(value="/generate/QR/{url}", method= RequestMethod.GET)
    public ResponseEntity<byte[]> genearteQR(@PathVariable String url, ModelMap model) {
        //generate QR code
        try {
            byte[] qrcode= QRSevice.getQRCodeImageByteArray(url, 100, 100);
            return new ResponseEntity<byte[]> (qrcode, HttpStatus.CREATED);
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not generate QR Code, IOException :: " + e.getMessage());
        }
        return null;

    }

}
