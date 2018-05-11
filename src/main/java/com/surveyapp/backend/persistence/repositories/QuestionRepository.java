package com.surveyapp.backend.persistence.repositories;

import com.surveyapp.backend.persistence.domain.backend.Question;

import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;

@Transactional
public interface QuestionRepository extends CrudRepository<Question, Integer> {

    Question findByqId(int i);
}