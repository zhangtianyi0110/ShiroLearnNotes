package com.zty.realm;

import com.zty.dao.UserDao;
import com.zty.pojo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    @Resource
    private UserDao userDao;


    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.从主体传过来的授权信息中，获取用户名
        String userName = (String) principalCollection.getPrimaryPrincipal();
        //2.通过用户名到数据库中获取角色权限数据
        Set<String> roles = getRoles(userName);
        Set<String> permissions = getPermissions(userName);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.从主体传过来的认证信息中，获取用户名
        String userName = (String) authenticationToken.getPrincipal();
        //2.通过用户名到数据库中获取凭证(map模拟)
        String password = getPasswordByUserName(userName);
        //判断用户是否存在
        if(password==null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName,password,"customRealm");
        //返回authenticationInfo对象前设置盐
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userName));
        return authenticationInfo;
    }

    private String getPasswordByUserName(String userName) {
        User user = userDao.getUserByUserName(userName);
        if(user!=null){
            return user.getPassword();
        }
        return null;
    }

    private Set<String>getRoles(String userName){
        Set<String> roleSet = new HashSet<String>(userDao.queryRolesByUserName(userName));
        return roleSet;
    }
    private Set<String> getPermissions(String userName) {
        Set<String> permissionSet = new HashSet<String>();
        Set<String> roleSet = getRoles(userName);
        for (String roleName : roleSet) {
            permissionSet.addAll(userDao.queryPermsByRoleName(roleName));
        }
        return permissionSet;
    }

    public static void main(String[] args) {
        System.out.println(new Md5Hash("123456","zty").toString());
    }

}
