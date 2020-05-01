package cn.jobseek.service.impl;

import cn.jobseek.service.DubboResumeService;
import cn.jobseek.service.ResumeUploadService;
import cn.jobseek.util.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@PropertySource("classpath:/properties/resume.properties")
public class ResumeUploadServiceImpl implements ResumeUploadService {

    @Value("${resume.fileDirPath}")
    private String fileDirPath;	// = "E:/JB_IMAGE/"; //定义文件根目录
    @Value("${resume.urlPath}")
    private String urlPath;	// = "http://image.jb.com/";

    @Reference(timeout = 3000,check = true,loadbalance = "roundrobin")
    private DubboResumeService dubboResumeService;

    /**
     * 上传简历
     * @param uploadResume
     * @param token
     */
    @Transactional
    @Override
    public void doUploadResume(MultipartFile uploadResume, String token) {
        //校验文件
        Assert.isArgumentValid(uploadResume.isEmpty(),"文件不见了,请重新上传");
        String resumeName = uploadResume.getOriginalFilename(); //获取文件名
        resumeName = resumeName.toLowerCase();
        boolean isResumeType = !resumeName.matches("^.+\\.(|doc|pdf|docx|wps)$");
        Assert.isArgumentValid(isResumeType,"上传的简历不是doc、docx、pdf、wps类型");

        try {
            //定义存储路径 datepath
            String datepath = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
            String fileLocalPath = fileDirPath + datepath; //路径+日期
            //创建目录
            File file = new File(fileLocalPath);
            if (!file.exists())
                file.mkdirs();//创建全部目录
            //创建文件
            String uuid = UUID.randomUUID().toString().replace("-", "");    //文件名
            String resumeType = resumeName.substring(resumeName.lastIndexOf("."));
            String realResumeName = uuid + resumeType ;
            //准备上传的全路径
            String realResumePath = fileLocalPath + realResumeName;
            //真正的上传操作
            uploadResume.transferTo(new File(realResumePath));
            //准备虚拟路径
            String url = urlPath + datepath +realResumeName;

            //更新jb_resume表
            dubboResumeService.saveResume(token, url,resumeName);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
