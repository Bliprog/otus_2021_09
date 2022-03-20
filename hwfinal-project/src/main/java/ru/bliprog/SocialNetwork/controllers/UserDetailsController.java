package ru.bliprog.SocialNetwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bliprog.SocialNetwork.entity.User;
import ru.bliprog.SocialNetwork.enums.RolesEnum;
import ru.bliprog.SocialNetwork.enums.ViewEnum;
import ru.bliprog.SocialNetwork.service.UserService;
import ru.bliprog.SocialNetwork.utils.UserRolesDefinition;
import ru.bliprog.SocialNetwork.utils.securityUtils.SecurityAuthUserUtil;

@CrossOrigin(origins = {"${service.frontend_url}"})
@Controller
@RequestMapping("/user_details")
@RequiredArgsConstructor
public class UserDetailsController {
    private final UserService userService;

    @GetMapping("/{name}")
    public String userDetailsGet(Model model, @PathVariable String name){
        boolean isUserProfile=false;
        User requestUser = userService.findUserByName(SecurityAuthUserUtil.getCurrentUsername());
        User userDetail = userService.findUserByName(name);
        if(requestUser.getUsername().equals(userDetail.getUsername())){
            isUserProfile=true;
        }
        model.addAttribute("isAdmin", UserRolesDefinition.isUserInRole(requestUser, RolesEnum.ROLE_ADMIN)||UserRolesDefinition.isUserInRole(requestUser,RolesEnum.ROLE_SUPERADMIN));
        model.addAttribute("isUserAdmin", UserRolesDefinition.isUserInRole(userDetail, RolesEnum.ROLE_ADMIN)||UserRolesDefinition.isUserInRole(userDetail,RolesEnum.ROLE_SUPERADMIN));
        model.addAttribute("isSuperAdmin",UserRolesDefinition.isUserInRole(requestUser,RolesEnum.ROLE_SUPERADMIN));
        model.addAttribute("isUserProfile",isUserProfile);
        model.addAttribute("user",userDetail);
        return ViewEnum.USER_DETAIL_VIEW.toString();
    }
}
