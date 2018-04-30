package com.surveyapp.test.integration;

import com.surveyapp.backend.persistence.domain.backend.*;
import com.surveyapp.backend.persistence.repositories.PasswordResetTokenRepository;
import com.surveyapp.backend.persistence.repositories.PlanRepository;
import com.surveyapp.backend.persistence.repositories.RoleRepository;
import com.surveyapp.backend.persistence.repositories.UserRepository;
import com.surveyapp.enums.PlanEnum;
import com.surveyapp.enums.RolesEnum;
import com.surveyapp.utils.UserUtils;
import org.junit.Assert;
import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractIntegrationTest {

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected PlanRepository planRepository;
    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    protected PasswordResetTokenRepository passwordResetTokenRepository;

    @Value("${token.expiration.length.minutes}")
    protected int expirationTimeInMinutes;


    protected Plan createBasicPlan(PlanEnum planEnum) {
        return new Plan(planEnum);
    }

    protected Role createBasicRole(RolesEnum rolesEnum) {
        return new Role(rolesEnum);
    }

    protected User createUser(String username, String email) {

        Plan basicPlan = createBasicPlan(PlanEnum.BASIC);
        planRepository.save(basicPlan);

        User basicUser = UserUtils.createBasicUser(username, email);
        basicUser.setPlan(basicPlan);

        Role basicRole = createBasicRole(RolesEnum.BASIC);
        roleRepository.save(basicRole);

        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole(basicUser, basicRole);
        userRoles.add(userRole);

        basicUser.getUserRoles().addAll(userRoles);
        basicUser = userRepository.save(basicUser);

        return basicUser;

    }

    protected User createUser(TestName testName){
        return createUser(testName.getMethodName(), testName.getMethodName()+"@surveyapp.com");
    }

    protected PasswordResetToken createPasswordResetToken(String token, User user, LocalDateTime now) {


        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, now, expirationTimeInMinutes);
        passwordResetTokenRepository.save(passwordResetToken);
        Assert.assertNotNull(passwordResetToken.getId());
        return passwordResetToken;

    }
    
}
