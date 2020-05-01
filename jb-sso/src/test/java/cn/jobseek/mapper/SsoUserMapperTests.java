package cn.jobseek.mapper;

import cn.jobseek.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class SsoUserMapperTests {

    @Autowired
    private SsoUserMapper userMapper;

    @Test
    public void saveObject(){
        User user = new User();
        user.setAccount("test-sso")
                .setPwd("12343243")
                .setValid(true);
        int i = (int) userMapper.saveObject(user);
        log.info("影响行数："+i);
    }

    @Test
    public void selectByAccount(){
        String account = "施川强";
        User user = userMapper.selectByAccount(account);
        System.out.println(user);
    }
}
