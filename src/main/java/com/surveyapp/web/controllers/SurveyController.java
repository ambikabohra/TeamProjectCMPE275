package com.surveyapp.web.controllers;

import com.surveyapp.backend.service.*;
import com.surveyapp.backend.persistence.domain.backend.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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

    public static final String SURVEYOR_HOME = "surveyor/createSurvey";

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private UserService userService;

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


    //postman functions

    @RequestMapping(value="/survey/{sId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSurveyDetail(@PathVariable(value = "sId") int sId, Model model) {

       Survey survey =  surveyService.getSurvey(sId);
       model.addAttribute("survey", survey);
       return new ResponseEntity<>(survey, HttpStatus.OK);
    }



    @RequestMapping(value="/survey/{email}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> surveyForm(@PathVariable String email,
                                        @RequestParam(value= "description") String description,
                                        @RequestParam(value = "sType") String surveyType,
                                        @RequestParam(value = "endTime", required = false) String endTime){

        Survey surveyObj = surveyService.createSurvey(email, description, surveyType, endTime);
        return new ResponseEntity<>(surveyObj, HttpStatus.OK);

    }

//    @RequestMapping(value="/survey", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public String saveSurvey( Model model) {
//
//      //  Survey survey =  surveyService.getSurvey(sId);
//        model.addAttribute("survey", new Survey());
//        return "/survey";
//    }

//    @RequestMapping(value="/survey", method=RequestMethod.POST)
//    public String contactSubmit(@ModelAttribute Survey survey, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            //errors processing
//        }
//        model.addAttribute("survey", survey);
//        return "redirect:/";
//    }


//    @PostMapping("/savequestion")
//    public String signUpForm(ModelMap model) {
//   /* System.out.print("@@@@@@@@@@@@"+title);*/
////        Survey survey = surveyService.getSurveyByName(title);
////
////        if(survey != null){
////            model.addAttribute("survey", survey);
////        }
////        else
//        Survey survey = new Survey();
////        model.addAttribute("survey", survey);
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@"+model.get("quesPlaceHolder"));
////        System.out.println(survey.getDescription());
//        return "index";
//    }
//
//    @PostMapping(value = "/survey")
//    public String signUpSubmit(@ModelAttribute("survey") Survey survey,
//                               BindingResult bindingResult,
//                               ModelMap modelMap, RedirectAttributes redirectAttributes)
//    {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String userName = auth.getName(); //get logged in username
//        System.out.println(modelMap.size());
//
//        String email = userService.findByUserName(userName).getEmail();
//
//        Survey obj = surveyService.createSurvey(email, "survey11","general", survey.getEndTime());
//
//        return "redirect:/savesurvey";
//    }


//
//    //Sign up form
//    @GetMapping("/survey")
//    public String signUpForm(Model model) {
//        model.addAttribute("survey", new Survey());
//        return "survey";
//    }
//
//    @PostMapping(value = "/survey")
//    public String signUpSubmit(@ModelAttribute("survey") Survey survey,
//                               BindingResult bindingResult,
//                               ModelMap modelMap)
//    {
////        System.out.println(survey.getDescription() + survey.getQuestions().size());
////        Survey obj = surveyService.createSurvey(email, description, surveyType, endTime);
//        return "verification";
//    }


}



