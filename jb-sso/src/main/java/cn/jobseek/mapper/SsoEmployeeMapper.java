package cn.jobseek.mapper;

import cn.jobseek.pojo.Company;
import cn.jobseek.pojo.Employee;
import cn.jobseek.pojo.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

public interface SsoEmployeeMapper {

    int saveEmployeeObject(Employee employee);

    @Select("select * from jb_employee where user_id = #{id}")
    Employee doSelectByUserId(Long id);

    int updateEmployee(Employee employee);

    @Update("update jb_employee set avatar = #{rPath},modified_time=now() where user_id = #{userId}")
    int updateAvatarByUserId(Long userId,String rPath);

}
