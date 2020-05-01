package cn.jobseek.controller;

import cn.jobseek.pojo.Company;
import cn.jobseek.pojo.Position;
import cn.jobseek.pojo.Pubperson;
import cn.jobseek.pojo.User;
import cn.jobseek.service.DubboCompanyService;
import cn.jobseek.util.Assert;
import cn.jobseek.util.CookieUtil;
import cn.jobseek.vo.JsonResult;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/company/")
public class DubboCompanyController {

    //调用sso-manager的服务
    @Reference(timeout = 3000,check = true,loadbalance = "roundrobin")
    private DubboCompanyService dubboCompanyService;

    @RequestMapping("doFindCompany")
    public JsonResult doFindCompany(Long companyId){
        Company company = dubboCompanyService.doFindCompany(companyId);
        return JsonResult.success(company);
    }

    //http://www.jb.com/company/saveCompany
    @RequestMapping("doSaveCompany")
    public JsonResult doSaveCompany(Company company){
        dubboCompanyService.doSaveCompany(company);
        return JsonResult.message("保存成功");
    }

    @RequestMapping("doFindCompanyContactWay")
    public JsonResult doFindCompanyContactWay(HttpServletRequest request){
        String token = CookieUtil.getCookieValue(request, "JB_TOKEN");
        Assert.isArgumentValid(token == null,"令牌找不到，请重新登录");
        User user = dubboCompanyService.doFindCompanyContactWay(token);
        return JsonResult.success(user);
    }

    @RequestMapping("doSaveCompanyContactWay")
    public JsonResult doSaveCompanyContactWay(HttpServletRequest request,User user,Long companyId){
        String token = CookieUtil.getCookieValue(request, "JB_TOKEN");
        Assert.isArgumentValid(token == null,"令牌找不到，请重新登录");
        dubboCompanyService.doSaveCompanyContactWay(token,user,companyId);
        return JsonResult.message("修改成功");
    }

    @RequestMapping("doPushPosition")
    public JsonResult doPushPosition(HttpServletRequest request,Long compId, Position position, Pubperson pubperson){
        String token = CookieUtil.getCookieValue(request, "JB_TOKEN");
        Assert.isArgumentValid(token == null,"令牌找不到，请重新登录");
        dubboCompanyService.doPushPosition(token,compId,position,pubperson);
        return JsonResult.message("发布成功");
    }

}
