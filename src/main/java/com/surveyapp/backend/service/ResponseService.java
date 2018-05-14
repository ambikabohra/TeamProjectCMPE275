package com.surveyapp.backend.service;

import com.surveyapp.backend.persistence.repositories.ResponseRepository;
import com.surveyapp.backend.persistence.domain.backend.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    @Autowired
    private ResponseRepository responseRepository;

    public Response saveResponse(String answer, Question question, Participant user){
        return responseRepository.save(new Response(answer, question, user));
    }

    public void saveResponses(List<Question> questions, String[] answers, Participant user) {
//        int i = 0;
//        Question question;
//        while (i < answers.length) {
//
//            question = questions.get(i); //select each question from questions list of the survey
//
//            //save in response table
//            // Response response = responseService.save(new Response(answers[i], question, user));
//            Response response= saveResponse(answers[i],question,user);
//
//            //add response in question table
//            question.getResponses().add(response);
//
//            i++;
//        }
    }

    public Response saveResponse1(String s) {
        return responseRepository.save(new Response(s));
    }
}
