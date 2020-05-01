package cn.jobseek.service.impl;

import cn.jobseek.pojo.User;
import cn.jobseek.service.DubboCompanyService;
import cn.jobseek.service.DubboEmployeeService;
import cn.jobseek.service.ImgUploadService;
import cn.jobseek.util.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@PropertySource("classpath:/properties/image.properties")
public class ImgUploadServiceImpl implements ImgUploadService {

    @Value("${image.fileDirPath}")
    private String fileDirPath;	// = "E:/JB_IMAGE/"; //定义文件根目录
    @Value("${image.urlPath}")
    private String urlPath;	// = "http://image.jb.com/";


    //调用sso-manager的服务
    @Reference(timeout = 3000,check = true,loadbalance = "roundrobin")
    private DubboEmployeeService dubboEmployeeService ;

    //调用sso-manager的服务
    @Reference(timeout = 3000,check = true,loadbalance = "roundrobin")
    private DubboCompanyService dubboCompanyService;

    /**
     * 上传图片
     * @param uploadImg
     * @return
     */
    @Transactional
    @Override
    public boolean doUploadImg(MultipartFile uploadImg,String token) {
        String fileName = doCheckImg(uploadImg);
        try {
            String url = uploadImg(uploadImg, fileName);
            //五 更新头像
            dubboEmployeeService.updateAvatarByUserId(token,url);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    //上传图片
    private String uploadImg(MultipartFile uploadImg, String fileName) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(uploadImg.getInputStream());
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        Assert.isServiceValid(width ==0 || height ==0,"上传图片失效，非正常操作");

        //三 说明图片类型正确 	分目录存储	date转化为yyyy/MM/dd
        String datePath = new SimpleDateFormat("yyyy/MM/dd/")
                .format(new Date());
        //D:/JB_IMAGE/yyyy/MM/dd/a.jpg
        String fileLocalPath = fileDirPath + datePath;
        File dirFile = new File(fileLocalPath);
        if (!dirFile.exists()) {
            //创建新目录
            dirFile.mkdirs();
        }

        //四、实现文件上传 UUID+图片后缀 fsdfds-dfds-fdsfd
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //abc.jpg
        int index = fileName.lastIndexOf(".");
        String fileType = fileName.substring(index);	//substring含头不含尾 这里含"."
        String realFileName = uuid + fileType;

        //准备文件上传的全路径 本地磁盘路径	D:\JB_IMAGE\2020\02\12\+uuid.jpg
        String realFilePath = fileLocalPath + realFileName;
        //文件上传
        uploadImg.transferTo(new File(realFilePath));
        //准备虚拟路径	http://image.jb.com/yyyy/MM/dd/uuid.jpg
        return urlPath  + datePath + realFileName;
    }

    //实现图片校验
    private String doCheckImg(MultipartFile uploadImg) {
        //一.实现图片校验
        //1.1 获取图片名称  判断字符串的类型
        String fileName = uploadImg.getOriginalFilename();
        fileName = fileName.toLowerCase();	//全部转化为小写
        //1.2利用正则表达式判断是否为图片
        Boolean isImgType = !fileName.matches("^.+\\.(jpg|png|gif)$");
        Assert.isServiceValid(isImgType,"该文件类型不是图片");
        //二:如何防止是恶意程序?  可以根据宽度和高度实现图片校验.
        //首先准备一个图片的容器.将字节信息添加到容器中,获取宽高.
        return fileName;
    }

    /**
     * 上传企业图片
     * @param uploadCompImg
     * @param token
     * @return
     */
    @Transactional
    @Override
    public boolean doUploadCmpImg(MultipartFile uploadCompImg, String token) {
        String fileName = doCheckImg(uploadCompImg);
        try {
            String url = uploadImg(uploadCompImg, fileName);
            //五 更新头像
            dubboCompanyService.updateAvatarByUserId(token,url);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
