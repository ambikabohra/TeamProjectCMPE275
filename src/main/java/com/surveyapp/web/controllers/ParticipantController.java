package com.surveyapp.web.controllers;


import com.surveyapp.backend.service.*;
import com.surveyapp.backend.persistence.domain.backend.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;


    /**
     * get a list of surveys of a registered user ( email , password are required)
     * localhost:8080/user?email=a@gmail.com&password=1234
     */
    @RequestMapping(value="/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchUsers(@RequestParam(value = "email") String email,
                                         @RequestParam(value = "password") String password,
                                         Model model){


        Participant participant = participantService.getParticipantByEmail(email);

        Set<Token> tokenSet = participant.getTokens(); // get a set of tokens mapped to user

        List<SurveyEntity> surveys= new ArrayList<>();

        for(Token token:tokenSet){
            surveys.add(token.getSurvey());  //make a survey lists tagged to that token
        }

        return new ResponseEntity<>(surveys, HttpStatus.OK);

    }

    /**
     * post method to store user
     * with response list = null
     * localhost:8080/user?email=a@gmail.com&password=1234
     */

    @RequestMapping(value="/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)   //email=a@gmail.com&pswd=1234)
    public ResponseEntity<?> createUser(@RequestParam(value = "email", required = false) String email,
                                        @RequestParam(value = "password", required = false) String password,
                                        ModelMap model){

            //SAVE user in partcipant table
            Participant participant = participantService.saveParticipant(null, email,password,true );
//                                                 map.addAttribute("participant " + participant);
            return new ResponseEntity<>(participant, HttpStatus.OK);
    }


}
