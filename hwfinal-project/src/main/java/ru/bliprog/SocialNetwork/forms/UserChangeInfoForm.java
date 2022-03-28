package ru.bliprog.SocialNetwork.forms;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserChangeInfoForm {
    private String username;
    private String email;
    private String phone;
}
