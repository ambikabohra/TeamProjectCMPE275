package com.devopsbuddy.utils;

import com.devopsbuddy.backend.persistence.domain.backend.User;

public class UserUtils {

    private UserUtils()  { throw new AssertionError("Non Instantiable");}

    public static User createBasicUser(){

        User user = new User();

        user.setUsername("basicUserName");
        user.setPassword("basicUserPassword");
        user.setEmail("basicUser@email.com");
        user.setFirstName("basicUserFirstName");
        user.setLastName("basicUserLastName");
        user.setPhoneNumber("1111111111");
        user.setCountry("basicUserCountry");
        user.setEnabled(true);
        user.setDescription("basicUserDescription");
        user.setProfileImageUrl("https://basicUser.image.com/basicUserProfileImageURL");

        return user;

    }

}
