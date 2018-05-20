

package com.surveyapp.web.controllers;

import com.surveyapp.backend.persistence.domain.backend.SurveyEntity;
import com.surveyapp.backend.service.QuestionService;
import com.surveyapp.backend.service.SurveyService;
import com.surveyapp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SurveyeeController {

    public static final String SURVEYEE_HOME = "surveyee/surveyeeHome";

    public static final String SHOW_SURVEY_RESPONSE = "surveyee/showSurveyResponse";

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/surveyeehome", method = RequestMethod.GET)
    public String getSurveysOfSurveyee(ModelMap model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        int userId = (int) userService.findByUserName(username).getId();



        return SURVEYEE_HOME;
    }

    @RequestMapping(value="/openSurvey", method = RequestMethod.GET)
    public String getSurveyResponse(@RequestParam(value="surveyId") int surveyId,
                            ModelMap m){

        SurveyEntity survey =  surveyService.getSurvey(surveyId);


        System.out.println(survey.getSurveyType());
       if((survey.getSurveyType().equals("general") || (survey.getSurveyType().equals("close") ||
               survey.getSurveyType().equals("open")) )&& survey.getPublished()) {
           List<com.surveyapp.backend.persistence.domain.backend.Question> questions = questionService.getQuestions(survey);
           m.addAttribute("survey", survey);
           m.addAttribute("questions",questions);
//        m.addAttribute("token", token);
           m.addAttribute("surveyId", surveyId);
           return SHOW_SURVEY_RESPONSE;
       }
       else{
           return SHOW_SURVEY_RESPONSE;
       }


    }

}