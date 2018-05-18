package com.surveyapp.backend.persistence.repositories;

//import com.surveyapp.backend.persistence.domain.backend.Survey;
import com.surveyapp.backend.persistence.domain.backend.SurveyEntity;
import com.surveyapp.backend.persistence.domain.backend.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends CrudRepository<SurveyEntity, Integer> {

    public SurveyEntity findBySurveyName(String surveyName);

    public SurveyEntity findBySurveyId(int surveyId);

//    public SurveyEntity findBySId(int sId);

    public SurveyEntity findByDescription(String description);

    public List<SurveyEntity> findByUser(User user);
}
