package com.surveyapp.backend.service;

import com.surveyapp.backend.persistence.repositories.TokenRepository;
import com.surveyapp.backend.persistence.domain.backend.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    public Token generateToken(SurveyEntity survey) {

        return tokenRepository.save(new Token(survey));
    }

    public Token findByToken(String token){
        return tokenRepository.findByTokenId(token);
    }
}
