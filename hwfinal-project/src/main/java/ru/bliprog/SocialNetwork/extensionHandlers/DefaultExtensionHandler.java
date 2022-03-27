package ru.bliprog.SocialNetwork.extensionHandlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.bliprog.SocialNetwork.exceptions.AuthorisationException;

@ControllerAdvice
public class DefaultExtensionHandler {

    @ExceptionHandler(AuthorisationException.class)
    public ResponseEntity<?> handleException(AuthorisationException e) {
        return ResponseEntity.badRequest().body(e);
    }
}
