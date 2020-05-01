package cn.jobseek.mapper;

import cn.jobseek.pojo.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class SsoUserRoleMapperTests {

    @Autowired
    private SsoUserRoleMapper ssoUserRoleMapper;

    @Test
    public void saveObject(){
        UserRole userRole = new UserRole();
        userRole.setUserId((long) 1001)
                .setRoleId((long) 2)
                .setCreatedTime(new Date())
                .setModifiedTime(userRole.getCreatedTime())
                .setCreatedUser("test");
        ssoUserRoleMapper.saveObject(userRole);
    }
}
