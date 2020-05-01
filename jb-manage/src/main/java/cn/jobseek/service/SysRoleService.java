package cn.jobseek.service;

import cn.jobseek.pojo.SysRole;
import cn.jobseek.vo.PageObject;

import java.util.List;

public interface SysRoleService {
    //获取role数据
    PageObject findPageObjects(String roleName, Integer pageCurrent);

    //保存role数据
    int saveObjects(SysRole entity);

    //根据id回显数据
    SysRole doFindObjectById(Long id);

    //更新role记录
    int doUpdateObject(SysRole entity);

    //删除role记录
    int doDeleteObject(Long id);

    //查询role表
    List<SysRole> doFindRoles();
}
