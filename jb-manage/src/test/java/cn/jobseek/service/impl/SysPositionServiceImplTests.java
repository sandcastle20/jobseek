package cn.jobseek.service.impl;

import cn.jobseek.service.SysPositionService;
import cn.jobseek.vo.PageObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SysPositionServiceImplTests{

    @Autowired
    private SysPositionService sysPositionService;

    @Test
    public void findPageObjectsTests(){
        PageObject pageObjects = sysPositionService.findPageObjects(null, 1);
        System.out.println(pageObjects.getRecords());
    }
}
