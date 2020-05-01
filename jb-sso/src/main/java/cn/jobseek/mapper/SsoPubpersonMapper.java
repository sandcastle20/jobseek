package cn.jobseek.mapper;

import cn.jobseek.pojo.Pubperson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

public interface SsoPubpersonMapper {

    int insertPubperson(Pubperson pubperson);

    @Select("select * from jb_pubperson where id = #{pubpersonId}")
    Pubperson selectByPubpersonId(Long pubpersonId);

    int deleteOnejob(Long pubpersonId);
}
