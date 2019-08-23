package com.zty;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {

    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("zty","123456","admin","user");
    }

    @Test
    public void testAuthentication(){
        //1.构建SecurityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //2.设置realm
        securityManager.setRealm(simpleAccountRealm);

        //3.主体授权
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        //4.构造token
        UsernamePasswordToken token = new UsernamePasswordToken("zty","123456");

        //5.验证登录
        subject.login(token);
        subject.checkRoles("admin","user");
        System.out.println("isAuthenticated:"+ subject.isAuthenticated());

        //6.验证角色
        subject.checkRole("admin");
        subject.checkRoles("admin","user");
        subject.checkRoles("admin","user1");

        //6.登出操作
        subject.logout();
        System.out.println("isAuthenticated:"+subject.isAuthenticated());


    }

}
