package ru.bliprog.SocialNetwork.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ViewEnum {
    ADMIN_VIEW("admin"),
    ALL_USER_VIEW("all_user"),
    EDIT_USER_INFO_VIEW("edit_user_info"),
    INDEX_VIEW("index"),
    LOGIN_VIEW("login"),
    REGISTRATION_VIEW("registration"),
    USER_DETAIL_VIEW("user_detail"),
    USER_TABLE_VIEW("user_table"),
    EDIT_USER_PASSWORD_VIEW("edit_user_password"),
    CHAT_VIEW("chat");

    private final String name;

    @Override
    public String toString() {
        return this.name;
    }
}
