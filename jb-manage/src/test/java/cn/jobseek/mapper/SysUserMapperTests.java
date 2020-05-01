package cn.jobseek.mapper;

import cn.jobseek.vo.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SysUserMapperTests {

    @Autowired
    public SysUserMapper sysUserMapper;

    @Test
    public void findPageObjects(){
        List<SysUser> pageObjects = sysUserMapper.findPageObjects(null, 0, 2);
        pageObjects.forEach((item)->{
            System.out.println(item);
        });
    }

    @Test
    public void getRowCount(){
        int rowCount = sysUserMapper.getRowCount(null);
        System.out.println("总记录数"+rowCount);
    }


}
