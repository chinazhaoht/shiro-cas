package com.cyyz.shirocas.service;

import com.cyyz.shirocas.beans.User;
import com.cyyz.shirocas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Administrator on 2016/1/25.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User register(String username,String password,String mobile,String email){
        User user = new User();
        user.setCreatedTime(LocalDateTime.now());
        user.setUsername(username);
        user.setPassword(password);
        user.setMobile(mobile);
        user.setEmail(email);
        user.setEnable(true);
        return userRepository.save(user);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

}
