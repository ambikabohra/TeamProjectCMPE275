package com.surveyapp.web.controllers;

import com.surveyapp.backend.service.*;
import com.surveyapp.backend.persistence.domain.backend.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@RestController
public class SurveyController{

    @Autowired
    private SurveyService surveyService;


    @RequestMapping(value="/survey/{sId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSurveyDetail(@PathVariable(value = "sId") int sId, Model model) {

       Survey survey =  surveyService.getSurvey(sId);
       model.addAttribute("survey", survey);
       return new ResponseEntity<>(survey, HttpStatus.OK);
    }



    @RequestMapping(value="/survey/{email}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> surveyForm(@PathVariable String email,
                                        @RequestParam(value= "description") String description,
                                        @RequestParam(value = "sType") String surveyType,
                                        @RequestParam(value = "endTime", required = false) String endTime, ModelMap modelmap){

        Survey surveyObj = surveyService.createSurvey(email, description, surveyType, endTime);
        return new ResponseEntity<>(surveyObj, HttpStatus.OK);

    }
}


