package com.devopsbuddy.utils;

import com.devopsbuddy.backend.persistence.domain.backend.User;

public class UserUtils {

    private UserUtils()  { throw new AssertionError("Non Instantiable");}

    public static User createBasicUser(String username, String email){

        User user = new User();

        user.setUsername(username);
        user.setPassword("basicUserPassword");
        user.setEmail(email);
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
