package cn.jobseek.mapper;

import cn.jobseek.pojo.User;
import cn.jobseek.vo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SysUserMapper {

    //基于username获取总记录数
    int getRowCount(@Param("username") String username);

    //基于username,startIndex,pageSize获取user数据
    List<SysUser> findPageObjects(@Param("username") String username,
                                  @Param("startIndex") Integer startIndex,
                                  @Param("pageSize") Integer pageSize);

    @Update("update jb_user set valid = #{valid} where id = #{id}")
    int validById(Integer id, Integer valid);

    //插入user表信息
    int insertObject(User entity);

    //基于userId查询use信息
    @Select("select * from jb_user where id = #{id}")
    User doFindObjectById(@Param("id") Long id);

    //更新use表信息
    int updateObject(User user);


    //删除user信息（基于userId）
    int deleteObject(@Param("userId")Long userId);

    //shiro认证基于用户名获取记录
    @Select("select * from jb_user where account=#{account}")
    User findUserByUsername(@Param("account") String account);

    //修改密码
    @Update("update jb_user set pwd = #{pwd},salt = #{salt},modified_time = now() where id = #{id}")
    int updatePwd(@Param("pwd") String pwd,
                  @Param("salt") String salt,
                  @Param("id") Long id);
}
