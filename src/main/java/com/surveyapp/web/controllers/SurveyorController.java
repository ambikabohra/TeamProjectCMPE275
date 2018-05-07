package com.surveyapp.web.controllers;

import com.surveyapp.backend.persistence.domain.backend.Token;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.surveyapp.backend.persistence.domain.backend.*;
import com.surveyapp.web.domain.frontend.ProAccountPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.surveyapp.backend.service.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SurveyorController {

    public static final String SURVEYOR_HOME = "surveyor/surveyorHome";
    public static final String SURVEYOR_STATS = "surveyor/stats";
//    public static final String QUESTION_STATS = "surveyorveyor/questionStats";

    @Autowired
    private UserService userService;

    @Autowired
    private SurveyService surveyService;

    @RequestMapping(value="/surveyor/stats", method = RequestMethod.GET)
    public String getSurveyDetails(@RequestParam("description") String surveyName, ModelMap model){
        Survey survey = surveyService.getSurveyByName(surveyName);

        System.out.println("@@@@@@@@@@@@@@"+survey.getSurveyType());

        //get participant count
        int count =0;
        List<Token> tokens = survey.getTokens();
        String type = survey.getSurveyType();
        List<Question> questions = survey.getQuestions();

        if(type.equals("general") || type.equals("gpen")) {

            System.out.println("@@@@@@@@@@@@@@"+ tokens.get(0).getTokenId());
            Token token = tokens.get(0);
            count = token.getParticipants().size(); // count of participant

        }
        else if(type == "closed"){
            count = tokens.size();
        }

        int submissionCount = questions.get(0).getResponses().size();
        // get the size of the list of responses of one question
        System.out.println("@@@@@@@@@@@@@@"+ count + " "+ submissionCount);
        double participantRate = submissionCount* 1.0 / count;

//        List<String> stats = new ArrayList<>();
//        stats.add(String.valueOf(survey.getSurveyId()));
//        stats.add("");
//        stats.add(survey.getEndTime());     //add end time
//        stats.add( String.valueOf(count));  //add participant count
//        stats.add( String.valueOf(participantRate)); // add participation rate

        model.addAttribute("stats", survey);
        return SURVEYOR_STATS;
    }

    @RequestMapping(value="/stats", method = RequestMethod.GET)
    public String getQuestionResponses(@RequestParam("surveyId") String sId, ModelMap model) {

        Survey survey = surveyService.getSurvey(Integer.valueOf(sId));
        List<Question> questions = survey.getQuestions();

        model.addAttribute("questions", questions);

        return SURVEYOR_STATS;

    }


        @RequestMapping(value = "/surveyorhome", method = RequestMethod.GET)
    public String getSurveryorHome( ModelMap model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName(); //get logged in username
        List<String> surveys = getSurveyDescription(userName);

        model.addAttribute("surveys", surveys);
        return SURVEYOR_HOME;
    }

    public List<String> getSurveyDescription(String userName){

        List<Survey> surveyList = userService.findByUserName(userName).getSurveys();
        List<String> surveys = new ArrayList<>();
        for(Survey survey: surveyList)
            surveys.add(survey.getDescription());
        return surveys;
    }

//    @RequestMapping(value = "/stats", method = RequestMethod.GET)
//    public String getStats( ModelMap model){
//
//        List<String> surveys = getSurveyDescription(userName);
//        model.addAttribute("surveys", surveys);
//        // model.addAttribute("role","surveyor");
//        // model.addAttribute(PAYLOAD_MODEL_KEY_NAME, new ProAccountPayload());
//        return SURVEYOR_STATS;
//    }

}
