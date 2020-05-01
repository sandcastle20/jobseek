package cn.jobseek.controller;

import cn.jobseek.service.DubboJobService;
import cn.jobseek.util.CookieUtil;
import cn.jobseek.vo.IndexPositionItem;
import cn.jobseek.vo.IndexPositionPageObject;
import cn.jobseek.vo.JobDetail;
import cn.jobseek.vo.JsonResult;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/job/")
public class DubboJobController {

    @Reference(timeout = 3000,check = true,loadbalance = "roundrobin")
    private DubboJobService dubboJobService ;

    @RequestMapping("findJobDetail")
    public JsonResult findJobDetail(Long positionId){
        JobDetail jobDetail = dubboJobService.findJobDetail(positionId);
        return JsonResult.success(jobDetail);
    }
    @RequestMapping("deleteOnejob")
    public JsonResult deleteOnejob(Long positionId){
        dubboJobService.deleteOnejob(positionId);
        return JsonResult.message("删除成功");
    }

    @RequestMapping("doApplyForPosition")
    public JsonResult doApplyForPosition(Long positionId, HttpServletRequest request){
        String token = CookieUtil.getCookieValue(request, "JB_TOKEN");
        dubboJobService.doApplyForPosition(positionId, token);
        return JsonResult.success("申请成功");
    }

    @RequestMapping("doFindIndexPositionItem")
    public JsonResult doFindIndexPositionItem(String positionName,String address){
        IndexPositionPageObject positionItems = dubboJobService.doFindIndexPositionItem(positionName, address);
        return JsonResult.success(positionItems);
    }


}
