package com.lijiazhen.springboot.service;

import com.lijiazhen.springboot.Mapper.UserMapper;
import com.lijiazhen.springboot.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(String name, String password) {
        Map<String,String> map =new HashMap<>();
        map.put("name",name);
        map.put("password",password);
//        System.out.println("userService"+name);
//        System.out.println("userService"+password);
        return userMapper.login(map);
    }

    public void add(User user) {
        userMapper.register(user);
    }
}
