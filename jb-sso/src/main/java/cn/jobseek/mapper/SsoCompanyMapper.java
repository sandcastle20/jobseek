package cn.jobseek.mapper;

import cn.jobseek.pojo.Company;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SsoCompanyMapper {

    int saveCompanyObject(Company company);

    @Select("select * from jb_company where user_id = #{userId}")
    Company doSelectByUserId(Long userId);

    @Select("select * from jb_company where id = #{companyId}")
    Company selectCompanyById(Long companyId);

    int updateCompany(Company company);

    @Update("update jb_company set email = #{email},phone = #{phone},modified_time = now() where id = #{id}")
    int updateCompanyContactWay(Company company);

    @Update("update jb_company set avatar = #{path},modified_time = now() where user_id = #{id}")
    int updateAvatarByUserId(Long id, String path);

    int updateCompanyForPushPosition(Company company);

    @Select("select * from jb_company where id = #{companyId}")
    Company selectByCompanyId(Long companyId);

//    int insertCompany(Company company);
}
