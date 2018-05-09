package com.surveyapp.web.controllers;


import com.surveyapp.backend.persistence.domain.backend.Counter;
import com.surveyapp.backend.persistence.domain.backend.Survey;
import com.surveyapp.backend.persistence.domain.backend.Token;
import com.surveyapp.backend.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StatController {
    public static final String SURVEYOR_RESPONSE = "surveyor/surveyResponse";

    public static final String SURVEYOR_STATS = "surveyor/stats";


    @Autowired
    private SurveyService surveyService;

    @RequestMapping(value="/surveyor/stats", method = RequestMethod.GET)
    public String getSurveyDetails(@RequestParam("description") String surveyName, ModelMap model){
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("error","Empty Survey, No Statistics to show");
//            return SURVEYOR_RESPONSE;
//        }

        Survey survey = surveyService.getSurveyByName(surveyName);

        //get participant count
        int count =0;
        List<Token> tokens = survey.getTokens();
        String type = survey.getSurveyType();
        List<com.surveyapp.backend.persistence.domain.backend.Question> questions = survey.getQuestions();

        if(survey.getQuestions().get(0).getResponses().size() != 0) {

            if (type.equals("general") || type.equals("open")) {

                System.out.println("@@@@@@@@@@@@@@" + tokens.get(0).getTokenId());
                Token token = tokens.get(0);
                count = token.getParticipants().size(); // count of participant

            } else if (type == "closed") {
                count = tokens.size();
            }

            int submissionCount = questions.get(0).getResponses().size();
            // get the size of the list of responses of one question

            double participantRate = submissionCount * 1.0 / count;

            model.addAttribute("stats", survey);
            model.addAttribute("counter", new Counter());  // for option counter
            return SURVEYOR_STATS;
        }
        else {
//            model.addAttribute("stats", survey);
            model.addAttribute("error", "empty survey");
            return SURVEYOR_STATS;
        }
    }

    @RequestMapping(value="/stats", method = RequestMethod.GET)
    public String getQuestionResponses(@RequestParam("surveyId") String sId, ModelMap model) {

        Survey survey = surveyService.getSurvey(Integer.valueOf(sId));
        List<com.surveyapp.backend.persistence.domain.backend.Question> questions = survey.getQuestions();

        model.addAttribute("questions", questions);

        return SURVEYOR_STATS;
    }


    @RequestMapping(value = "/surveyorresponse", method = RequestMethod.GET)
    public String getSurveyor( ModelMap model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName(); //get logged in username
        List<String> surveys = surveyService.getListOfSurveyDescriptions(userName);

        model.addAttribute("surveys", surveys);
        return SURVEYOR_RESPONSE;
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
