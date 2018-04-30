package com.surveyapp;

import com.surveyapp.backend.service.I18NService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SurveyApplicationTests {

	@Autowired
	private I18NService i18NService;

	@Test
    public void testMessageByLocaleService() {
		String messageId = "index.main.callout";
		String expectedResult = "Bootstrap starter template";
		String actual = i18NService.getMessage(messageId);
		Assert.assertEquals("The Actual and Expected strings dont match", expectedResult, actual);
	}

}
