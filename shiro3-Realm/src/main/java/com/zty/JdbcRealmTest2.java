package com.zty;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;


public class JdbcRealmTest2 {

    //数据源
    DruidDataSource dataSource = new DruidDataSource();
    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }

    @Test
    public void testJdbcRealm2(){
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        //设置权限开关
        jdbcRealm.setPermissionsLookupEnabled(true);

        //自定义sql语句查询自定义表
        String sql = "select password from test_user where user_name = ?";
        jdbcRealm.setAuthenticationQuery(sql);//认证查询
        String sql2 =  "select role_name from test_user_role where user_name = ?";
        jdbcRealm.setUserRolesQuery(sql2);//角色授权查询
        String sql3 = "select permission from test_roles_permissions where role_name = ?";
        jdbcRealm.setPermissionsQuery(sql3);//权限查询

        //1.构建SecurityManager
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //2.设置realm
        securityManager.setRealm(jdbcRealm);
        //3.主体提交认证
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        //4.构造token
        UsernamePasswordToken token = new UsernamePasswordToken("ttt","123456");
        //5.验证登录
        subject.login(token);
        System.out.println("isAuthenticated:"+subject.isAuthenticated());
        //6.验证角色权限
        subject.checkRole("user");
        subject.checkPermission("user:get");
        //6.登出操作
        subject.logout();
        System.out.println("isAuthenticated:"+subject.isAuthenticated());
    }
}
