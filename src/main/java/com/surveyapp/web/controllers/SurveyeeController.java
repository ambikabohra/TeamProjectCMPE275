

package com.surveyapp.web.controllers;

        import com.surveyapp.backend.persistence.domain.backend.Participant;
        import com.surveyapp.backend.persistence.domain.backend.Question;
        import com.surveyapp.backend.persistence.domain.backend.Response;
        import com.surveyapp.backend.persistence.domain.backend.SurveyEntity;
        import com.surveyapp.backend.service.ParticipantService;
        import com.surveyapp.backend.service.QuestionService;
        import com.surveyapp.backend.service.SurveyService;
        import com.surveyapp.web.domain.frontend.EmailAddresses;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
public class SurveyeeController {

    public static final String SURVEYEE_HOME = "surveyee/surveyeeHome";

    public static final String SHOW_SURVEY_RESPONSE = "surveyee/showSurveyResponse";

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/surveyeehome", method = RequestMethod.GET)
    public String getSurveysOfSurveyee(ModelMap model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       // String userName = auth.getName(); //get logged in username

    String currentUserName = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        List<SurveyEntity> surveys = new ArrayList<>();
        System.out.println("name of the participant" + currentUserName);
//        if(currentUserName != null) {
            //list of surveys, a participant has given
            surveys = (surveyService.getListOfGivenSurveyDescriptions(currentUserName));
//            System.out.println("name of the survey" + surveys.get(0));

//        }
        model.addAttribute("surveys", surveys);
        model.addAttribute("error", null);
        model.addAttribute("role","surveyee");
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