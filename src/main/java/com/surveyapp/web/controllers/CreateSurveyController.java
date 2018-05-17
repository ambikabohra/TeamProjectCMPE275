package com.surveyapp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CreateSurveyController {

    public static final String CREATE_SURVEY = "survey/createSurvey";

    @RequestMapping(value="/createSurvey" , method = RequestMethod.GET)
    public String createSurvey(ModelMap model){
        model.addAttribute("role","surveyor");
        return CREATE_SURVEY;
    }

}
