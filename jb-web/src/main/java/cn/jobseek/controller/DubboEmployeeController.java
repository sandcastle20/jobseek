package cn.jobseek.controller;


import cn.jobseek.pojo.Employee;
import cn.jobseek.pojo.Resume;
import cn.jobseek.pojo.User;
import cn.jobseek.service.DubboEmployeeService;
import cn.jobseek.service.DubboUserService;
import cn.jobseek.util.CookieUtil;
import cn.jobseek.vo.JsonResult;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/employee/")
public class DubboEmployeeController {

    //调用sso-manager的服务
    @Reference(timeout = 3000,check = true,loadbalance = "roundrobin")
    private DubboEmployeeService dubboEmployeeService ;

    @RequestMapping("doFindEmployee")
    public JsonResult doFindEmployee(String token){
        Employee employee = dubboEmployeeService.doFindEmployee(token);
        return JsonResult.success(employee);
    }

    @RequestMapping("doUpdateEmployee")
    public JsonResult doUpdateEmployee(Employee employee){
        dubboEmployeeService.doUpdateEmployee(employee);
        return JsonResult.message("更新信息成功");
    }

    @RequestMapping("doFindContactWay")
    public JsonResult doFindContactWay(HttpServletRequest request){
        String token = CookieUtil.getCookieValue(request, "JB_TOKEN");
        User user = dubboEmployeeService.doFindContactWay(token);
        return JsonResult.success(user);
    }

    @RequestMapping("saveContactWay")
    public JsonResult saveContactWay(User user,HttpServletRequest request){
        String token = CookieUtil.getCookieValue(request, "JB_TOKEN");
        dubboEmployeeService.saveContactWay(user,token);
        return JsonResult.message("修改成功");
    }


}
