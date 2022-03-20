package ru.bliprog.SocialNetwork.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RolesEnum {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_SUPERADMIN("ROLE_SUPERADMIN");

    private final String name;

    @Override
    public String toString() {
        return this.name;
    }
}
