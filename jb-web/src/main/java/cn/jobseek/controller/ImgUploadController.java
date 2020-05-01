package cn.jobseek.controller;

import cn.jobseek.service.ImgUploadService;
import cn.jobseek.util.CookieUtil;
import cn.jobseek.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RestController
@RequestMapping("/img/")
public class ImgUploadController {

    @Autowired
    private ImgUploadService imgUploadService;



    @RequestMapping("avatar")
    public JsonResult doUploadImg(MultipartFile uploadImg, HttpServletRequest request) throws IOException {
        String token = CookieUtil.getCookieValue(request, "JB_TOKEN");
        imgUploadService.doUploadImg(uploadImg,token);
        return JsonResult.message("上传成功");
    }

    @RequestMapping("avatar_cmp")
    public JsonResult doUploadCmpImg(MultipartFile uploadCompImg, HttpServletRequest request) throws IOException {
        String token = CookieUtil.getCookieValue(request, "JB_TOKEN");
        imgUploadService.doUploadCmpImg(uploadCompImg,token);
        return JsonResult.message("上传成功");
    }




}
