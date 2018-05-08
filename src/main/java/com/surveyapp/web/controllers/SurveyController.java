package com.surveyapp.web.controllers;

import com.surveyapp.backend.service.QuestionService;
import com.surveyapp.utils.SurveyUtils;
import com.surveyapp.web.domain.frontend.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SurveyController {


    @Autowired
    private QuestionService questionService;

    /* The Application Logger */
    private static final Logger LOG = LoggerFactory.getLogger(SurveyController.class);

    @RequestMapping(value = "/savequestion", method = RequestMethod.GET)
    public String saveQuestionGet(ModelMap model) {
        return SurveyorController.CREATE_SURVEY;
    }

    @RequestMapping(value = "/savequestion", method = RequestMethod.POST)
    public String saveQuestionPost(@ModelAttribute Question question, BindingResult bindingResult) {

        if(question.getQuesType().equals("checkbox")){
            com.surveyapp.backend.persistence.domain.backend.Question question1 = SurveyUtils.webToDomainQues(question);
            questionService.addQuestion(question1);
        }
        return SurveyorController.CREATE_SURVEY;
    }

}
