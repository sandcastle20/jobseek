package cn.jobseek.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysViewUserMapper {
    @Select("select isnull(name) from jb_sys_user WHERE id=#{id}")
    boolean findRoleName(@Param("id") Long id);
}
