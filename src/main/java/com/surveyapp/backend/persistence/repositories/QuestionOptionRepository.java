package com.surveyapp.backend.persistence.repositories;

import com.surveyapp.backend.persistence.domain.backend.QuestionOption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionOptionRepository extends CrudRepository<QuestionOption, Integer> {
}
