package cn.jobseek.service;

import cn.jobseek.pojo.User;
import cn.jobseek.vo.PageObject;
import cn.jobseek.vo.SysUser;

import java.util.List;
import java.util.Map;

public interface SysUserService {

    PageObject<SysUser> doFindPageObjects(String username, Integer pageCurrent);

    int doValidById(Integer id, Integer valid);

    int doSaveObject(User entity, Long roleId);

    Map<String, Object> doFindObjectById(Long id);

    int doUpdateObject(User user, Long roleId);

    int doDeleteObject(Long id);

    int doUpdatePassword(String pwd, String newPwd, String cfgPwd);
}
