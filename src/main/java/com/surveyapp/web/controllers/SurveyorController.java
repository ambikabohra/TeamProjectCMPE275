package com.surveyapp.web.controllers;

import com.surveyapp.web.domain.frontend.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SurveyorController {

    public static final String SURVEYOR_HOME = "surveyor/surveyorHome";

    public static final String QUESTION = "question";

    @RequestMapping(value = "/surveyorhome", method = RequestMethod.GET)
    public String getSurveryorHome(ModelMap model){

        model.addAttribute("role","surveyor");
        return SURVEYOR_HOME;
    }

    public static final String CREATE_SURVEY = "surveyor/createSurvey";
    public static final String SET_SURVEY_DETAILS = "survey/setsurvey";

    @RequestMapping(value = "/createsurvey", method = RequestMethod.GET)
    public String createSurveyGet(ModelMap model){
        model.addAttribute(QUESTION ,new Question());
        model.addAttribute("role","surveyor");
        return SET_SURVEY_DETAILS;
    }

    public static final String SHARE_SURVEY = "surveyor/shareSurvey";

    @RequestMapping(value = "/sharesurvey", method = RequestMethod.GET)
    public String shareSurveyGet(ModelMap model){

        model.addAttribute("role","surveyor");
        return SHARE_SURVEY;
    }

    public static final String CHECK_RESPONSES = "surveyor/surveyResponse";

    @RequestMapping(value = "/surveyresponse", method = RequestMethod.GET)
    public String surveyResponseGet(ModelMap model){

        model.addAttribute("role","surveyor");
        return CHECK_RESPONSES;
    }

    public static final String EDIT_SURVEY = "surveyor/editSurvey";

    @RequestMapping(value = "/editsurvey", method = RequestMethod.GET)
    public String editSurveyGet(ModelMap model){

        model.addAttribute("role","surveyor");
        return EDIT_SURVEY;
    }


}
