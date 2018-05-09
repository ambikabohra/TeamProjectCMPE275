package com.surveyapp.web.controllers;

import com.surveyapp.backend.persistence.domain.backend.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.surveyapp.backend.persistence.domain.*;
import com.surveyapp.backend.service.*;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class SurveyorController {


    public static final String SURVEYOR_HOME = "surveyor/surveyorHome";
    public static final String SURVEYOR_RESPONSE = "surveyor/surveyResponse";

    public static final String SURVEYOR_STATS = "surveyor/stats";

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

        model.addAttribute("role","surveyor");
        return CREATE_SURVEY;
    }

    public static final String SHARE_SURVEY = "surveyor/shareSurvey";

    @RequestMapping(value = "/sharesurvey", method = RequestMethod.GET)
    public String shareSurveyGet(ModelMap model){

        model.addAttribute("role","surveyor");
        return SHARE_SURVEY;
    }

    public static final String CHECK_RESPONSES = "surveyor/surveyResponse";

    @RequestMapping(value = "/surveyresponse", method = RequestMethod.GET)
    public String surveyResponseGet(ModelMap model){


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName(); //get logged in username
        List<String> surveys = getSurveyDescription(userName);
//
//        model.addAttribute("surveys", surveys);
//        return SURVEYOR_HOME;

        model.addAttribute("surveys","surveys");
        return CHECK_RESPONSES;
    }

    public static final String EDIT_SURVEY = "surveyor/editSurvey";

    @RequestMapping(value = "/editsurvey", method = RequestMethod.GET)
    public String editSurveyGet(ModelMap model){

        model.addAttribute("role","surveyor");
        return EDIT_SURVEY;
    }


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
//        stats.add(survey.getDescription());
//        stats.add(survey.getSurveyType());
//        stats.add(String.valueOf(survey.getSurveyId())); //add surveyId
//        stats.add(String.valueOf(survey.getCurrent())); //add start date
//        stats.add(survey.getEndTime());     //add end time
//        stats.add( String.valueOf(count));  //add participant count
//        stats.add(String.valueOf(submissionCount));
//        stats.add( String.valueOf(participantRate)); // add participation rate
//        HashMap<Question, List<QuestionOption>> hmap = new HashMap<>();
        model.addAttribute("stats", survey);
        model.addAttribute("counter",new Counter());  // for option counter
        return SURVEYOR_STATS;
    }

    @RequestMapping(value="/stats", method = RequestMethod.GET)
    public String getQuestionResponses(@RequestParam("surveyId") String sId, ModelMap model) {

        Survey survey = surveyService.getSurvey(Integer.valueOf(sId));
        List<Question> questions = survey.getQuestions();

        model.addAttribute("questions", questions);

        return SURVEYOR_STATS;

    }


    @RequestMapping(value = "/surveyorresponse", method = RequestMethod.GET)
    public String getSurveyor( ModelMap model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName(); //get logged in username
        List<String> surveys = getSurveyDescription(userName);

        model.addAttribute("surveys", surveys);
        return SURVEYOR_RESPONSE;
    }
//
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
