package com.surveyapp.test.integration;


import com.surveyapp.backend.persistence.domain.backend.User;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Rule
    public TestName testName = new TestName();

    @Test
    public void CreateNewUserTest() throws Exception {

        User userCreated = createUser(testName);

        Assert.assertNotNull(userCreated);
        Assert.assertNotNull(userCreated.getId());


    }


}
