package com.surveyapp.web.controllers;

import com.surveyapp.web.domain.frontend.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;

@Controller
public class SurveyController {

    /* The Application Logger */
    private static final Logger LOG = LoggerFactory.getLogger(SurveyController.class);

    @RequestMapping(value = "/savequestion", method = RequestMethod.GET)
    public String saveQuestionGet(ModelMap model) {
        return SurveyorController.CREATE_SURVEY;
    }

    @RequestMapping(value = "/savequestion", method = RequestMethod.POST)
    public String saveQuestionPost(@ModelAttribute Question question, BindingResult bindingResult) {
        System.out.println(question.getQuesText());
        System.out.println(question.getOption1Text());
        System.out.println(question.getOption2Text());
        System.out.println(question.getOption3Text());
        System.out.println(question.getOption4Text());
        return SurveyorController.CREATE_SURVEY;
    }

}
