package cn.jobseek.web;

import cn.jobseek.vo.JsonResult;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public JsonResult doHandleRuntimeException(RuntimeException e){
        e.printStackTrace();
        return JsonResult.fail(e);
    }

    @ExceptionHandler(ShiroException.class)
    public JsonResult doHandleShiroException(ShiroException e){
        JsonResult result = null;
        if (e instanceof UnknownAccountException){
            result = JsonResult.fail(e, "账号不存在");
        }
        if (e instanceof LockedAccountException){
            result = JsonResult.fail(e,"账号已经被禁用");
        }
        if (e instanceof IncorrectCredentialsException){
            result = JsonResult.fail(e,"密码不正确");
        }
        e.printStackTrace();
        return result;
    }
}
