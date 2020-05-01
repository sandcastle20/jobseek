package cn.jobseek.mapper;

import cn.jobseek.pojo.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

public interface SsoUserRoleMapper {

    void saveObject(UserRole userRole);

    @Select("select role_id from jb_user_role where user_id = #{id}")
    Long doSelectByUserId(Long id);
}
