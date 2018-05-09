package com.surveyapp.backend.service;

import com.surveyapp.backend.persistence.domain.backend.QuestionOption;
import com.surveyapp.backend.persistence.repositories.QuestionOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionOptionService {

    @Autowired
    private QuestionOptionRepository questionOptionRepository;

    public void addQuestionOption(QuestionOption questionOption){
        questionOptionRepository.save(questionOption);
    }


}
