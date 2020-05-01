package cn.jobseek.service;

import cn.jobseek.mapper.SsoEmployeeMapper;
import cn.jobseek.mapper.SsoResumeMapper;
import cn.jobseek.pojo.Employee;
import cn.jobseek.pojo.Resume;
import cn.jobseek.pojo.User;
import cn.jobseek.util.Assert;
import cn.jobseek.util.ObjectMapperUtil;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import java.util.Date;

@Service(timeout = 3000)
public class DubboResumeServiceImpl implements DubboResumeService {

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private SsoEmployeeMapper employeeMapper;

    @Autowired
    private SsoResumeMapper resumeMapper;


    /**
     * 根据token查询userid
     * 根据userid查询employeeId
     * 插表jb_resume 有记录更新
     *                无记录插入
     * @param token
     */
    @Transactional
    @Override
    public int saveResume(String token,String url,String resumeName) {
        String userJson = jedisCluster.get(token);
        Assert.isArgumentValid(userJson == null,"系统错误请联系管理员");
        User user = ObjectMapperUtil.toObj(userJson, User.class);
        //根据userid,查询employee
        Employee employee = employeeMapper.doSelectByUserId(user.getId());
        //根据employeeid,查询resume
        Resume resume = resumeMapper.selectByEmpId(employee.getId());
        int rows = 0;
        if (null!=resume){
            resume.setResumeUrl(url).setResumeName(resumeName).setModifiedTime(new Date());
            rows = resumeMapper.updateResumeUrl(resume);
        }else {
            resume = new Resume();
            resume.setEmployeeId(employee.getId())
                    .setResumeUrl(url)
                    .setResumeName(resumeName)
                    .setCreatedUser(user.getAccount())
                    .setCreatedTime(new Date())
                    .setModifiedTime(resume.getCreatedTime());
            rows = resumeMapper.insertResume(resume);
        }
        return rows;


    }
}
