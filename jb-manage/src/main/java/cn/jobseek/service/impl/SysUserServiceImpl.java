package cn.jobseek.service.impl;

import cn.jobseek.config.PaginationProperties;
import cn.jobseek.mapper.SysRoleMapper;
import cn.jobseek.mapper.SysUserMapper;
import cn.jobseek.mapper.SysUserRoleMapper;
import cn.jobseek.mapper.SysViewUserMapper;
import cn.jobseek.pojo.SysRole;
import cn.jobseek.pojo.User;
import cn.jobseek.pojo.UserRole;
import cn.jobseek.service.SysUserService;
import cn.jobseek.util.Assert;
import cn.jobseek.vo.PageObject;
import cn.jobseek.vo.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysViewUserMapper sysViewUserMapper;

    @Autowired
    private PaginationProperties paginationProperties;

    @Override
    public PageObject<SysUser> doFindPageObjects(String username, Integer pageCurrent) {
        //参数校验
        Assert.isArgumentValid(pageCurrent == null ||pageCurrent <1 ,"页码值不正确");
        //获取rowCount页面总记录数
//        sysUserMapper.getRowCount(username)
        int rowCount = sysUserMapper.getRowCount(username);
        int startIndex = paginationProperties.getstartIndex(pageCurrent);
        int pageSize = paginationProperties.getPageSize();
        List<SysUser> records = sysUserMapper.findPageObjects(username, startIndex, pageSize);
        Assert.isServiceValid(records==null,"找不到用户记录");
        return new PageObject<>(rowCount,records,pageSize,pageCurrent);
    }

    @Override
    public int doValidById(Integer id, Integer valid) {
        //参数校验
        Assert.isArgumentValid(id == null||id<0,"id值无效,id为"+id);
        Assert.isArgumentValid(valid!=0 && valid!=1,"valid值无效,valid为"+valid);
        //执行mapper
        int rows = sysUserMapper.validById(id, valid);
        Assert.isServiceValid(rows == 0,"未能执行操作");
        return rows;
    }

    @Transactional
    @Override
    public int doSaveObject(User user, Long roleId) {
        //1.参数校验
        Assert.isArgumentValid(user==null,"保存对象不能为空");
        Assert.isServiceValid(StringUtils.isEmpty(user.getAccount()),"用户名不能未空");
        Assert.isServiceValid(StringUtils.isEmpty(user.getPwd()),"密码不能为空");
        Assert.isServiceValid(StringUtils.isEmpty(user.getEmail()),"邮箱不能为空 ");
        Assert.isServiceValid(StringUtils.isEmpty(user.getMobile()),"手机不能为空 ");
        Assert.isServiceValid(roleId==null,"没有分配角色 ");
        //2.为密码加密
//        String pwd = DigestUtils.md5DigestAsHex(user.getPwd().getBytes());
        String pwd = user.getPwd();
        String salt = UUID.randomUUID().toString();//replaceAll("\\-", "")
        SimpleHash sh = new SimpleHash("MD5", pwd, salt, 1);
        user.setPwd(sh.toHex())
                .setSalt(salt)
                .setValid(true)
                .setCreatedTime(new Date())
                .setModifiedTime(user.getCreatedTime())
                .setCreatedUser("test");
        //3.插入user表数据
        int rows = sysUserMapper.insertObject(user);
        //4.插入user_role表数据

        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId())
                .setRoleId(roleId)
                .setCreatedTime(new Date())
                .setModifiedTime(userRole.getCreatedTime())
                .setCreatedUser("test");
        sysUserRoleMapper.insertObject(userRole);

        return rows;
    }


    @Transactional
    @Override
    public Map<String, Object> doFindObjectById(Long id) {
        //参数小样
        Assert.isArgumentValid(id==null||id<1,"id值无效");
        //查询user表信息
        User user = sysUserMapper.doFindObjectById(id);
        Assert.isServiceValid(user == null,"没找到对应的记录");
        HashMap<String, Object> map = new HashMap<>();
        map.put("user",user);

        //基于用户id查询视图sys_job_user的roleName是否存在roleName值
        boolean  noneRole = sysViewUserMapper.findRoleName(id);
        if (noneRole==false){    //roleName为null时 noneRole为true
            //查询user_role表信息
            UserRole userRole = sysUserRoleMapper.doFindObjectById(id);
            Assert.isServiceValid(userRole==null,"未找到用户id对应的角色");
            //查询role表信息
            SysRole sysRole = sysRoleMapper.doFindObjectById(userRole.getRoleId());
            Assert.isServiceValid(userRole.getRoleId()==null,"未找到角色id对应的角色信息");
            map.put("roleId",sysRole.getId());
        }



        return map;
    }

    @Transactional
    @Override
    public int doUpdateObject(User user, Long roleId) {
        //参数校验
        Assert.isServiceValid(user==null,"修改对象不能为空");
        Assert.isArgumentValid(roleId==null||roleId<1,"角色id出错");
        //执行mapper
        //1.更新user_role 找到userid对应的roleId 一对一的关系
        Long userId = user.getId();
        sysUserRoleMapper.updateObject(userId,roleId);
        //2.更新role
        user.setModifiedTime(new Date());
        int rows = sysUserMapper.updateObject(user);
        return rows;
    }

    /**
     * 基于userId删除
     * @param id userId
     * @return
     */
    @Transactional
    @Override
    public int doDeleteObject(Long id) {
        //参数校验
        Assert.isArgumentValid(id==null||id<1,"id值无效");
        //mapper操作
        //1.删除user_role数据
        sysUserRoleMapper.deleteObjectByUserId(id);
        //2.删除自身数据
        int rows = sysUserMapper.deleteObject(id);
        return rows;
    }

    /**
     * 修改密码
     * @param pwd
     * @param newPwd
     * @param cfgPwd
     * @return
     */
    @Override
    public int doUpdatePassword(String pwd, String newPwd, String cfgPwd) {
        //1.判断新密码和就密码是否相同
        Assert.isArgumentValid(StringUtils.isEmpty(newPwd),"新密码不能为空");
        Assert.isArgumentValid(StringUtils.isEmpty(cfgPwd),"确认密码不能为空");
        Assert.isArgumentValid(!newPwd.equals(cfgPwd),"两次输入的密码不相同");
        //2.判断密码
        // 2.1是否输入正确
        Assert.isArgumentValid(StringUtils.isEmpty(pwd),"原密码不能为空");
        //2.1获取登录用户
        User  user = (User)SecurityUtils.getSubject().getPrincipal();
        //获取加密后的pwd(数据库中存的)
        SimpleHash md5 = new SimpleHash("MD5", pwd, user.getSalt(), 1);
        //subject中的principle身份没有加密的原密码
        Assert.isServiceValid(!user.getPwd().equals(md5.toHex()),"原密码不正确");
        //3.对新密码加密
        String salt = UUID.randomUUID().toString();
        SimpleHash newMd5 = new SimpleHash("MD5", newPwd, salt, 1);
        //4.将新密码加密后（即newMd5）的结果更新到数据库
        int rows = sysUserMapper.updatePwd(newMd5.toHex(),salt,user.getId());//基于id更新密码和盐值
        Assert.isServiceValid(rows == 0,"修改密码失败");
        return rows;
    }

}
