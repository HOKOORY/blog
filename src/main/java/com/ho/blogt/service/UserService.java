package com.ho.blogt.service;

import com.ho.blogt.entity.User;

public interface UserService {
    /**
     * 用户登录
     * @param name 用户名
     * @param password 密码
     * @return user对象
     */
    User login(String name,String password);

    /**
     * 注册
     * @param user 需要的参数都封装到user对象中
     * @return 用户对象
     */
    User register(User user);

}
