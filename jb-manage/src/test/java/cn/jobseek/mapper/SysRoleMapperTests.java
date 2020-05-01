package cn.jobseek.mapper;

import cn.jobseek.pojo.SysRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class SysRoleMapperTests {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Test
    public void getRowCount(){
        int rowCount = sysRoleMapper.getRowCount(null);
        System.out.println(rowCount);
    }

    //SysRole(id=2, name=empolyee, note=求职者) 没有创建时间 修改时间 创建人
    @Test void findPageObject(){
        List<SysRole> sysRoles = sysRoleMapper.doFindObjects(null,1,1);
        sysRoles.forEach((item)->{
            System.out.println(item);
        });
    }

    //save ok
    @Test
    public void  saveObject(){
        SysRole entity = new SysRole().setName("test1")
                .setNote("note")
                .setCreatedTime(new Date())
                .setModifiedTime(new Date())
                .setCreatedUser("test");
        sysRoleMapper.saveObjects(entity);
    }

    //find role by id ok
    @Test
    public void doFindObjectById(){
        SysRole entity = sysRoleMapper.doFindObjectById((long) 6);
        System.out.println(entity);
    }

    @Test
    public void doDeleteObject(){
//        int rows = sysRoleMapper.deleteObject((long) 6);
        int rows = sysRoleMapper.deleteObject((long) 6);
        System.out.println("影响行数："+rows);
    }
}

