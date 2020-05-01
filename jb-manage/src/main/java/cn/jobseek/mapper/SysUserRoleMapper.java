package cn.jobseek.mapper;

import cn.jobseek.pojo.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SysUserRoleMapper {


    void insertObject(UserRole userRole);

    @Select("select role_id from jb_user_role where user_id = #{userId}")
    UserRole doFindObjectById(@Param("userId") Long userId);

    @Update("update jb_user_role set role_id = #{roleId} where user_id = #{userId}")
    int updateObject(@Param("userId") Long userId,@Param("roleId")  Long roleId);

    //基于roleId更新user_role表，使得存在roleId的记录都变成null
    int updateObjectAfterDelete(@Param("roleId") Long roleId);

    //删除useid对应的记录
    void deleteObjectByUserId(@Param("userId") Long userId);
}
