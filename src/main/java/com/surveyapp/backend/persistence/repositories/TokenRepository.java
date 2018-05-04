package com.surveyapp.backend.persistence.repositories;

import com.surveyapp.backend.persistence.domain.backend.Token;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<Token, String>{
}
