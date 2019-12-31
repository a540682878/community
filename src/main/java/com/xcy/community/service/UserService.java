package com.xcy.community.service;

import com.xcy.community.mapper.UserMapper;
import com.xcy.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    //判断添加用户还是更新用户
    public User addOrUpdate(User user){
        //先获取已有的user
        User dbuser = userMapper.findUserByAccoundId(user.getAccoundId());
        if(dbuser == null){
            user.setGmtCreat(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreat());
            userMapper.insertUser(user);
        }else {
            dbuser.setGmtModified(user.getGmtCreat());
            dbuser.setName(user.getName());
            dbuser.setToken(user.getToken());
            dbuser.setAvatarUrl(user.getAvatarUrl());
            userMapper.updateUser(dbuser);
        }
        return user;
    }
}
