package com.surveyapp.utils;


import com.surveyapp.backend.persistence.domain.backend.Question;

public class SurveyUtils {

    private SurveyUtils()  { throw new AssertionError("Non Instantiable");}

    public static <T extends com.surveyapp.web.domain.frontend.Question> Question webToDomainQues(T frontendPayload){
        Question question = new Question();
        question.setqType(frontendPayload.getQuesType());
        question.setDescription(frontendPayload.getQuesText());
        return question;

    }


}
