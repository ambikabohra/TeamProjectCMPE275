package com.devopsbuddy.utils;

import com.devopsbuddy.backend.persistence.domain.backend.User;
import com.devopsbuddy.web.controllers.ForgotMyPasswordController;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * Builds and returns the URL to reset the user password.
     * @param request The Http Servlet Request
     * @param userId The user id
     * @param token The token
     * @return the URL to reset the user password.
     */
    public static String createPasswordResetUrl(HttpServletRequest request, long userId, String token) {
        String passwordResetUrl =
                request.getScheme() +
                        "://" +
                        request.getServerName() +
                        ":" +
                        request.getServerPort() +
                        request.getContextPath() +
                        ForgotMyPasswordController.CHANGE_PASSWORD_PATH +
                        "?id=" +
                        userId +
                        "&token=" +
                        token;

        return passwordResetUrl;
    }

}
