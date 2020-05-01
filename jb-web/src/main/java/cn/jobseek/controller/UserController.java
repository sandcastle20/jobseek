package cn.jobseek.controller;

import cn.jobseek.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//http://www.jb.com/user/logout.html

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private JedisCluster jedisCluster;

    //1.重定向至系统首页
    //2.根据cookie查询token
    //3.删除redis  根据token删除redis
    //4.删除cookie信息
    @RequestMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        //获取页面token
        String token = CookieUtil.getCookieValue(request, "JB_TOKEN");
        //验证token存在则进行以下操作
        if (!StringUtils.isEmpty(token)){
            jedisCluster.del("token");//删除redis的token数据
            CookieUtil.deleteCookie(response,"JB_TOKEN","jb.com","/");
        }
        return "redirect:/";
    }
}
