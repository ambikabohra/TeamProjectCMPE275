package com.surveyapp.web.controllers;

import com.surveyapp.backend.persistence.domain.backend.Question;
import com.surveyapp.backend.persistence.domain.backend.QuestionOption;
//import com.surveyapp.backend.persistence.domain.backend.Survey;
import com.surveyapp.backend.persistence.domain.backend.SurveyEntity;
import com.surveyapp.backend.service.OptionService;
import com.surveyapp.backend.service.QuestionService;
import com.surveyapp.backend.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class OpenSurveyController {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private OptionService optionService;

    public static final String SHOW_SURVEY = "surveyee/showSurvey";

//    @RequestMapping(value="/survey/{sId}", method = RequestMethod.GET)
//    public String getSurvey(@PathVariable int sId, Model m){
//
//        //To populate HTML, we need survey name,
//        Survey survey =  surveyService.getSurvey(sId);
//        List<Question> questions = questionService.getQuestions(survey);
//        m.addAttribute("survey", survey);
//        m.addAttribute("questions",questions);
//        return SHOW_SURVEY;
//
//
////        //To populate HTML, we need survey name,
////        Survey survey =  surveyService.getSurvey(sId);
////        m.addAttribute("survey", survey);
////        List<Question> questions = questionService.getQuestions(survey);
////        for (Question question : questions) {
////            List<QuestionOption> options = optionService.getOptions(question);
////            m.addAttribute("question",question);
////            m.addAttribute("options",options);
////        }
//    }

    @RequestMapping(value="/takesurvey", method = RequestMethod.GET)
    public String getSurvey(@RequestParam(value="surveyId") int surveyId,
                        @RequestParam(value = "token" ) String token,
                            Locale locale,
                            ModelMap m){

        //To populate HTML, we need survey name,


        SurveyEntity survey =  surveyService.getSurveyObject(surveyId);

        System.out.println(survey.getSurveyType());
        List<Question> questions = questionService.getQuestions(survey);
        m.addAttribute("survey", survey);
        m.addAttribute("questions",questions);
        m.addAttribute("token",token);
        return SHOW_SURVEY;


//        //To populate HTML, we need survey name,
//        Survey survey =  surveyService.getSurvey(sId);
//        m.addAttribute("survey", survey);
//        List<Question> questions = questionService.getQuestions(survey);
//        for (Question question : questions) {
//            List<QuestionOption> options = optionService.getOptions(question);
//            m.addAttribute("question",question);
//            m.addAttribute("options",options);
//        }
    }
}
