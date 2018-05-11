package com.surveyapp.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.surveyapp.backend.persistence.converters.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class SurveyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int surveyId;

    @Column
    private String surveyName;

    @Column(name = "expiry_date")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime endTime;

    @Column
    private String surveyType;

    @Column
    private Boolean isPublished;

    @Column
    private String description;

    @Column
    private String url;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private User user;   //mapped to User table

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Token> tokens = new ArrayList<>();

    private Date current = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "curr", length = 10)
    public Date getCurrrent() {
        return this.current;
    }

    public SurveyEntity() {
    }

    public SurveyEntity(Boolean isPublished, String description, LocalDateTime endTime, String surveyType, List<Question> questions  ) {
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

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(String surveyType) {
        this.surveyType = surveyType;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Date getCurrent() {
        return current;
    }

    public void setCurrent(Date current) {
        this.current = current;
    }
}