package com.surveyapp.web.controllers;

import com.surveyapp.backend.persistence.domain.backend.QuestionOption;
import com.surveyapp.backend.persistence.domain.backend.SurveyEntity;
import com.surveyapp.backend.persistence.domain.backend.User;
import com.surveyapp.backend.service.QuestionOptionService;
import com.surveyapp.backend.service.QuestionService;
import com.surveyapp.backend.service.SurveyService;
import com.surveyapp.backend.service.UserService;
import com.surveyapp.utils.SurveyUtils;
import com.surveyapp.web.domain.frontend.EmailAddresses;
import com.surveyapp.web.domain.frontend.Question;
import com.surveyapp.web.domain.frontend.Survey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class SurveyController {


    @Autowired
    private QuestionService questionService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionOptionService questionOptionService;
/*

    @Autowired
    private TokenService tokenService;
*/

    /* The Application Logger */
    private static final Logger LOG = LoggerFactory.getLogger(SurveyController.class);

    public static final String ADD_QUESTION = "surveyor/createSurvey";

    @RequestMapping(value = "/setsurvey", method = RequestMethod.POST)
    public String setSurveyPost(@ModelAttribute Survey newsurvey, BindingResult bindingResult, ModelMap model) {

        System.out.println(newsurvey.getSurveyName());
        System.out.println(newsurvey.getSurveyType());
        System.out.println(newsurvey.getDays());
        System.out.println(newsurvey.getHours());
        System.out.println(newsurvey.getMinutes());
        System.out.println(newsurvey.getSurveyDesc());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        System.out.println(name);

        User user = userService.findByUserName(name);
        System.out.println(user);

        SurveyEntity surveyEntity = SurveyUtils.webToDomainSurvey(newsurvey, user);

        surveyService.addSurvey(surveyEntity);
        surveyEntity = surveyService.getSurvey(newsurvey.getSurveyName());

        model.addAttribute("question", new Question());
        model.addAttribute("surveyId", surveyEntity.getSurveyId());
        return SurveyorController.CREATE_SURVEY;
    }

    @RequestMapping(value = "/savequestion", method = RequestMethod.POST)
    public String saveQuestionPost(@ModelAttribute Question question, BindingResult bindingResult,
                                   @RequestParam("surveyId") int surveyId,
                                   ModelMap model) {

        System.out.println("surveyId :" + surveyId);
        SurveyEntity survey = surveyService.getSurveyById(surveyId);

        com.surveyapp.backend.persistence.domain.backend.Question question1 = SurveyUtils.webToDomainQues(question, survey);

        questionService.addQuestion(question1);

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

        model.addAttribute("question", new Question());
        model.addAttribute("surveyId", surveyId);
        return SurveyorController.CREATE_SURVEY;
    }

    @RequestMapping(value = "/publishsurvey", method = RequestMethod.GET)
    public String publishSurveyGet(@RequestParam("surveyId") int surveyId,
                                   ModelMap model) {

        SurveyEntity surveyEntity = surveyService.getSurveyById(surveyId);
        surveyEntity.setPublished(true);
        model.addAttribute("survey", surveyEntity);

        if (surveyEntity.getSurveyType().equals("Open Survey")) {

            return "survey/publishGeneralSurvey";

        } else if (surveyEntity.getSurveyType().equals("Close Survey")) {

            model.addAttribute("surveyId", surveyId);
            model.addAttribute("emailaddressesObject", new EmailAddresses());
            return "survey/publishCloseSurvey";

        } else {
            String urlForSurvey = "http://localhost:8080/survey?surveyId=" + surveyId;
            surveyEntity.setUrl(urlForSurvey);
            surveyService.addSurvey(surveyEntity);
            model.addAttribute("url", urlForSurvey);
            model.addAttribute("surveyId", surveyId);
            return "survey/publishGeneralSurvey";
        }
    }

    @RequestMapping(value = "/closesurveyemails", method = RequestMethod.POST)
    public String closeSurveyEmailsPost(@ModelAttribute EmailAddresses emailAddresses, HttpServletRequest servletRequest, ModelMap model){


        List<String> listOfEmails = Arrays.asList(emailAddresses.getEmailAddresses().split("\\s*,\\s*"));

        for (String s:listOfEmails) {
            System.out.println(s);
        }

        /*SurveyEntity surveyObj = surveyService.getSurveyById(surveyId);

        int sId = surveyObj.getSurveyId();
        String type = surveyObj.getSurveyType();

        String newToken = null;
        if (type.equals("Close Survey"))
        {
            *//**
             * for closed survey,  email list will be passed
             * add new token for each email id
             *//*
           for (String email : listOfEmails) {
                Token token = tokenService.generateToken(surveyObj);
                token.getParticipants().add(new Participant(email, null, true));
            }
        }
        else
        {
            //for open survey, no email list
            Token token = tokenService.generateToken(surveyObj);
            newToken = token.getTokenId();
        }*/

        return "survey/closeSurveyEmailSent";
    }

}
