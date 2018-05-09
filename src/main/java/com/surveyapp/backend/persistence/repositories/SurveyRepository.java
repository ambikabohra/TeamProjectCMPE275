package com.surveyapp.backend.persistence.repositories;

import com.surveyapp.backend.persistence.domain.backend.SurveyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends CrudRepository<SurveyEntity, Integer> {

    public SurveyEntity findBySurveyName(String surveyName);

    public SurveyEntity findBySurveyId(int surveyId);

}
