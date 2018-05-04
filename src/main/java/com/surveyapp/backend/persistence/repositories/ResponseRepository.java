package com.surveyapp.backend.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.surveyapp.backend.persistence.domain.backend.Response;

@Repository
public interface ResponseRepository extends CrudRepository<Response, Integer>{


}
