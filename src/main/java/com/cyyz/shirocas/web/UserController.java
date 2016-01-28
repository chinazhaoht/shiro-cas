package com.cyyz.shirocas.web;

import com.cyyz.shirocas.beans.User;
import com.cyyz.shirocas.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Administrator on 2016/1/26.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users",method = RequestMethod.POST)
    public User register(String username,String password,String mobile,String email){

        return userService.register(username,password,mobile,email);
    }

    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public ModelAndView findUsers(){

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        System.out.println(session.getId());
        System.out.println(session.getHost());
        System.out.println(session.getTimeout());

        List<User> userList = userService.findAll();
        ModelAndView modelAndView = new ModelAndView("/user/userList");
        modelAndView.addObject("userList",userList);
        return  modelAndView;
    }
}
