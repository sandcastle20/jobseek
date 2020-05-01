package cn.jobseek.controller;

import cn.jobseek.pojo.User;
import cn.jobseek.service.SysUserService;
import cn.jobseek.vo.JsonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/")
public class SysUserController{

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("doLogin")
    public JsonResult doLogin(String account,String pwd,boolean isRememberMe){
        //1.封装用户名+密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(account, pwd);
        //2.设置记住我
        usernamePasswordToken.setRememberMe(isRememberMe);
        //3.提交用户名和密码进行认证
        Subject subject = SecurityUtils.getSubject();//构建主体
        subject.login(usernamePasswordToken);////此时会将token提交给securityManager
        return JsonResult.message("login ok");
    }

    @RequestMapping("/doUpdatePassword")
    public JsonResult doUpdatePassword(String pwd,String newPwd,String cfgPwd){
        sysUserService.doUpdatePassword(pwd, newPwd, cfgPwd);
        return JsonResult.message("修改密码成功");
    }

    @RequestMapping("/doFindPageObjects")
    public JsonResult doFindPageObjects(String username,Integer pageCurrent){
        return JsonResult.success(sysUserService.doFindPageObjects(username,pageCurrent));
    }

    @RequestMapping("/doValidById")
    public JsonResult doValidById(Integer id,Integer valid){
        sysUserService.doValidById(id,valid);
        return JsonResult.message("成功");
    }


    @RequestMapping("/doSaveObject")
    public JsonResult doSaveObject(User user, Long roleId){
        sysUserService.doSaveObject(user,roleId);
        return JsonResult.message("保存成功");
    }

    @RequestMapping("/doFindObjectById")
    public JsonResult doFindObjectById(Long id){
        Map<String, Object> map = sysUserService.doFindObjectById(id);
        return JsonResult.success(map);
    }

    @RequestMapping("/doUpdateObject")
    public JsonResult doUpdateObject(User user,Long roleId){
        sysUserService.doUpdateObject(user,roleId);
        return JsonResult.message("修改成功");
    }

    @RequestMapping("/doDeleteObject")
    public JsonResult doDeleteObject(Long id){
        sysUserService.doDeleteObject(id);
        return JsonResult.message("删除成功");
    }

}
