package com.surveyapp.backend.service;

import com.surveyapp.backend.persistence.domain.backend.SurveyEntity;
import com.surveyapp.backend.persistence.repositories.SurveyRepository;
import com.surveyapp.backend.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private UserRepository userRepository;

    public void addSurvey(SurveyEntity surveyEntity){
        surveyRepository.save(surveyEntity);
    }

    public SurveyEntity getSurvey(String surveyName) {
        return surveyRepository.findBySurveyName(surveyName);
    }

    public SurveyEntity getSurveyById(int surveyId) {
        return surveyRepository.findBySurveyId(surveyId);
    }

//    public Survey createSurvey(String email, String description, String surveyType, String endTime){
//
//        List<Question> question_list = new ArrayList<>();
//
//        SurveyEntity survey = surveyRepository.save(new Survey(true, description, endTime, surveyType, question_list));
//        User user = userRepository.findByEmail(email);
//
//
//        System.out.println(user.getUsername());
//        survey.
//        user.getSurveys().add(survey);  //add to the surveyor's survey list
//        userRepository.save(user);
//
//        return survey;
//    }

    public SurveyEntity getSurvey(int sId) {
        return surveyRepository.findBySurveyId(sId);

    }

}
