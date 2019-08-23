package com.zty.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class RoleOrFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        Subject subject = getSubject(request, response);
        String[] roles = (String[]) o;
        if(roles==null&&roles.length==0){//自定义roles为空，通过
            return true;
        }
        for (String role : roles) {//只要有其中一个角色就有权限
            if(subject.hasRole(role)){
                return true;
            }
        }
        return false;//一个都不满足不通过
    }
}
