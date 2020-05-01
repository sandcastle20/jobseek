package cn.jobseek.controller;

import cn.jobseek.service.SysPositionService;
import cn.jobseek.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/job/")
public class SysPositionController {

    @Autowired
    private SysPositionService sysPositionService;

    @RequestMapping("doFindPageObjects")
    public JsonResult doFindPageObjects(String positionName,Integer pageCurrent){
        return JsonResult.success(sysPositionService.findPageObjects(positionName,pageCurrent));
    }

    @RequestMapping("doCheckObjects")
    public JsonResult doCheckObjects(Long ...ids){
        sysPositionService.checkObjects(ids);
        return JsonResult.message("审核成功");
    }


}
