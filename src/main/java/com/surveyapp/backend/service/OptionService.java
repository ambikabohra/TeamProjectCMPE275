package com.surveyapp.backend.service;

import com.surveyapp.backend.persistence.domain.backend.Question;
import com.surveyapp.backend.persistence.domain.backend.QuestionOption;
//import com.surveyapp.backend.persistence.domain.backend.Survey;
import com.surveyapp.backend.persistence.repositories.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OptionService {

    @Autowired
    private OptionRepository optionRepository;

    public List<QuestionOption> getOptions(Question question){
        List<QuestionOption> options = new ArrayList<>();
        options = question.getOptions();
        return options;
    }



}
