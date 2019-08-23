package com.zty;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;


public class JdbcRealmTest {

    //数据源
    DruidDataSource dataSource = new DruidDataSource();
    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }

    @Test
    public void testJdbcRealm(){
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        //设置权限开关
        jdbcRealm.setPermissionsLookupEnabled(true);

        //1.构建SecurityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //2.设置realm
        securityManager.setRealm(jdbcRealm);
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
        subject.checkRoles("admin","suer");
        subject.checkPermission("user:delete");
        subject.checkPermissions("user:delete","user:get","user:post");
        //6.登出操作
        subject.logout();
        System.out.println("isAuthenticated:"+subject.isAuthenticated());
    }
}
