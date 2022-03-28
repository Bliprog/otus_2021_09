package ru.bliprog.SocialNetwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bliprog.SocialNetwork.entity.User;
import ru.bliprog.SocialNetwork.enums.RolesEnum;
import ru.bliprog.SocialNetwork.enums.ViewEnum;
import ru.bliprog.SocialNetwork.exceptions.AuthorisationException;
import ru.bliprog.SocialNetwork.forms.UserChangeInfoForm;
import ru.bliprog.SocialNetwork.forms.UserChangePasswordForm;
import ru.bliprog.SocialNetwork.service.UserService;
import ru.bliprog.SocialNetwork.utils.UserRolesDefinition;
import ru.bliprog.SocialNetwork.utils.securityUtils.SecurityAuthUserUtil;


@CrossOrigin(origins = {"${service.frontend_url}"})
@Controller
@RequestMapping("/edit")
@RequiredArgsConstructor
public class EditUserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')||hasAuthority('ROLE_SUPERADMIN')||#username==authentication.name")
    @GetMapping("/{username}/change_information")
    public String editFormGet(@PathVariable String username, Model model) {
        User user = userService.findUserByName(username);
        model.addAttribute("userForm", user);
        return ViewEnum.EDIT_USER_INFO_VIEW.toString();

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')||hasAuthority('ROLE_SUPERADMIN')||#username==authentication.name")
    @GetMapping("/{username}/change_password")
    public String editPasswordFormGet(@PathVariable String username, Model model) {
        User user = userService.findUserByName(username);
        model.addAttribute("userForm", user);
        return ViewEnum.EDIT_USER_PASSWORD_VIEW.toString();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')||hasAuthority('ROLE_SUPERADMIN')||#username==authentication.name")
    @PostMapping("/{username}/change_information")
    public String editInfoFormPost(@PathVariable String username, @ModelAttribute("userForm") UserChangeInfoForm userForm) throws AuthorisationException {
        User user = userService.findUserByName(username);
        User requestUser = userService.findUserByName(SecurityAuthUserUtil.getCurrentUsername());
        boolean isUserAdmin = UserRolesDefinition.isUserInRole(user, RolesEnum.ROLE_SUPERADMIN) || UserRolesDefinition.isUserInRole(user, RolesEnum.ROLE_ADMIN);
        boolean isSuperAdmin = UserRolesDefinition.isUserInRole(requestUser, RolesEnum.ROLE_SUPERADMIN);
        if (isUserAdmin && !isSuperAdmin && !username.equals(SecurityAuthUserUtil.getCurrentUsername())) {
            throw new AuthorisationException("accessError", "У вас недостаточно прав для изменения этого пользователя");
        }
        user.setEmail(userForm.getEmail());
        user.setPhone(userForm.getPhone());
        if (user.getUsername().equals(userForm.getUsername())) {
            userService.saveEditUserInfo(user);
        } else {
            if (userService.isUsernameAlreadyUsed(userForm.getUsername())) {
                throw new AuthorisationException("usernameError", "Имя пользователя занято");
            } else {
                boolean isCurrentUser = SecurityAuthUserUtil.getCurrentUsername().equals(user.getUsername());
                user.setUsername(userForm.getUsername());
                userService.saveEditUserInfo(user);
                if (isCurrentUser) {
                    SecurityAuthUserUtil.changeCurrentAuthority(user);
                }
            }
        }
        return ViewEnum.EDIT_USER_INFO_VIEW.toString();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')||hasAuthority('ROLE_SUPERADMIN')||#username==authentication.name")
    @PostMapping("/{username}/change_password")
    public String editPasswordFormPost(@PathVariable String username,
                                       @ModelAttribute("userForm") UserChangePasswordForm userForm,
                                       Model model) throws AuthorisationException {
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            throw new AuthorisationException("passwordError", "Пароли не совпадают");
        }
        User user = userService.findUserByName(username);
        User requestUser = userService.findUserByName(SecurityAuthUserUtil.getCurrentUsername());
        boolean isUserAdmin = UserRolesDefinition.isUserInRole(user, RolesEnum.ROLE_SUPERADMIN) || UserRolesDefinition.isUserInRole(user, RolesEnum.ROLE_ADMIN);
        boolean isSuperAdmin = UserRolesDefinition.isUserInRole(requestUser, RolesEnum.ROLE_SUPERADMIN);
        if (isUserAdmin && !isSuperAdmin && !username.equals(SecurityAuthUserUtil.getCurrentUsername())) {
            model.addAttribute("accessError", "У вас недостаточно прав для изменения этого пользователя");
            throw new AuthorisationException("accessError", "У вас недостаточно прав для изменения этого пользователя");
        }
        user.setPassword(userForm.getPassword());
        userService.ChangePassword(user);
        return "redirect:/" + ViewEnum.USER_TABLE_VIEW;
    }
}
