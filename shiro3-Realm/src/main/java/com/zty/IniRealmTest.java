package com.zty;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class IniRealmTest {
    @Test
    public void testIniRealm(){
        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        //1.构建SecurityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //2.设置realm
        securityManager.setRealm(iniRealm);
        //3.主体提交认证
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        //4.构造token
        UsernamePasswordToken token = new UsernamePasswordToken("zty","123456");
        //5.验证登录
        subject.login(token);
        System.out.println("isAuthenticated:"+subject.isAuthenticated());
        //6.验证角色权限
        subject.checkRole("admin");
        subject.checkPermission("user:delete");
//        subject.checkRole("admin1");
//        subject.checkPermission("user1:delete");
        //6.登出操作
        subject.logout();
        System.out.println("isAuthenticated:"+subject.isAuthenticated());
    }
}
