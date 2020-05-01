package cn.jobseek.mapper;

import cn.jobseek.pojo.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    //基于角色名字，查询总记录数
    int getRowCount(@Param("roleName") String roleName);

    //基于角色名字，查询角色记录
    List<SysRole> doFindObjects(@Param("roleName") String roleName,
                                @Param("startIndex") Integer startIndex,
                                @Param("pageSize") Integer pageSize);

    //保存角色信息
    int saveObjects(SysRole entity);

    //根据id查询角色信息
    @Select("select id,name,note,created_time,modified_time,created_time,created_user from jb_role where id = #{id}")
    SysRole doFindObjectById(@Param("id") Long id);

    //根据id修改角色信息
    int updateObject(SysRole entity);

    //根据id删除记录
    int deleteObject(@Param("id") Long id);

    @Select("select * from jb_role")
    List<SysRole> findRoles();
}
