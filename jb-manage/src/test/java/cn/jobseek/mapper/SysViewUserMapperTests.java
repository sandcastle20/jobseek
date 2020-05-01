package cn.jobseek.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysViewUserMapperTests {

    @Autowired
    private SysViewUserMapper sysViewUserMapper;

    @Test
    public void findRoleName(){
        boolean roleName = sysViewUserMapper.findRoleName((long) 8);
        System.out.println(roleName);

    }
}
