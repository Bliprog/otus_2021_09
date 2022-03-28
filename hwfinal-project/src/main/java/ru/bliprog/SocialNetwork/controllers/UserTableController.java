package ru.bliprog.SocialNetwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bliprog.SocialNetwork.entity.User;
import ru.bliprog.SocialNetwork.enums.ViewEnum;
import ru.bliprog.SocialNetwork.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = {"${service.frontend_url}"})
@Controller
@RequestMapping("/user_table")
@RequiredArgsConstructor
public class UserTableController {
    private final UserService userService;


    private Model createModel(Model model) {
        List<HashMap<String, Object>> users = new ArrayList<>();
        for (User user : userService.allUsers()) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("username", user.getUsername());
            hashMap.put("email", user.getEmail());
            users.add(hashMap);
        }
        model.addAttribute("users", users);
        return model;
    }

    @GetMapping
    public String userTableGet(Model model) {
        model = createModel(model);
        model.addAttribute("isGet", true);

        return ViewEnum.USER_TABLE_VIEW.toString();
    }

}
