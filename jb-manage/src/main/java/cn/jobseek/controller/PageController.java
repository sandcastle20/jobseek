package cn.jobseek.controller;

import cn.jobseek.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class PageController {

    /**
     * 转发登录页面
     * @return  login.html
     */
    @RequestMapping("doLoginUI")
    public String doLoginUI(){
        return "login";
    }


    /**
     * 转发后台首页
     * @return starter.html
     */
    @RequestMapping("doIndexUI")
    public String doIndexUI(Model model){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("account",user.getAccount());
        return "starter";
    }

    /**
     * 转发系统管理下的子模块页面
     * @return 系统系模块.html
     */
    @RequestMapping("job/job_list")
    public String doSysUI(){
        return "job/job_list";
    }

    /**
     * 转发系统管理下的子模块页面
     * @param moduleUI
     * @return 系统系模块.html
     */
    @RequestMapping("{module}/{moduleUI}")
    public String doSysUI(@PathVariable String moduleUI){
        return "sys/"+moduleUI;
    }



}
