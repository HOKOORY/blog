package com.ho.blogt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ho.blogt.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User>{
    User getUserByName(String name);
    long register(User user);
}
