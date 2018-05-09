package com.surveyapp.web.controllers;

import com.surveyapp.backend.persistence.domain.backend.Counter;
import com.surveyapp.backend.persistence.domain.backend.Survey;
import com.surveyapp.backend.persistence.domain.backend.Token;
import com.surveyapp.backend.service.SurveyService;
import com.surveyapp.backend.service.UserService;
import com.surveyapp.web.domain.frontend.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SurveyorController {

    public static final String SURVEYOR_HOME = "surveyor/surveyorHome";

    public static final String QUESTION = "question";

    public static final String SHARE_SURVEY = "surveyor/shareSurvey";

    public static final String CHECK_RESPONSES = "surveyor/surveyResponse";

    public static final String EDIT_SURVEY = "surveyor/editSurvey";

    @Autowired
    private UserService userService;

    @Autowired
    private SurveyService surveyService;



    @RequestMapping(value = "/surveyorhome", method = RequestMethod.GET)
    public String getSurveryorHome(ModelMap model){

        model.addAttribute("role","surveyor");
        return SURVEYOR_HOME;
    }

    public static final String CREATE_SURVEY = "surveyor/createSurvey";

    @RequestMapping(value = "/createsurvey", method = RequestMethod.GET)
    public String createSurveyGet(ModelMap model){
        model.addAttribute(QUESTION ,new Question());
        model.addAttribute("role","surveyor");
        return CREATE_SURVEY;
    }

    @RequestMapping(value = "/sharesurvey", method = RequestMethod.GET)
    public String shareSurveyGet(ModelMap model){

        model.addAttribute("role","surveyor");
        return SHARE_SURVEY;
    }




    @RequestMapping(value = "/surveyresponse", method = RequestMethod.GET)
    public String surveyResponseGet(ModelMap model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName(); //get logged in username

        List<String> surveys = surveyService.getListOfSurveyDescriptions(userName);

        model.addAttribute("surveys","surveys");
        model.addAttribute("error", null);
        return CHECK_RESPONSES;
    }

    @RequestMapping(value = "/editsurvey", method = RequestMethod.GET)
    public String editSurveyGet(ModelMap model){
        Authentication auth1 = SecurityContextHolder.getContext().getAuthentication();
        String userName1 = auth1.getName(); //get logged in username

        List<String> surveyList = surveyService.getListOfSurveyDescriptions(userName1);
        System.out.println(surveyList.get(0));

        model.addAttribute("surveys",surveyList);
        return EDIT_SURVEY;
    }

}
