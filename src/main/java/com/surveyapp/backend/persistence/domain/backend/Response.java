package com.surveyapp.backend.persistence.domain.backend;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Response {


    //considering general survey, no user id required
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int responseId;

    private String answer;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "qId")
//    @JsonBackReference
//    private Question question;

    @JsonIgnore    //ignores looping in json response
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "qId")
    private Question question;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pId")
    private Participant participant;

    public Response(String answer, Question question, Participant participant) {
        this.answer = answer;
        this.question = question;
        this.participant = participant;
    }

    public Response() {
    }

    public int getResponseId() {
        return responseId;
    }

    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }
}
