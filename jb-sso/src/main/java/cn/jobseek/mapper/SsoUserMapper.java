package cn.jobseek.mapper;

import cn.jobseek.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SsoUserMapper {

    /**
     * 保存user.account账号+user.pwd密码+user.email邮箱+user.mobile手机
     * @param user
     */
    int saveObject(User user);

    @Select("select * from jb_user where account =#{account} ")
    User selectByAccount(@Param("account") String account);

    @Select("select * from jb_user where account = #{account} and pwd = #{md5Pwd}")
    User selectByAccountAndPwd(@Param("account")String account,
                               @Param("md5Pwd")String md5Pwd);

    @Select("select account from jb_user where id = #{userId}")
    String selectAccountByUserId(Long userId);

    @Select("select * from jb_user where id = #{id}")
    User selectByUserId(Long id);

    @Update("update jb_user set email = #{email},mobile = #{mobile},modified_time = now()  where id = #{id}")
    int updateContactWay(User user);
}
