package com.cyyz.shirocas.shiro.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.stereotype.Component;

public class MysessionListener implements SessionListener {

    public void onStart(Session session) {
        System.out.println("Create session"+session.getId());
    }

    public void onStop(Session session) {
        System.out.println("Stop session"+session.getId());
    }

    public void onExpiration(Session session) {
        System.out.println("过期时触发"+session.getId());
    }
}
