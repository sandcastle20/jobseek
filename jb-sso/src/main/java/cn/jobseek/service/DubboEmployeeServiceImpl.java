package cn.jobseek.service;

import cn.jobseek.mapper.SsoEmployeeMapper;
import cn.jobseek.mapper.SsoUserMapper;
import cn.jobseek.pojo.Employee;
import cn.jobseek.pojo.User;
import cn.jobseek.util.Assert;
import cn.jobseek.util.ObjectMapperUtil;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
@Slf4j
@Service(timeout = 3000)
public class DubboEmployeeServiceImpl implements DubboEmployeeService {


    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private SsoEmployeeMapper employeeMapper;

    @Autowired
    private SsoUserMapper userMapper;



    /**
     * 根据token get(token)->User（反射）查询redis中对应的userid
     * 若没有token直接 抛异常
     *  若查不到userid，抛异常
     *  根据userid，获取封装好的user的其他属性，返回
     * @param token
     * @return
     */
    @Override
    public Employee doFindEmployee(String token) {
        //1.参数校验
        Assert.isArgumentValid(token == null,"初始化信息失败，请重新登录");
        String userJson = jedisCluster.get(token);
        Assert.isServiceValid(userJson==null|| StringUtils.isEmpty(userJson),"请联系redis数据库人员,发送用户数据异常");
        User user = ObjectMapperUtil.toObj(userJson, User.class);
        log.info(user.toString());
        //2.根据userid查询employee信息
        Employee employee = employeeMapper.doSelectByUserId(user.getId());
        Assert.isServiceValid(employee==null,"未查到对应的求职者信息");
        return employee;
    }

    /**
     * 更新求职者信息
     * @param employee
     * @return
     */
    @Override
    public int doUpdateEmployee(Employee employee) {
        employee.setModifiedTime(new Date());
        int rows = employeeMapper.updateEmployee(employee);
        Assert.isServiceValid(rows == 0,"未找到该用户信息，请重新登录");
        return rows;
    }

    /**
     * 图片上传2-更新url
     * @param token
     * @return
     */
    @Override
    public int updateAvatarByUserId(String token,String path) {
        Assert.isArgumentValid(token==null||StringUtils.isEmpty(token),"系统错误，请重新登录");
        String userJson = jedisCluster.get(token);
        Assert.isServiceValid(userJson == null,"系统错误，请重新登录");
        User user = ObjectMapperUtil.toObj(userJson, User.class);
        int rows = employeeMapper.updateAvatarByUserId(user.getId(), path);
        Assert.isServiceValid(rows == 0,"更新数据失败");
        return rows;
    }

    /**
     * 通过token获取userid获取user
     * @param token
     * @return
     */
    @Override
    public User doFindContactWay(String token) {
        User user = doGetUserByToken(token);
        return user;
    }

    private User doGetUserByToken(String token) {
        String userJson = jedisCluster.get(token);
        Assert.isArgumentValid(userJson==null|| StringUtils.isEmpty(userJson),"系统错误，联系管理员");
        User user = ObjectMapperUtil.toObj(userJson, User.class);
        if (StringUtils.isEmpty(user.getMobile()) || StringUtils.isEmpty(user.getEmail())){
            //默认userid存在
            user = userMapper.selectByUserId(user.getId());
        }
        return user;
    }

    @Override
    public int saveContactWay(User user, String token) {
        User queryUser = doGetUserByToken(token);
        user.setId(queryUser.getId())
                .setModifiedTime(new Date());
        return userMapper.updateContactWay(user);
    }

}
