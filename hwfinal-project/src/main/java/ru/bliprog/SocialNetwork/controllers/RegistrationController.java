package ru.bliprog.SocialNetwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bliprog.SocialNetwork.entity.User;
import ru.bliprog.SocialNetwork.enums.ViewEnum;
import ru.bliprog.SocialNetwork.exceptions.AuthorisationException;
import ru.bliprog.SocialNetwork.service.UserService;

@CrossOrigin(origins = {"${service.frontend_url}"})
@Controller
@RequestMapping(value = "/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping
    public String registrationGet(Model model) {
        model.addAttribute("userForm", new User());
        return ViewEnum.REGISTRATION_VIEW.toString();
    }

    @PostMapping
    public String registrationPost(User userForm,
                                   Model model) throws AuthorisationException {
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            throw new AuthorisationException("passwordError", "Пароли не совпадают");
        }
        boolean isSaved = userService.saveNewUser(userForm);
        if (!isSaved) {
            model.addAttribute("saveError", "Имя пользователя занято");
            throw new AuthorisationException("saveError", "Имя пользователя занято");
        }
        return "redirect:/";
    }
}
