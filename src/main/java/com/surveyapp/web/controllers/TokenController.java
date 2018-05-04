package com.surveyapp.web.controllers;


import com.surveyapp.backend.service.*;
import com.surveyapp.backend.persistence.domain.backend.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SurveyService surveyService;



    @RequestMapping(value="/token", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getToken(@RequestParam(value = "sId") int sId){

        Survey survey = surveyService.getSurvey(sId);

        return new ResponseEntity<>(survey.getTokens(), HttpStatus.OK);
    }


    @RequestMapping(value="/token", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createToken(@RequestParam(name = "surveyId") int sId,
                                         @RequestParam(name="emailList", required = false) String[] emailList) {
        Survey survey = surveyService.getSurvey(sId);



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
}
