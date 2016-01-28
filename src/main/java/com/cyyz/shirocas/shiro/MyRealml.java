package com.cyyz.shirocas.shiro;

import com.cyyz.shirocas.beans.User;
import com.cyyz.shirocas.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Created by Administrator on 2016/1/25.
 */
public class MyRealml implements Realm{


    @Autowired
    private UserService userService;

    public String getName() {
        return "myRealml";
    }

    public boolean supports(AuthenticationToken authenticationToken) {

        return authenticationToken instanceof UsernamePasswordAuthenticationToken;
    }

    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String username = (String)authenticationToken.getPrincipal();
        String password = (String)authenticationToken.getCredentials();
        User users = userService.findByUsername(username);
        if(users == null){
            throw new UnknownAccountException();
        }

        if(!users.getPassword().equals(password)){
            throw new IncorrectCredentialsException();
        }

        return new SimpleAuthenticationInfo(username,password,getName());
    }
}
