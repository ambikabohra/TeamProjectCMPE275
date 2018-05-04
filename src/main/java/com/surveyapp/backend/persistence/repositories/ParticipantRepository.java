package com.surveyapp.backend.persistence.repositories;


import com.surveyapp.backend.persistence.domain.backend.Participant;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface ParticipantRepository extends CrudRepository<Participant, Integer> {

    Participant findByEmail(String email);

//    @Query(value = "SELECT * FROM Participant t where t.id = :pId")
     Participant findByUserId(int userId);
}