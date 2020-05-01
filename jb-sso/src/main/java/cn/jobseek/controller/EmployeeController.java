package cn.jobseek.controller;

import cn.jobseek.mapper.SsoEmployeeMapper;
import cn.jobseek.mapper.SsoResumeMapper;
import cn.jobseek.mapper.SsoUserRoleMapper;
import cn.jobseek.pojo.Employee;
import cn.jobseek.pojo.Resume;
import cn.jobseek.pojo.User;
import cn.jobseek.pojo.UserRole;
import cn.jobseek.util.CookieUtil;
import cn.jobseek.util.ObjectMapperUtil;
import cn.jobseek.vo.JsonResult;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RequestMapping("/employee/")
@RestController
public class EmployeeController {

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private SsoEmployeeMapper ssoEmployeeMapper;

    @Autowired
    private SsoUserRoleMapper ssoUserRoleMapper;

    @Autowired
    private SsoResumeMapper resumeMapper;

    //http://sso.jb.com/user/query/f4c1465a-c5a8-4d91-b9b0-d07a577b3b6e?callback=jQuery31106364152343987011_1587459849472&_=1587459849473
    //获取到employee的user_name和school
    @RequestMapping("query/{token}")
    public JSONPObject findEmployByToken(@PathVariable String token, String callback, HttpServletResponse response){
        //1.从redis中获取userjson信息
        String userJson = jedisCluster.get(token);

        //2.如果缓存中没有数据，证明token信息存在问题，删除cookie
        if (StringUtils.isEmpty(userJson)){
            CookieUtil.deleteCookie(response,"JB_TOKEN","jb.com","/");
            return new JSONPObject(callback, JsonResult.fail());
        }

        //3.根据userjson查询userId对应的roleId;
        User user = ObjectMapperUtil.toObj(userJson,User.class);
        Long roleId = ssoUserRoleMapper.doSelectByUserId(user.getId());
        if (roleId == null){
            return new JSONPObject(callback, JsonResult.fail());
        }
        Map<String, Object> employeeVoMap = new HashMap<>();
        employeeVoMap.put("roleId",ObjectMapperUtil.toJSON(roleId));
        if (roleId!=2L)
            return new JSONPObject(callback,JsonResult.success(employeeVoMap));

        //4.根据userjson查询employee的user_nanme和school;
        Employee employee = ssoEmployeeMapper.doSelectByUserId(user.getId());

        //5.根据employeee.getId查询resume表数据
        Resume resume = resumeMapper.selectByEmpId(employee.getId());


        employeeVoMap.put("employee",ObjectMapperUtil.toJSON(employee));
        employeeVoMap.put("resume",(null==resume)?null:ObjectMapperUtil.toJSON(resume));

        //组装JsonResult
        JsonResult resultJson = JsonResult.success(employeeVoMap);
        return new JSONPObject(callback,resultJson);
    }
}
