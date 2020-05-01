package cn.jobseek.service;

import cn.jobseek.mapper.*;
import cn.jobseek.pojo.*;
import cn.jobseek.util.Assert;
import cn.jobseek.util.ObjectMapperUtil;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import java.util.Date;

@Service(timeout = 3000)
public class DubboCompanyServiceImpl implements DubboCompanyService {

    @Autowired
    private SsoCompanyMapper companyMapper;
    
    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private SsoUserMapper userMapper;

    @Autowired
    private SsoPubpersonMapper pubpersonMapper;

    @Autowired
    private SsoPositionMapper positionMapper;

    @Autowired
    private SsoCompanyPositionMapper companyPositionMapper;

    /**
     * 基于companyId查询company
     * @param companyId
     * @return
     */
    @Override
    public Company doFindCompany(Long companyId) {
        Assert.isArgumentValid(companyId==null,"企业id错误");
        Company company = companyMapper.selectCompanyById(companyId);
        return company;
    }

    /**
     * 更改company
     * @param company
     * @return
     */
    @Override
    public int doSaveCompany(Company company) {
        Assert.isArgumentValid(company.getId()==null,"企业id错误");
        company.setModifiedTime(new Date());
        int rows = companyMapper.updateCompany(company);
        return rows;
    }

    /**
     * 获取企业用户的联系方式
     * @return
     */
    @Override
    public User doFindCompanyContactWay(String token) {
        String userJson = jedisCluster.get(token);
        Assert.isArgumentValid(userJson == null,"redis缓存中没有用户json");
        User user = ObjectMapperUtil.toObj(userJson, User.class);
        Assert.isServiceValid(user.getEmail()==null||user.getEmail()==null,"请重新登录");
        return user;
    }

    /**
     * 保存jb_company（phone，email）与jb_user(mobile,email) 中的
     * @param token-->userId update user.getEmail user.getMobile
     * @param user
     * @param companyId update user.getEmail user.getMobile
     * @return
     */
    @Transactional
    @Override
    public int doSaveCompanyContactWay(String token, User user, Long companyId) {
        String userJson = jedisCluster.get(token);
        Assert.isArgumentValid(userJson == null,"redis缓存中没有用户json");
        User redisUser = ObjectMapperUtil.toObj(userJson, User.class);
        redisUser.setEmail(user.getEmail()).setMobile(user.getMobile());
        //1.update jb_user
        int rows = userMapper.updateContactWay(redisUser);
        Assert.isServiceValid(rows==0,"修改失败");
        //1.1 update redis
        String updatedUserJson = ObjectMapperUtil.toJSON(redisUser);
        jedisCluster.setex(token,7*24*3600,updatedUserJson);
        //2.update jb_company
        Company company = new Company();
        company.setId(companyId).setEmail(user.getEmail()).setPhone(user.getMobile());
        rows = companyMapper.updateCompanyContactWay(company);
        Assert.isServiceValid(rows==0,"修改失败");
        return 0;
    }

    /**
     * 更新url-jb_company
     * @param token
     * @return
     */
    @Override
    public int updateAvatarByUserId(String token, String path) {
        Assert.isArgumentValid(token==null|| StringUtils.isEmpty(token),"系统错误，请重新登录");
        String userJson = jedisCluster.get(token);
        Assert.isServiceValid(userJson == null,"系统错误，请重新登录");
        User user = ObjectMapperUtil.toObj(userJson, User.class);
        int rows = companyMapper.updateAvatarByUserId(user.getId(), path);
        Assert.isServiceValid(rows == 0,"更新数据失败");
        return rows;
    }

    /**
     * 发布职位
     *
     * @param token
     * @param compId  企业id
     * @param position  positionName salary address duty demanding welfare workTime
     * @param pubperson deptName pubName
     * @return
     */
    @Transactional
    @Override
    public int doPushPosition(String token, Long compId, Position position, Pubperson pubperson) {
        //校验参数
        Assert.isArgumentValid(compId == null,"企业id失效");
        //token->account
        String userJson = jedisCluster.get(token);
        User redisUser = ObjectMapperUtil.toObj(userJson, User.class);
        //insert jb_position get id-> position_id
        position.setCheckStatus(false)
                .setCreatedTime(new Date())
                .setModifiedTime(position.getCreatedTime())
                .setCreatedUser(redisUser.getAccount());
        int row = positionMapper.insertPosition(position);
        Assert.isServiceValid(row == 0,"发布失败");
        //insert jb_pubperson get id -> pubperson_id
        pubperson.setCreatedTime(new Date())
                .setModifiedTime(position.getCreatedTime())
                .setCreatedUser(redisUser.getAccount());
        row = pubpersonMapper.insertPubperson(pubperson);
        Assert.isServiceValid(row == 0,"发布失败");
        //if company_id == null(from jb_company_positon) , insert into jb_company_position(company_id,position_id,pubposition)
        CompanyPosition companyPosition = new CompanyPosition();
        companyPosition.setCompanyId(compId).setPositionId(position.getId()).setPubpersonId(pubperson.getId());
        row = companyPositionMapper.insertObject(companyPosition);
        Assert.isServiceValid(row == 0,"发布失败");
        return row;
    }


}
