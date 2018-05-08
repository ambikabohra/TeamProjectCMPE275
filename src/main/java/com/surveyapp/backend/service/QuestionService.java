package com.surveyapp.backend.service;

import com.surveyapp.backend.persistence.domain.backend.Question;
import com.surveyapp.backend.persistence.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public void addQuestion(Question question){
        questionRepository.save(question);
    }

}
