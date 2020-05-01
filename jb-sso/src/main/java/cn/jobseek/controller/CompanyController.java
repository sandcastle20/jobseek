package cn.jobseek.controller;

import cn.jobseek.mapper.SsoCompanyMapper;
import cn.jobseek.mapper.SsoCompanyPositionMapper;
import cn.jobseek.mapper.SsoUserRoleMapper;
import cn.jobseek.mapper.SsoWebPositionitemMapper;
import cn.jobseek.pojo.*;
import cn.jobseek.util.Assert;
import cn.jobseek.util.CookieUtil;
import cn.jobseek.util.ObjectMapperUtil;
import cn.jobseek.vo.JsonResult;
import cn.jobseek.vo.PositionItem;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Slf4j
@RequestMapping("/company/")
@RestController
public class CompanyController {

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private SsoWebPositionitemMapper webPositionitemMapper;

    @Autowired
    private SsoCompanyMapper companyMapper;

    @Autowired
    private SsoUserRoleMapper userRoleMapper;

    @Autowired
    private SsoCompanyPositionMapper companyPositionMapper;





    //http://sso.jb.com/company/query/52e32545-438c-4e6b-887c-d2e38dd7800f?callback=jQuery31105516064058458987_1587998258201&_=1587998258202
    //基于token获取company.
    @RequestMapping("query/{token}")
    public JSONPObject findCompanyByToken(@PathVariable String token, String callback, HttpServletResponse response){
        //1.从redis中获取userjson信息
        String userJson = jedisCluster.get(token);

        //2.如果缓存中没有数据，证明token信息存在问题，删除cookie
        if (StringUtils.isEmpty(userJson)){
            CookieUtil.deleteCookie(response,"JB_TOKEN","jb.com","/");
            return new JSONPObject(callback, JsonResult.fail());
        }

        //3.根据userjson查询userId对应的roleId;
        User user = ObjectMapperUtil.toObj(userJson, User.class);
        Long roleId = userRoleMapper.doSelectByUserId(user.getId());
        if (roleId == null){
            return new JSONPObject(callback, JsonResult.fail());
        }
        Map<String, Object> companyVoMap = new HashMap<>();
        companyVoMap.put("roleId",ObjectMapperUtil.toJSON(roleId));
        if (roleId != 3L){
            return new JSONPObject(callback, JsonResult.success(companyVoMap));
        }

        //4.根据userjson查询company
        Company company = companyMapper.doSelectByUserId(user.getId());//21
        companyVoMap.put("company",ObjectMapperUtil.toJSON(company));

        Map<String, Object> positionItemVoMap = new HashMap<>();
        //5.查询是否存在发布的职位
        List<CompanyPosition> companyPositions = companyPositionMapper.doSelectByCompId(company.getId());

        if (companyPositions.isEmpty()){
            companyVoMap.put("position",null);
            return new JSONPObject(callback, JsonResult.success(companyVoMap));
        }
        Long[] positionIds = getPositionIds(companyPositions);

        //5.1查询视图
        List<PositionItem> positionItems = webPositionitemMapper.selectObjectByPositionId(positionIds);
        Assert.isServiceValid(positionItems == null,"未找到职位条目的视图信息");
        companyVoMap.put("position",ObjectMapperUtil.toJSON(positionItems));
        //组装JsonResult
        JsonResult resultJson = JsonResult.success(companyVoMap);
        return new JSONPObject(callback,resultJson);
    }


    //从cp 记录中获取positionids
    private Long[] getPositionIds(List<CompanyPosition> companyPositions) {
        List pIds = new ArrayList<>();
        companyPositions.stream().forEach(companyPosition-> pIds.add(companyPosition.getPositionId()));
        return Arrays.stream(pIds.toArray()).toArray(Long[]::new);
    }
}
