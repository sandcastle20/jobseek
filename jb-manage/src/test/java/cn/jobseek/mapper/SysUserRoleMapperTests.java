package cn.jobseek.mapper;

import cn.jobseek.pojo.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysUserRoleMapperTests {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Test
    public void updateObjectAfterDelete(){
        int i = sysUserRoleMapper.updateObjectAfterDelete((long) 12);
        System.out.println("影响行数:"+i);
    }
}
