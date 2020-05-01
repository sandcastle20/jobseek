package cn.jobseek.controller;


import cn.jobseek.pojo.Company;
import cn.jobseek.pojo.Employee;
import cn.jobseek.pojo.User;
import cn.jobseek.service.DubboUserService;
import cn.jobseek.util.Assert;
import cn.jobseek.vo.JsonResult;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/user/")
public class DubboUserController {

    //调用sso-manager的服务
    @Reference(timeout = 3000,check = true,loadbalance = "roundrobin")
    private DubboUserService dubboUserService;

    //注册user
    @RequestMapping("doRegisterUser")
    public JsonResult doRegisterUser(Employee employee, User user, Long roleId){
        dubboUserService.doRegisterUser(employee, user, roleId);
        return JsonResult.message("注册成功");
    }

    //注册company
    //http://www.jb.com/user/doRegisterCompany
    @RequestMapping("doRegisterCompany")
    public JsonResult doRegisterCompany(Company company, User user, Long roleId){

        dubboUserService.doRegisterCompany(company,user,roleId);
        return JsonResult.message("注册成功");

    }

    @RequestMapping("doLogin")
    public JsonResult doLogin(String account, String pwd, HttpServletResponse response){
        String token = dubboUserService.doLogin(account, pwd);
        Assert.isArgumentValid(account==null||StringUtils.isEmpty(account),"账号不能为空");
        Assert.isArgumentValid(pwd==null||StringUtils.isEmpty(pwd),"账号不能为空");
        if (StringUtils.isEmpty(token)){    //token->令牌为空
            return JsonResult.fail();
        }
        //token有值，保存到cookie中
        Cookie cookie = new Cookie("JB_TOKEN", token);
        cookie.setDomain("jb.com");
        cookie.setPath("/");
        cookie.setMaxAge(7*24*3600);//7天
        response.addCookie(cookie);
        return  JsonResult.message("登录成功");
    }





}
