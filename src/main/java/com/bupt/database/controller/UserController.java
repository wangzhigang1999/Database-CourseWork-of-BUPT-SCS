package com.bupt.database.controller;

import com.bupt.database.pojo.User;
import com.bupt.database.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author wanz
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/database")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping("/login")
    public Object login(String username, String password, HttpSession session) {
        User user = new User(username, password);
        return userService.login(user, session);
    }

    @RequestMapping("/addUser")
    public Object addUser(String username, String password, String role) {
        User user = new User(username, password, role);
        return userService.addUser(user);
    }

    @RequestMapping("/deleteUser")
    public Object deleteUser(String username, HttpSession session) {
        return userService.deleteUser(username, session);
    }

    @RequestMapping("/getAllUser")
    public Object getAllUser() {
        return userService.getUserInfo();
    }

    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(HttpSession session) {
        return userService.getCurrentUser(session);
    }

    @RequestMapping("/logout")
    public Object logout(HttpSession session) {
        return userService.logout(session);
    }
}
