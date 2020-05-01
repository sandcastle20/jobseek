package cn.jobseek.service;

import org.springframework.web.multipart.MultipartFile;

public interface ResumeUploadService {


    void doUploadResume(MultipartFile uploadResume, String token);
}
