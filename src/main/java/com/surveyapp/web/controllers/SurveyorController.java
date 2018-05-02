package com.surveyapp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SurveyorController {

    public static final String SURVEYOR_HOME = "surveyor/surveyorHome";

    @RequestMapping(value = "/surveyorhome", method = RequestMethod.GET)
    public String getSurveryorHome(ModelMap model){
        model.addAttribute("role","surveyor");
        return SURVEYOR_HOME;
    }

}
