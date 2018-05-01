package com.surveyapp.backend.service;

import com.surveyapp.backend.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import com.surveyapp.backend.persistence.domain.backend.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /* The Application Logger */
    private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

         User user = userRepository.findByUsername(username);
         if(user == null){
             LOG.warn("Username {} was not found", username);
             throw new UsernameNotFoundException(("Username "+username+" not found"));
         }

         if(!user.isVerified()){
             LOG.warn("Username {} is not verified", username);
             throw new UsernameNotFoundException(("Username "+username+" is not verified"));
         }

         return user;
    }
}
