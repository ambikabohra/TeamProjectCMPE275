package com.surveyapp.backend.persistence.domain.backend;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class QuestionOption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int optionId;

    private String optionValue;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "qId")
    private Question question;


    public QuestionOption() {
    }


    public QuestionOption(String optionValue, Question question) {
        this.optionValue = optionValue;
        this.question = question;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }
}