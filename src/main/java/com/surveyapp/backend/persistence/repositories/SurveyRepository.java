package com.surveyapp.backend.persistence.repositories;

import com.surveyapp.backend.persistence.domain.backend.Survey;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends CrudRepository<Survey, Integer> {
    Survey findBySurveyId(int sId);

    Survey findByDescription(String description);
}
