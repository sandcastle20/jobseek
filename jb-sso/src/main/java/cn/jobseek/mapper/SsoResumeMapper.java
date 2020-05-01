package cn.jobseek.mapper;

import cn.jobseek.pojo.Resume;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

public interface SsoResumeMapper {

    @Select("select * from jb_resume where employee_id = #{empId}")
    Resume selectByEmpId(Long empId);

    int updateResumeUrl(Resume resume);

    int insertResume(Resume resume);
}
