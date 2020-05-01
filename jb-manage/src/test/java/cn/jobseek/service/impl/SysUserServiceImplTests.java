package cn.jobseek.service.impl;

import cn.jobseek.pojo.User;
import cn.jobseek.service.SysUserService;
import cn.jobseek.vo.PageObject;
import cn.jobseek.vo.SysUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@SpringBootTest
public class SysUserServiceImplTests {

    @Autowired
    private SysUserService sysUserService;

    @Test
    public void doFindPageObjects(){
        PageObject<SysUser> sysUserPageObject = sysUserService.doFindPageObjects(null, 1);
        System.out.println(sysUserPageObject.getRowCount()+"-》总记录数");
        System.out.println(sysUserPageObject.getPageCount()+"-》总页数");
        System.out.println(sysUserPageObject.getPageSize()+"-》每页显示记录数");
        sysUserPageObject.getRecords().forEach((user)->{
            System.out.println(user);
        });
    }

    @Test
    public void doSaveObject(){
        User entity = new User().setAccount("张三")
                .setPwd("123456789")
                .setEmail("12345678900@163.com")
                .setMobile("12345678900");
        int rows = sysUserService.doSaveObject(entity, (long) 1);
        System.out.println("影响行数："+rows);

    }

    @Test
    public void doFindObjectById(){
        Map<String, Object> map = sysUserService.doFindObjectById((long) 3);
        //使用keySet得到key值
        Set<String> keySet = map.keySet();
        Iterator<String> iterator = keySet.iterator();
        iterator.forEachRemaining((item)->{
            Object value = map.get(item);
            System.out.println("key:"+item+"----value:"+value);
        });
    }


    @Test
    public void doUpdateObject(){
        User user = new User();
        user.setId((long)5).setAccount("test").setMobile("17621835508").setEmail("test123@163.com");
        Long roleId = (long) 3;
        int rows = sysUserService.doUpdateObject(user, roleId);
        System.out.println("影响行数："+rows);
    }
}
