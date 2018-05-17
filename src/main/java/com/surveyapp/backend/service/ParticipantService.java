package com.surveyapp.backend.service;

import com.surveyapp.backend.persistence.domain.backend.*;
import com.surveyapp.backend.persistence.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Part;
import java.util.Optional;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    public Participant getParticipant(int userId){
        Participant participant= participantRepository.findByUserId(userId);
        return participant;
    }

    public Participant saveParticipant(String pName, String email, String password, boolean b) {
        return participantRepository.save(new Participant(pName, email, password, true));
    }

    public Participant getParticipantByEmail(String email) {

        return  participantRepository.findByEmail(email);
    }

    public Participant getParticipantByPName(String userName) {
        return  participantRepository.findByPName(userName);
    }

//    public  findByEmail(String userName) {
//    }
}
