package com.surveyapp.backend.service;

<<<<<<< HEAD
import com.surveyapp.backend.persistence.repositories.*;
import com.surveyapp.backend.persistence.domain.backend.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
=======
import com.surveyapp.backend.persistence.domain.backend.SurveyEntity;
import com.surveyapp.backend.persistence.repositories.SurveyRepository;
import com.surveyapp.backend.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
>>>>>>> origin/savequestionkaran
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

    @Autowired
    private UserService userService;

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


    public List<String> getListOfSurveyDescriptions(String userName){

        List<Survey> surveyList = userService.findByUserName(userName).getSurveys();
        List<String> surveys = new ArrayList<>();
        for(Survey survey: surveyList)
            surveys.add(survey.getDescription());
        return surveys;
    }

    public Survey getSurveyByName(String description) {
            return surveyRepository.findByDescription(description);
    }

}
