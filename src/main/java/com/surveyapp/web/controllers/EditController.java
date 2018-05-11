package com.surveyapp.web.controllers;

import com.surveyapp.backend.persistence.domain.backend.Question;
//import com.surveyapp.backend.persistence.domain.backend.Survey;
import com.surveyapp.backend.persistence.domain.backend.SurveyEntity;
import com.surveyapp.backend.persistence.domain.backend.Token;
import com.surveyapp.backend.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EditController {

    public static final String SURVEY_EDIT_PAGE = "surveyor/surveyEditPage";

    @Autowired
    private  SurveyService surveyService;

    @RequestMapping(value="/surveyor/editSurvey", method = RequestMethod.GET)
    public String getSurveyDetails(@RequestParam("description") String surveyName, ModelMap model) {

        SurveyEntity survey = surveyService.getSurveyByName(surveyName);

        model.addAttribute("survey", survey);
        model.addAttribute("questions", survey.getQuestions());
//        String optionsCounter = "";
        int i=1;
        for(Question question: survey.getQuestions()){
//            optionsCounter = "optionsQ"+String.valueOf(i);
            model.addAttribute("optionsQ"+String.valueOf(i),question.getOptions());
            i++;
        }

    return SURVEY_EDIT_PAGE;
    }
}
