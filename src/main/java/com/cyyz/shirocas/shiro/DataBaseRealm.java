package com.cyyz.shirocas.shiro;

import com.cyyz.shirocas.beans.User;
import com.cyyz.shirocas.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2016/1/26.
 */
@Component
public class DataBaseRealm extends AuthorizingRealm{


    @Autowired
    private UserService userService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String username = (String)authenticationToken.getPrincipal();
       // String password = (String)authenticationToken.getCredentials();

        String password = new String((char[])authenticationToken.getCredentials());
        User user = userService.findByUsername(username);
        if(user == null){
            throw new UnknownAccountException();
        }
        if(!user.getPassword().equals(password)){
            throw new IncorrectCredentialsException();
        }

        return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),"");
    }
}
