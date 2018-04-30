package com.surveyapp.test.integration;

import com.surveyapp.backend.persistence.domain.backend.Role;
import com.surveyapp.backend.persistence.domain.backend.User;
import com.surveyapp.backend.persistence.domain.backend.UserRole;
import com.surveyapp.backend.service.UserService;
import com.surveyapp.enums.PlanEnum;
import com.surveyapp.enums.RolesEnum;
import com.surveyapp.utils.UserUtils;
import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractServiceIntegrationTest {
    @Autowired
    protected UserService userService;

    protected User createUser(TestName testName) {
        Set<UserRole> userRoles = new HashSet<>();

        String username = testName.getMethodName();
        String email = testName.getMethodName()+"@surveyapp.com";

        User basicUser = UserUtils.createBasicUser(username, email);
        userRoles.add(new UserRole(basicUser, new Role(RolesEnum.BASIC)));

        return userService.createUser(basicUser, PlanEnum.BASIC, userRoles);
    }
}
