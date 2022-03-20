package ru.bliprog.SocialNetwork.forms;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserChangePasswordForm {
    private String password;
    private String passwordConfirm;
}