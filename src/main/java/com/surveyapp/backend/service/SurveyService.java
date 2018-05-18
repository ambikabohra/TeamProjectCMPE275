package com.surveyapp.backend.service;


import com.surveyapp.backend.persistence.domain.backend.SurveyEntity;
import com.surveyapp.backend.persistence.domain.backend.Token;
import com.surveyapp.backend.persistence.domain.backend.User;
import com.surveyapp.backend.persistence.repositories.SurveyRepository;
import com.surveyapp.backend.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@Transactional
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ParticipantService participantService;

    public void addSurvey(SurveyEntity surveyEntity){
        surveyRepository.save(surveyEntity);
    }

    public SurveyEntity getSurvey(String surveyName) {
        return surveyRepository.findBySurveyName(surveyName);
    }

    public SurveyEntity getSurveyById(int surveyId) {
        return surveyRepository.findBySurveyId(surveyId);
    }


    public SurveyEntity getSurvey(int sId) {
        return surveyRepository.findBySurveyId(sId);
    }


    public SurveyEntity getSurveyObject(int sId) {
            return surveyRepository.findBySurveyId(sId);

    }


    public List<String> getListOfSurveyDescriptions(String userName){

        List<SurveyEntity> surveyList = userService.findByUserName(userName).getSurveys();
        List<String> surveys = new ArrayList<>();
        for(SurveyEntity survey: surveyList)
            surveys.add(survey.getDescription());
        return surveys;
    }

    public List<SurveyEntity> getListOfGivenSurveyDescriptions(String userName){

        Set<Token> tokens = participantService.getParticipantByPName(userName).getTokens();

        List<SurveyEntity> surveys = new ArrayList<>();
        for(Token token: tokens){
            surveys.add(token.getSurvey());
        }

        return surveys;
    }

    public SurveyEntity getSurveyByName(String description) {
            return surveyRepository.findByDescription(description);
    }

    public List<SurveyEntity> getSurveyByUser(User user) {

        List<SurveyEntity> surveyEntityList = surveyRepository.findByUser(user);

        return surveyEntityList;


    }
}
