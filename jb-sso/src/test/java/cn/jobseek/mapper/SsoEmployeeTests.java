package cn.jobseek.mapper;

import cn.jobseek.pojo.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SsoEmployeeTests {

    @Autowired
    private SsoEmployeeMapper ssoEmployeeMapper;

    @Test
    public void saveObject(){
        Employee employee = new Employee();
        employee.setUserId((long) 1001)
                .setUserName("测试注册")
                .setSex(true);
        ssoEmployeeMapper.saveEmployeeObject(employee);
    }

}
