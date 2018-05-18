package com.surveyapp.web.controllers;

import com.surveyapp.backend.persistence.domain.backend.Question;
import com.surveyapp.backend.persistence.domain.backend.SurveyEntity;
import com.surveyapp.backend.persistence.domain.backend.User;
import com.surveyapp.backend.service.QuestionService;
import com.surveyapp.backend.service.SurveyService;
import com.surveyapp.backend.service.UserService;
import com.surveyapp.web.domain.frontend.Survey;
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
    public static final String CHECK_RESPONSES = "surveyor/surveyResponse";
    public static final String VIEW_SAVED_SURVEY = "surveyor/viewSavedSurveys";
    public static final String EDIT_SAVED_SURVEY = "surveyor/editSavedSurvey";


    @Autowired
    private SurveyService surveyService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/surveyorhome", method = RequestMethod.GET)
    public String getSurveryorHome(ModelMap model) {

        model.addAttribute("role", "surveyor");
        return SURVEYOR_HOME;
    }

    public static final String CREATE_SURVEY = "surveyor/createSurvey";
    public static final String SET_SURVEY_DETAILS = "survey/setsurvey";

    @RequestMapping(value = "/setsurvey", method = RequestMethod.GET)
    public String createSurveyGet(ModelMap model) {
        model.addAttribute("newsurvey", new Survey());
        return SET_SURVEY_DETAILS;
    }

    public static final String SHARE_SURVEY = "surveyor/shareSurvey";

    @RequestMapping(value = "/sharesurvey", method = RequestMethod.GET)
    public String shareSurveyGet(ModelMap model) {

        model.addAttribute("role", "surveyor");
        return SHARE_SURVEY;
    }


    @RequestMapping(value = "/surveyresponse", method = RequestMethod.GET)
    public String surveyResponseGet(ModelMap model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName(); //get logged in username

        List<String> surveys = surveyService.getListOfSurveyDescriptions(userName);

        model.addAttribute("surveys", "surveys");
        model.addAttribute("error", null);
        return CHECK_RESPONSES;
    }

    @RequestMapping(value = "/editsurvey", method = RequestMethod.GET)
    public String editSurveyGet(ModelMap model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName(); //get logged in username

        User user = userService.findByUserName(userName);

        List<SurveyEntity> surveyEntityList = new ArrayList<>();

        surveyEntityList = surveyService.getSurveyByUser(user);

        for (SurveyEntity s : surveyEntityList) {
            System.out.println(s.getSurveyName());
        }

        model.addAttribute("surveyList", surveyEntityList);

        return VIEW_SAVED_SURVEY;
    }


    @RequestMapping(value = "/editSavedSurvey", method = RequestMethod.GET)
    public String editSavedSurveyGet(@RequestParam("surveyId") String surveyId,
                                     ModelMap modelMap) {

        getSurveyDetails(modelMap, surveyId);

        return EDIT_SAVED_SURVEY;
    }

    @RequestMapping(value = "/deleteQuestion", method = RequestMethod.GET)
    public String deleteQuestionGet(@RequestParam("surveyId") String surveyId,
                                    @RequestParam("questionId") String questionId,
                                    ModelMap modelMap){

        questionService.deleteQuestion(Integer.parseInt(questionId));

        getSurveyDetails(modelMap, surveyId);

        return EDIT_SAVED_SURVEY;
    }

    private void getSurveyDetails(ModelMap modelMap, String surveyId) {

        SurveyEntity survey = surveyService.getSurveyById(Integer.parseInt(surveyId));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName(); //get logged in username

        User user = userService.findByUserName(userName);

        if (survey == null) {
            modelMap.addAttribute("status", "surveyNotFound");
        } else {
            List<Question> questionList = survey.getQuestions();

            if (questionList.size() == 0) {
                modelMap.addAttribute("status", "questionsNotFound");
            } else {
                for (Question question : questionList) {
                    if (question.getqType().equals("checkbox") || question.getqType().equals("radio") || question.getqType().equals("dropdown")) {

                    }
                }

                modelMap.addAttribute("user", user);
                modelMap.addAttribute("survey", survey);
                modelMap.addAttribute("questionList", questionList);
                modelMap.addAttribute("question", new com.surveyapp.web.domain.frontend.Question());
            }
        }
    }

}
