package cn.jobseek.controller;

import cn.jobseek.service.ImgUploadService;
import cn.jobseek.service.ResumeUploadService;
import cn.jobseek.util.CookieUtil;
import cn.jobseek.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequestMapping("/file/")
@RestController
public class ResumeUploadController {

    @Autowired
    private ResumeUploadService resumeUploadService;



    @RequestMapping("resume")
    public JsonResult doUploadResum(MultipartFile uploadResume,HttpServletRequest request){
        String token = CookieUtil.getCookieValue(request, "JB_TOKEN");
        resumeUploadService.doUploadResume(uploadResume,token);
        return JsonResult.message("上传成功");
    }


}
