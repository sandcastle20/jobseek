package cn.jobseek.service;

import cn.jobseek.mapper.SsoCompanyMapper;
import cn.jobseek.pojo.Company;
import cn.jobseek.pojo.Employee;
import cn.jobseek.pojo.User;
import cn.jobseek.mapper.SsoEmployeeMapper;
import cn.jobseek.mapper.SsoUserMapper;
import cn.jobseek.mapper.SsoUserRoleMapper;
import cn.jobseek.pojo.UserRole;
import cn.jobseek.util.Assert;
import cn.jobseek.util.ObjectMapperUtil;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service(timeout = 3000)
public class DubboUserServiceImpl implements DubboUserService {

    @Autowired
    private SsoUserMapper userMapper;

    @Autowired
    private SsoUserRoleMapper userRoleMapper;

    @Autowired
    private SsoEmployeeMapper employeeMapper;

    @Autowired
    private SsoCompanyMapper companyMapper;

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 基于user.account账号+user.pwd密码+user.email邮箱+user.mobile手机
     * 基于employee.userName姓名+employee.sex性别+employee.school学校+employee.major专业+employee.salary薪资+employee.tip简介
     * @param employee
     * @param user
     * @param roleId
     * @return
     */
    @Transactional
    @Override
    public int doRegisterUser(Employee employee, User user, Long roleId) {
        //参数校验
        Assert.isServiceValid(user==null,"没有接收到用户信息");
        Assert.isServiceValid(employee==null,"没有接收到求职者信息");
        Assert.isServiceValid(roleId!=2L&&roleId!=3L,"没有选择角色信息");
        //1.加密
        User encryptUser = md5UserEncrypt(user);
        //2.保存用户表和角色表
        Long userId = saveUserAndUserRole(roleId, encryptUser);
        //3.save employee info
        employee.setUserId(userId)
                .setCreatedTime(encryptUser.getCreatedTime())
                .setModifiedTime(encryptUser.getCreatedTime())
                .setCreatedUser(encryptUser.getAccount());
        int rows = employeeMapper.saveEmployeeObject(employee);
        return rows;
    }

    /**
     * 基于user.account账号+user.pwd密码+user.email邮箱+user.mobile手机
     * 基于company.company_name+company.location+company.scale+company.tip+(company.phone+company.email)
     * @param company
     * @param user
     * @param roleId
     * @return
     */
    @Transactional
    @Override
    public int doRegisterCompany(Company company, User user, Long roleId) {
        //1.参数验证
        Assert.isServiceValid(user==null,"没有接收到用户信息");
        Assert.isServiceValid(company==null,"没有接收到求职者信息");
        Assert.isServiceValid(roleId!=2L&&roleId!=3L,"没有选择角色信息");
        //1.加密
        User encryptUser = md5UserEncrypt(user);
        //2.保存用户表和角色表
        Long userId = saveUserAndUserRole(roleId, encryptUser);
        //3.保存企业表
        company.setUserId(userId)
                .setEmail(encryptUser.getEmail())
                .setPhone(encryptUser.getMobile())
                .setCreatedTime(encryptUser.getCreatedTime())
                .setModifiedTime(encryptUser.getCreatedTime())
                .setCreatedUser(encryptUser.getAccount());
        int rows = companyMapper.saveCompanyObject(company);
        return rows;
    }



    //保存用户表和角色表
    private Long saveUserAndUserRole(Long roleId, User encryptUser) {
        //1.save user info
        userMapper.saveObject(encryptUser);
        //2.save user_role info
        Long userId = encryptUser.getId();
        UserRole userRole = new UserRole();
        userRole.setUserId(userId)
                .setRoleId(roleId)
                .setCreatedTime(encryptUser.getCreatedTime())
                .setModifiedTime(encryptUser.getCreatedTime())
                .setCreatedUser(encryptUser.getAccount());
        userRoleMapper.saveObject(userRole);
        return userId;
    }

    //md5加密
    private User md5UserEncrypt(User user) {
        String pwd = user.getPwd();
        String salt = UUID.randomUUID().toString();
        SimpleHash md5Pwd = new SimpleHash("MD5", pwd, salt, 1);
        user.setPwd(md5Pwd.toHex())
                .setSalt(salt)
                .setValid(true)
                .setCreatedTime(new Date())
                .setModifiedTime(user.getCreatedTime())
                .setCreatedUser(user.getAccount());
        return user;
    }




    /**
     * 登录
     * @param account
     * @param pwd
     * @return
     */
    @Override
    public String doLogin(String account, String pwd) {
        //验证参数
        Assert.isArgumentValid(StringUtils.isEmpty(account),"您未输入账号");
        Assert.isArgumentValid(StringUtils.isEmpty(pwd),"您未输入密码");
        //查询是否存在账号
        User user = userMapper.selectByAccount(account);

        Assert.isServiceValid(user==null,"账号不存在，请先注册");
        //查询密码是否输入正确
        String salt = user.getSalt();
        SimpleHash realPwd = new SimpleHash("MD5",pwd, salt, 1);
        User pwdUser = userMapper.selectByAccountAndPwd(account, realPwd.toHex());
        Assert.isServiceValid(pwdUser==null,"密码输入错误");
        //获取令牌
        String token = UUID.randomUUID().toString();
        user.setPwd("123456");//密码脱敏
        String userJson = ObjectMapperUtil.toJSON(user);//准备存入redis的用户json串
        System.out.println();
        jedisCluster.setex(token,7*24*3600,userJson);

        return token;
    }


}
