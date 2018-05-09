package com.surveyapp.backend.service;

import com.surveyapp.backend.persistence.repositories.*;
import com.surveyapp.backend.persistence.domain.backend.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private UserRepository userRepository;

        public Survey createSurvey(String email, String description, String surveyType, String endTime){

            List<Question> question_list = new ArrayList<>();

            Survey survey = surveyRepository.save(new Survey(true, description, endTime, surveyType, question_list));
            User user = userRepository.findByEmail(email);

            System.out.println(user.getUsername());
            user.getSurveys().add(survey);  //add to the surveyor's survey list
            userRepository.save(user);

            return survey;
        }

    public Survey getSurvey(int sId) {
            return surveyRepository.findBySurveyId(sId);

    }

    public Survey getSurveyByName(String description) {
            return surveyRepository.findByDescription(description);
    }

}
