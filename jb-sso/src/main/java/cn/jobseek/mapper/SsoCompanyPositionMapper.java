package cn.jobseek.mapper;

import cn.jobseek.pojo.Company;
import cn.jobseek.pojo.CompanyPosition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SsoCompanyPositionMapper {


    int insertObject(CompanyPosition companyPosition);

    @Select("select * from jb_company_position where company_id = #{id}")
    List<CompanyPosition> doSelectByCompId(Long id);

    @Select("select * from jb_company_position where position_id = #{id}")
    CompanyPosition selectByPositionId(Long id);

    int deleteOnejob(Long positionId);
}
