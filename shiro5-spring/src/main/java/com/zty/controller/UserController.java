package com.zty.controller;

import com.zty.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {


    @RequestMapping(value="/subLogin",method = RequestMethod.POST,
    produces = "application/json;charset=utf-8")
    @ResponseBody
    public String subLogin(User user){

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try {
            subject.login(token);
        }catch (AuthenticationException e){
            return e.getMessage();
        }
        if(subject.hasRole("admin")){
            return "有admin权限";
        }

        return "无admin权限";
    }

    @RequestMapping(value = "/testRole",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles("admin")//必须具备admin角色才能访问
    public String testRole(){
        return "testRole success";
    }

    @RequestMapping(value = "/testRole1",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles("admin1")//必须具备admin1角色才能访问
    public String testRole1(){
        return "testRole1 success";
    }

    @RequestMapping(value = "/testRole2",method = RequestMethod.GET)
    @ResponseBody
    @RequiresRoles({"admin","admin1"})//必须具备admin1角色才能访问
    public String testRole2(){
        return "testRole2 success";
    }

    @RequestMapping(value = "/testPerms",method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("user:get")//必须具备admin角色才能访问
    public String testPerms(){
        return "testPerms success";
    }

    @RequestMapping(value = "/testPerms1",method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions({"user:get","user:get1"})//必须具备admin角色才能访问
    public String testPerms1(){
        return "testPerms1 success";
    }



    //拦截器/过滤器调用

    @RequestMapping(value = "/testPerms2",method = RequestMethod.GET)
    @ResponseBody
    public String testPerms2(){
        return "testPerms2 success";
    }

    @RequestMapping(value = "/testPerms3",method = RequestMethod.GET)
    @ResponseBody
    public String testPerms3(){
        return "testPerms3 success";
    }

    @RequestMapping(value = "/testRole3",method = RequestMethod.GET)
    @ResponseBody
    public String testRole3(){
        return "testRole3 success";
    }

    @RequestMapping(value = "/testRole4",method = RequestMethod.GET)
    @ResponseBody
    public String testRole4(){
        return "testRole4 success";
    }


    @RequestMapping(value = "/testCustomRole",method = RequestMethod.GET)
    @ResponseBody
    public String testCustomRole(){
        return "testCustomRole success";
    }
}
