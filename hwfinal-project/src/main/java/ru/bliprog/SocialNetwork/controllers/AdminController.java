package ru.bliprog.SocialNetwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bliprog.SocialNetwork.entity.User;
import ru.bliprog.SocialNetwork.enums.RolesEnum;
import ru.bliprog.SocialNetwork.enums.ViewEnum;
import ru.bliprog.SocialNetwork.exceptions.AuthorisationException;
import ru.bliprog.SocialNetwork.service.UserService;
import ru.bliprog.SocialNetwork.utils.UserRolesDefinition;
import ru.bliprog.SocialNetwork.utils.securityUtils.SecurityAuthUserUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = {"${service.frontend_url}"})
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @PostMapping("/delete/{username}")
    public String deleteFormPost(@PathVariable String username, HttpServletRequest request, Model model) throws AuthorisationException {
        User user = userService.findUserByName(username);
        User requestUser = userService.findUserByName(SecurityAuthUserUtil.getCurrentUsername());
        boolean isUserAdmin = UserRolesDefinition.isUserInRole(user, RolesEnum.ROLE_SUPERADMIN) || UserRolesDefinition.isUserInRole(user, RolesEnum.ROLE_ADMIN);
        boolean isSuperAdmin = UserRolesDefinition.isUserInRole(requestUser, RolesEnum.ROLE_SUPERADMIN);
        if (isUserAdmin && !isSuperAdmin) {
            throw new AuthorisationException("accessError", "У вас недостаточно прав для удаления этого пользователя");
        }
        if (UserRolesDefinition.getCountOfUsersInRole(userService.allUsers(), RolesEnum.ROLE_SUPERADMIN) == 1) {
            model.addAttribute("superAdminError", "Вы единственный супер админ");
            throw new AuthorisationException("superAdminError", "Вы единственный супер админ");
        }
        userService.deleteUser(user.getId());
        if (request.getUserPrincipal().getName().equals(user.getUsername())) {
            try {
                request.logout();
            } catch (ServletException e) {
                System.out.println("Надеюсь такого не произойдет");
            }
        }
        return "redirect:/" + ViewEnum.USER_TABLE_VIEW;
    }
}
