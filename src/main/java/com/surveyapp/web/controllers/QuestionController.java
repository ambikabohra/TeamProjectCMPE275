package com.surveyapp.web.controllers;

import com.surveyapp.backend.service.*;
import com.surveyapp.backend.persistence.domain.backend.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value="/question/{sId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> questionForm(@PathVariable(value = "sId") int sId,
                                          @RequestParam(value = "questiondesc") String description,
                                          @RequestParam(value = "questionType") String qType,
                                          @RequestParam(value = "options", required = false) String[] options){

        Survey obj = surveyService.getSurvey(sId);
        List<QuestionOption> optionList = new ArrayList<>();
        Question question = questionService.saveQuestion( qType, description, obj, optionList, options);

        return new ResponseEntity<>(question, HttpStatus.OK);
    }

}
