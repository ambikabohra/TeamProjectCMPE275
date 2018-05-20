package com.surveyapp.web.controllers;

import com.surveyapp.backend.persistence.domain.backend.Question;
import com.surveyapp.backend.persistence.domain.backend.QuestionOption;
import com.surveyapp.backend.persistence.domain.backend.SurveyEntity;
import com.surveyapp.backend.persistence.domain.backend.User;
import com.surveyapp.backend.service.QuestionOptionService;
import com.surveyapp.backend.service.QuestionService;
import com.surveyapp.backend.service.SurveyService;
import com.surveyapp.backend.service.UserService;
import com.surveyapp.web.domain.frontend.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @Autowired
    private QuestionOptionService questionOptionService;

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

    static void createQuestionOptions(@ModelAttribute com.surveyapp.web.domain.frontend.Question question, Question question1, QuestionOptionService questionOptionService) {
        if (question.getQuesType().equals("checkbox") || question.getQuesType().equals("radio") || question.getQuesType().equals("dropdown")) {

            QuestionOption option1 = new QuestionOption(question.getOption1Text(), question1);
            QuestionOption option2 = new QuestionOption(question.getOption2Text(), question1);
            QuestionOption option3 = new QuestionOption(question.getOption3Text(), question1);
            QuestionOption option4 = new QuestionOption(question.getOption4Text(), question1);

            questionOptionService.addQuestionOption(option1);
            questionOptionService.addQuestionOption(option2);
            questionOptionService.addQuestionOption(option3);
            questionOptionService.addQuestionOption(option4);

        }
    }

    @RequestMapping(value = "/deleteQuestion", method = RequestMethod.GET)
    public String deleteQuestionGet(@RequestParam("surveyId") String surveyId,
                                    @RequestParam("questionId") String questionId,
                                    ModelMap modelMap) {

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
                modelMap.addAttribute("questionObj", new com.surveyapp.web.domain.frontend.Question());
            }
        }
    }

    @RequestMapping(value = "/updatequestion", method = RequestMethod.POST)
    public String saveQuestionPost(@ModelAttribute com.surveyapp.web.domain.frontend.Question question, BindingResult bindingResult,
                                   @RequestParam("surveyId") int surveyId,
                                   @RequestParam("questionId") int questionId,
                                   ModelMap model) {

        System.out.println("surveyId :" + surveyId);
        System.out.println("questionId :" + questionId);

        System.out.println(question.getQuesText());
        System.out.println(question.getQuesType());
        System.out.println(question.getOption1Text());
        System.out.println(question.getOption2Text());
        System.out.println(question.getOption3Text());
        System.out.println(question.getOption4Text());

        SurveyEntity survey = surveyService.getSurveyById(surveyId);

        System.out.println(survey.toString());

        Question question1 = questionService.getQuestionById(questionId);

        question1.setDescription(question.getQuesText());


        if (question1.getqType().equals("checkbox") || question1.getqType().equals("radio") || question1.getqType().equals("dropdown")) {

            List<QuestionOption> questionOptionList = question1.getOptions();

            questionOptionList.get(0).setOptionValue(question.getOption1Text());
            questionOptionList.get(1).setOptionValue(question.getOption2Text());
            questionOptionList.get(2).setOptionValue(question.getOption3Text());
            questionOptionList.get(3).setOptionValue(question.getOption4Text());


        }

        questionService.addQuestion(question1);

        model.addAttribute("question", new com.surveyapp.web.domain.frontend.Question());
        model.addAttribute("surveyId", surveyId);

        getSurveyDetails(model, String.valueOf(surveyId));

        return SurveyorController.EDIT_SAVED_SURVEY;
    }

}
