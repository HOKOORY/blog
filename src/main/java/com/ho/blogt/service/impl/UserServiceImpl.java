package com.ho.blogt.service.impl;

import com.ho.blogt.entity.User;
import com.ho.blogt.enums.ErrorCodeAndMsg;
import com.ho.blogt.exception.HttpException;
import com.ho.blogt.mapper.UserMapper;
import com.ho.blogt.service.UserService;
import com.ho.blogt.utils.HashEncodeUtil;
import com.ho.blogt.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * @author 63408
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    TokenServiceImpl tokenService;


    @Override
    public User login(String name, String password) {
        User user = userMapper.getUserByName(name);
        if (null == user) {
            throw new HttpException(ErrorCodeAndMsg.User_INPUT_FAIL);
        }
        String md5password = null;
        try {
            // 进行密码+盐 md5 加密
            md5password = HashEncodeUtil.Md5Encode(password + user.getSalt());
        } catch (NoSuchAlgorithmException e) {
            throw new HttpException(ErrorCodeAndMsg.NETWORK_ERROR);
        }
        // 判断加密后密码是否与数据库的密码相同
        if (!md5password.equals(user.getPassword())) {
            throw new HttpException(ErrorCodeAndMsg.USER_PASSWORD_ERROR);
        }
        // 验证成功 ， 生成token
        String token = tokenService.generatorToken(user.getId() + user.getName() + StringUtil.randomString(12));
        user.setToken(token);
        tokenService.setToken(token, user);
        user.setSalt("");
        user.setPassword("");
        return user;
    }

    @Override
    public User register(User user) {
        User mUser = userMapper.getUserByName(user.getName());
        if (null != mUser){
            throw new HttpException(ErrorCodeAndMsg.USER_NAME_IN_DATABASE);
        }
        String salt = StringUtil.randomString(5);
        user.setSalt(salt);
        String password = null;
        try {
            password = HashEncodeUtil.Md5Encode(user.getPassword() + salt);
        } catch (NoSuchAlgorithmException e) {
            throw new HttpException(ErrorCodeAndMsg.NETWORK_ERROR);
        }
        user.setPassword(password);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userMapper.register(user);
        user.setPassword("");
        user.setSalt("");
        return user;
    }
}
