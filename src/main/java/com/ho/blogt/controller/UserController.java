package com.ho.blogt.controller;

import com.ho.blogt.entity.Response;
import com.ho.blogt.entity.User;
import com.ho.blogt.service.TokenService;
import com.ho.blogt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    /**
     * 登录接口
     *
     * @param name     用户名
     * @param password 密码
     * @param token    token 用于直接获取用户信息
     * @return
     */
    @PostMapping(value = "/login")
    public Response login(@RequestParam(name = "name") String name,
                          @RequestParam(name = "password") String password,
                          @RequestHeader(name = "token", required = false, defaultValue = "0") String token) {
        User user = (User) tokenService.getToken(token);
        if (null != user) {
            return new Response(user);
        }
        user = userService.login(name, password);
        return new Response(user);
    }

    /**
     * 注册用户
     *
     * @param name        用户名
     * @param password    密码
     * @param avatar      头像
     * @param phoneNumber 手机号码
     * @return
     */
    @PostMapping(value = "/register")
    public Response register(@RequestParam(name = "name") String name,
                             @RequestParam(name = "password") String password,
                             @RequestParam(name = "avatar") String avatar,
                             @RequestParam(name = "phoneNumber") String phoneNumber) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setAvatar(avatar);
        user.setPhoneNumber(phoneNumber);
        User regUser = userService.register(user);
        return new Response(regUser);
    }
}
