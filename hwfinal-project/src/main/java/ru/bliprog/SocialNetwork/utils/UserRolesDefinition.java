package ru.bliprog.SocialNetwork.utils;

import lombok.RequiredArgsConstructor;
import ru.bliprog.SocialNetwork.entity.User;
import ru.bliprog.SocialNetwork.enums.RolesEnum;

import java.util.List;


@RequiredArgsConstructor
public class UserRolesDefinition {
    public static boolean isUserInRole(User user, RolesEnum role) {
        return user.getRoles().stream().anyMatch(x -> x.getAuthority().equals(role.toString()));
    }

    public static int getCountOfUsersInRole(List<User> users, RolesEnum role) {
        return (int) users.stream().filter(x -> x.getRoles().stream().anyMatch(y -> y.getAuthority().equals(role.toString()))).count();
    }
}
