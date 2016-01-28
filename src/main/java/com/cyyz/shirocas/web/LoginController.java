package com.cyyz.shirocas.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2016/1/25.
 */
@Controller
@RequestMapping("/")
public class LoginController {


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(HttpServletRequest request,String username,String password ){
        HttpSession session = request.getSession(true);
        String errorMessage = "";
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        //UserTokenAuth token = new UserTokenAuth(username);
        System.out.println(session.getId());
        token.setRememberMe(true);
        try{
            user.login(token);
            String userId = (String) user.getPrincipal();
            session.setAttribute("USERNAME", userId);
            session.setAttribute("token",token);
            System.out.println(token.toString());
            return "success";
        }catch(UnknownAccountException e){

        }catch (IncorrectCredentialsException e){

        }catch (LockedAccountException e){

        }catch (AuthenticationException e){
            token.clear();
        }
        session.setAttribute("ErrorMessage",errorMessage);

        return "error";
    }

    @RequestMapping("/to_register")
    public String toRegister(){
        return "/register";
    }

    @RequestMapping("to_login")
    public String toLogin(){
        return "login";
    }
}
