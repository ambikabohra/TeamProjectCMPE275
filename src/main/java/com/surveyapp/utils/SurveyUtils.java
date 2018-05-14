package com.surveyapp.utils;


import com.surveyapp.backend.persistence.domain.backend.Question;
import com.surveyapp.backend.persistence.domain.backend.QuestionOption;
import com.surveyapp.backend.persistence.domain.backend.SurveyEntity;
import com.surveyapp.backend.persistence.domain.backend.User;
import com.surveyapp.web.domain.frontend.Survey;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;

public class SurveyUtils {

    private SurveyUtils() {
        throw new AssertionError("Non Instantiable");
    }

    public static <T extends com.surveyapp.web.domain.frontend.Question> Question webToDomainQues(T frontendPayload, SurveyEntity survey/*, List<QuestionOption> questionOptionList*/) {
        Question question = new Question();
        question.setqType(frontendPayload.getQuesType());
        question.setDescription(frontendPayload.getQuesText());
        System.out.println("reached till here");
        question.setSurvey(survey);
        System.out.println("this ran too");
        System.out.println("reached till here 1");
       /* question.setOptions(questionOptionList);*/
        System.out.println("this ran too 1");
        return question;

    }

    public static <T extends Survey> SurveyEntity webToDomainSurvey(T frontendPayload, User user) {
        SurveyEntity surveyEntity = new SurveyEntity();
        surveyEntity.setSurveyType(frontendPayload.getSurveyType());
        surveyEntity.setSurveyName(frontendPayload.getSurveyName());
        surveyEntity.setDescription(frontendPayload.getSurveyDesc());
        surveyEntity.setUser(user);

        int expirationTimeInMin = Integer.parseInt(frontendPayload.getDays()) * 24 * 60 + Integer.parseInt(frontendPayload.getHours()) * 60 + Integer.parseInt(frontendPayload.getMinutes());
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        surveyEntity.setEndTime(now.plusMinutes(expirationTimeInMin));

        return surveyEntity;

    }

   /* public static <T extends com.surveyapp.web.domain.frontend.Question> List<QuestionOption> webToDomainOption(T frontendPayload) {

        System.out.println("inside webToDomainOption");
        List<QuestionOption> questionOptionList = new ArrayList<QuestionOption>();
        QuestionOption option1 = new QuestionOption(frontendPayload.getOption1Text());
        System.out.println("option 1"+option1.getOptionValue());
        QuestionOption option2 = new QuestionOption(frontendPayload.getOption2Text());
        QuestionOption option3 = new QuestionOption(frontendPayload.getOption3Text());
        QuestionOption option4 = new QuestionOption(frontendPayload.getOption4Text());


        questionOptionList.add(option1);
        questionOptionList.add(option2);
        questionOptionList.add(option3);
        questionOptionList.add(option4);

        System.out.println("questionOptionList size " + questionOptionList.size());

        return questionOptionList;
    }*/


}
