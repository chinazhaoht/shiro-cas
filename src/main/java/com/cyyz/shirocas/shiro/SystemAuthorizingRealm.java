package com.cyyz.shirocas.shiro;

import com.cyyz.shirocas.beans.User;
import com.cyyz.shirocas.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2016/1/26.
 */
public class SystemAuthorizingRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;

        User user = userService.findByUsername(token.getUsername());

        if(user != null){
            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),"");
        }
        return null;
    }
}
