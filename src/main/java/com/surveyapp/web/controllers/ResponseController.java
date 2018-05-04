package com.surveyapp.web.controllers;


import com.surveyapp.backend.service.*;
import com.surveyapp.backend.persistence.domain.backend.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

//import javax.transaction.Transactional;

@RestController
public class ResponseController {

    @Autowired
    private ResponseService responseService;

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private ParticipantService participantService;


   //  Get All responses of the survey
    @RequestMapping(value = "/survey/responses/{sId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
   // @ResponseBody
    public HashMap<String, List<Response>>  getResponses(@PathVariable(value = "sId") int sId) {
        Survey surveyObj = surveyService.getSurvey(sId);

        HashMap<String, List<Response>> hmap = new HashMap<>();
        //keep question , and its answers
        for(Question question: surveyObj.getQuestions()) {
            hmap.put(question.getDescription(), question.getResponses());
        }

        return hmap;
    }

// when user clicks on "submit" button ,  request ex: localhost:8080/survey/{sId}/responses?pId=123answers=aaa,bbrequest ex: localhost:8080/survey/response/{sId}/answer1=""&answer2=""
    @RequestMapping(value="/survey/responses/{sId}",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createResponse(@PathVariable(value="sId") int sId,  //survey id
                                            @RequestParam(value = "userId") int userId,
                                            @RequestParam(value = "answers") String[] answers){

        try {
            // add answers in questions list of the survey

            Survey surveyObj = surveyService.getSurvey(sId);
            //get list of questions
            List<Question> questions = surveyObj.getQuestions();

            //get Participant object by survey Id
            Participant participant = participantService.getParticipant(userId);

           // responseService.saveResponses(questions, answers, user);
            //select each question and update response list
            int i = 0;
            Question question;
            while (i < answers.length) {

                question = questions.get(i); //select each question from questions list of the survey

                //save in response table
               // Response response = responseService.save(new Response(answers[i], question, user));
                Response response= responseService.saveResponse(answers[i],question,participant);

                //add response in question table
                question.getResponses().add(response);

                i++;
            }

            return new ResponseEntity<>("Done", HttpStatus.OK);
        }

        catch (Exception ue)   ///exception for unique constraint exception in mysql
        {
            return new ResponseEntity<>("error occured", HttpStatus.BAD_REQUEST);
        }

    }

}
