package cn.jobseek.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImgUploadService {

    boolean doUploadImg(MultipartFile uploadImg,String token);

    boolean doUploadCmpImg(MultipartFile uploadCompImg, String token);
}
