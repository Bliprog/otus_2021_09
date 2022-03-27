package ru.bliprog.SocialNetwork.utils.securityUtils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.bliprog.SocialNetwork.entity.User;


public class SecurityAuthUserUtil {
    private static Authentication authentication;

    public static String getCurrentUsername() {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public static void changeCurrentAuthority(User user) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        User userInAuth = (User) authentication.getPrincipal();
        userInAuth.setUsername(user.getUsername());
    }
}
