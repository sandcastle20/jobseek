package cn.jobseek.mapper;

import cn.jobseek.vo.SysPosition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SysPositionMapperTests {

    @Autowired
   private SysPositionMapper sysPositionMapper;

    @Test
    public void getRowCountTest(){
        int rowCount = sysPositionMapper.getRowCount(null);
        System.out.println("总记录数："+rowCount);
    }

    @Test
    public void getPageObjectTest(){
        List<SysPosition> pageObject = sysPositionMapper.getPageObject(null,0, 1);
        pageObject.forEach((item)->{
            System.out.println(item);
        });
    }


}
