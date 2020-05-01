package cn.jobseek.controller;

import cn.jobseek.mapper.SsoUserRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRoleMapper {

    @Autowired
    private SsoUserRoleMapper userRoleMapper;

    @Test
    public void test(){
        Long res = userRoleMapper.doSelectByUserId((long) 2);
        System.out.println(res);
    }
}
