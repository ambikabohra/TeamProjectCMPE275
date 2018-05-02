

package com.surveyapp.web.controllers;

        import org.springframework.stereotype.Controller;
        import org.springframework.ui.ModelMap;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SurveyeeController {

    public static final String SURVEYEE_HOME = "surveyee/surveyeeHome";

    @RequestMapping(value = "/surveyeehome", method = RequestMethod.GET)
    public String getSurveysOfSurveyee(ModelMap model){
        model.addAttribute("role","surveyee");
        return SURVEYEE_HOME;
    }

}