package com.surveyapp.backend.persistence.domain.backend;

//import com.sun.istack.internal.NotNull;

//import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import lombok.Data;

@Entity
public class Survey {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int surveyId;


        @Column
      //  @NotNull
        private Boolean isPublished;

     //   @Temporal(TemporalType.DATE)
        @Column
        private String endTime;
  //      private String endTime;

        @Column
       // @NotNull
        private String surveyType;

        private String description;

        @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Question> questions;  //question can be added with survey creation or later


        @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Token> tokens = new ArrayList<Token>();

         @JsonIgnore
         @ManyToOne(fetch = FetchType.EAGER)
      //   @JoinColumn(name = "email")
         @JoinColumn(name = "id")
         private User user;   //mapped to User table


        private Date current = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "curr", length = 10)
    public Date getCurrrent() {
        return this.current;
    }


    public Survey() {
    }

    public Survey(Boolean isPublished, String description, String endTime, String surveyType, List<Question> questions  ) {
        this.isPublished = isPublished;
        this.description = description;
        this.endTime = endTime;
        this.surveyType = surveyType;
        this.questions = questions;
        this.current = new Date();
    }


    public int getSurveyId() {
        return surveyId;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean isPublished) {
        isPublished = isPublished;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(String surveyType) {
        this.surveyType = surveyType;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Token> getTokens() { return tokens; }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCurrent() {
        return current;
    }

    public void setCurrent(Date current) {
        this.current = current;
    }
}
