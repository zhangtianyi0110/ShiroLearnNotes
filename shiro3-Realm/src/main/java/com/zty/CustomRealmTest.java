package com.zty;

import com.zty.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class CustomRealmTest {

    @Test
    public void testCustomRealm(){
        CustomRealm customRealm = new CustomRealm();

        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(customRealm);

        SecurityUtils.setSecurityManager(securityManager);
        Subject subject =  SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zty", "123456");
        subject.login(token);

        System.out.println("isAuthenticated:"+subject.isAuthenticated());

        subject.checkRoles("admin","user");
        subject.checkPermissions("user:delete","user:get");

        subject.logout();
    }
}
