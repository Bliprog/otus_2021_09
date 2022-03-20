package ru.bliprog.SocialNetwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bliprog.SocialNetwork.entity.User;
import ru.bliprog.SocialNetwork.enums.RolesEnum;
import ru.bliprog.SocialNetwork.enums.ViewEnum;
import ru.bliprog.SocialNetwork.service.UserService;
import ru.bliprog.SocialNetwork.utils.UserRolesDefinition;
import ru.bliprog.SocialNetwork.utils.securityUtils.SecurityAuthUserUtil;

@CrossOrigin(origins = {"${service.frontend_url}"})
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {
    private final UserService userService;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String index(Model model){
        User requestUser = userService.findUserByName(SecurityAuthUserUtil.getCurrentUsername());
        if(requestUser==null){
            model.addAttribute("username","unknown");
        }
        else {
            model.addAttribute("isAdmin", UserRolesDefinition.isUserInRole(requestUser, RolesEnum.ROLE_ADMIN)||UserRolesDefinition.isUserInRole(requestUser,RolesEnum.ROLE_SUPERADMIN));
            model.addAttribute("username",requestUser.getUsername());
        }

            return ViewEnum.INDEX_VIEW.toString();
    }
}
