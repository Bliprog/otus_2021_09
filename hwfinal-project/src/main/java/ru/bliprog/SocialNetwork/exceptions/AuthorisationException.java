package ru.bliprog.SocialNetwork.exceptions;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthorisationException extends Exception {
    private final String name;
    private final String message;
}
