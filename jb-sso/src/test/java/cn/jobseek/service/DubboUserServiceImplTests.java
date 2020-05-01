package cn.jobseek.service;

import cn.jobseek.pojo.Employee;
import cn.jobseek.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class DubboUserServiceImplTests {

    @Autowired
    private DubboUserService dubboUserService;

    @Test
    public void doRegisterUser(){
        User user = new User();
        Employee employee = new Employee();
        Long roleId = 2L;
        user.setAccount("100001")
                .setPwd("123456")
                .setMobile("17621835502")
                .setEmail("17621835502@163.com");
        employee.setUserName("测试登录").setSex(true);
        dubboUserService.doRegisterUser(employee,user,roleId);
    }

}
