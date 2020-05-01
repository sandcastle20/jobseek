package cn.jobseek.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/")
@Controller
public class PageController {

    @RequestMapping("register/{who_register}")
    public String doRegisterUI(@PathVariable String who_register){
        return who_register;
    }

    @RequestMapping("me/{formMoudle}")
    public String doMeFormUI(@PathVariable String formMoudle){
        return formMoudle;
    }

    @RequestMapping("company/{formMoudle}")
    public String doCompanyFormUI(@PathVariable String formMoudle){
        return formMoudle;
    }


    @RequestMapping("{page}")
    public String doPageUI(@PathVariable String page){
        return page;
    }
}
