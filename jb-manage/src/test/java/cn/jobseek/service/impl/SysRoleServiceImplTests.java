package cn.jobseek.service.impl;

import cn.jobseek.pojo.SysRole;
import cn.jobseek.service.SysRoleService;
import cn.jobseek.vo.PageObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@Slf4j
@SpringBootTest
public class SysRoleServiceImplTests {

    @Autowired
    private SysRoleService sysRoleService;

    //ok
    @Test
    public void findPageObjectsTests(){
        PageObject pageObjects = sysRoleService.findPageObjects("admin", 1);
        System.out.println(pageObjects.getRecords());
    }

    @Test
    public void saveObject(){
        SysRole entity = new SysRole().setName("test1")
                .setNote("note")
                .setCreatedTime(new Date())
                .setModifiedTime(new Date())
                .setCreatedUser("test");
        int rows = sysRoleService.saveObjects(entity);
        log.info("-----------"+rows+"-----------");
    }

    @Test
    public void doDeleteObject(){
        int rows = sysRoleService.doDeleteObject((long) 10);
        System.out.println("影响行数："+rows);
    }
}
