package com.bupt.database.service;

import com.bupt.database.annotation.AuthCheck;
import com.bupt.database.mapper.UserMapper;
import com.bupt.database.pojo.Resp;
import com.bupt.database.pojo.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanz
 */
@Service
public class UserService {
    private static final String DEFAULT_AVATAR = "https://www.bupt.site/image/avata.png";
    final UserMapper userMapper;


    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Resp login(User user, HttpSession session) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            return new Resp(-1, "用户名和密码不能为空!");
        }
        User userByUserName = userMapper.getUserByUserName(user.getUsername());

        if (userByUserName == null) {
            return new Resp(-5, "用户不存在!");
        }

        boolean res = userByUserName.getPassword().equals(user.getPassword());
        if (res) {
            HashMap<String, Object> resMap = new HashMap<>(16);
            session.setAttribute("role", userByUserName.getRole());
            session.setAttribute("username", userByUserName.getUsername());
            session.setMaxInactiveInterval(3600);

            resMap.put("user_role", userByUserName.getRole());
            resMap.put("username", userByUserName.getUsername());
            resMap.put("token", session.getId());
            resMap.put("avatar", DEFAULT_AVATAR);

            return new Resp(resMap);
        }
        return new Resp(-2, "密码错误,请重试!");

    }

    @AuthCheck(role = "admin")
    public Resp addUser(User user) {
        User userByUserName = userMapper.getUserByUserName(user.getUsername());
        if (userByUserName != null) {
            return new Resp(-3, "Username already exists");
        }
        int insert = userMapper.insert(user);
        if (insert == 1) {
            return new Resp(user);
        }
        return new Resp(-4, "Insertion failed, please try again");
    }

    @AuthCheck(role = "admin")
    public Resp deleteUser(String username, @NotNull HttpSession session) {

        String loginUser = (String) session.getAttribute("username");
        if (loginUser.equals(username)) {
            return new Resp(-4, "不能删除自己!");
        }

        Map<String, Object> map = new HashMap<>(16);
        map.put("username", username);
        int delete = userMapper.deleteByMap(map);

        if (delete == 1) {
            return new Resp("success");
        }
        return new Resp(-4, "删除失败请重试!");
    }

    @AuthCheck(role = {"admin"})
    public Resp getUserInfo() {
        List<User> list = userMapper.getAllUsers();
        return new Resp(list);
    }

    @AuthCheck(role = {"admin", "user"})
    public Resp getCurrentUser(HttpSession session) {
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("username", username);
        map.put("role", role);
        map.put("avatar", DEFAULT_AVATAR);
        return new Resp(map);

    }

    public Resp logout(@org.jetbrains.annotations.NotNull HttpSession session) {
        session.invalidate();
        return new Resp("success");
    }


}
