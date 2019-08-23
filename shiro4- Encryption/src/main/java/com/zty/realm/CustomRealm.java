package com.zty.realm;

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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    Map<String,String>userMap = new HashMap<String, String>();
    {
        //密码加盐用md5算法,盐为“salt”
        userMap.put("zty",new Md5Hash("123456","salt").toString());
        super.setName("customRealm");
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.从主体传过来的授权信息中，获取用户名
        String userName = (String) principalCollection.getPrimaryPrincipal();
        //2.通过用户名到数据库中获取角色权限数据(set模拟)
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
        String password = userMap.get(userName);
        //判断用户是否存在
        if(password==null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName,password,"customRealm");
        //返回authenticationInfo对象前设置盐
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("salt"));
        return authenticationInfo;
    }
    private Set<String>getRoles(String userName){
        Set<String> roleSet = new HashSet<String>();
        roleSet.add("admin");
        roleSet.add("user");
        return roleSet;
    }
    private Set<String> getPermissions(String userName) {
        Set<String> permissionSet = new HashSet<String>();
        permissionSet.add("user:delete");
        permissionSet.add("user:get");
        return permissionSet;
    }

}
