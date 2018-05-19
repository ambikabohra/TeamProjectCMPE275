package com.surveyapp.backend.service;

import com.surveyapp.backend.persistence.domain.backend.Question;
import com.surveyapp.backend.persistence.domain.backend.SurveyEntity;
import com.surveyapp.backend.persistence.repositories.OptionRepository;
import com.surveyapp.backend.persistence.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;

    public void addQuestion(Question question){
        questionRepository.save(question);
    }

    //For getting Question List
    public List<Question> getQuestions(SurveyEntity obj){
        List<Question> questions = new ArrayList<>();
        questions = obj.getQuestions();
        return questions;
    }

    public void deleteQuestion(int i) {
        questionRepository.delete(i);
    }

    public Question getQuestionById(int id) {
        return questionRepository.findByqId(id);
    }
}
