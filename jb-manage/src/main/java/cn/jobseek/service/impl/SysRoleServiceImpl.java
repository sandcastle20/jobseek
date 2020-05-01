package cn.jobseek.service.impl;

import cn.jobseek.config.PaginationProperties;
import cn.jobseek.mapper.SysRoleMapper;
import cn.jobseek.mapper.SysUserRoleMapper;
import cn.jobseek.pojo.SysRole;
import cn.jobseek.service.SysRoleService;
import cn.jobseek.util.Assert;
import cn.jobseek.vo.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;


    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private PaginationProperties paginationProperties;

    @Override
    public PageObject<SysRole> findPageObjects(String roleName, Integer pageCurrent) {
        //1.参数校验
        Assert.isArgumentValid(pageCurrent == null || pageCurrent <1,"页码值不正确");
        //2.执行mapper操作
        //2.1获取总记录数rowCount
        int rowCount = sysRoleMapper.getRowCount(roleName);
        //2.2 业务校验
        Assert.isServiceValid(rowCount == 0 ,"没有查询到对应的记录");
        //定义每页行数pageSize
        int pageSize = paginationProperties.getPageSize();
        //定义起始索引处startIndex(pageCurrent-1)*pageSize
        int startIndex = paginationProperties.getstartIndex(pageCurrent);
        //2.3获取role表数据
        List<SysRole> records = sysRoleMapper.doFindObjects(roleName, startIndex, pageSize);
        //3封装数据
        return new PageObject<SysRole>(rowCount, records, pageSize, pageCurrent);
    }

    /**
     * 保存角色信息
     * @param entity SysRole pojo
     * @return
     */
    @Override
    public int saveObjects(SysRole entity) {
        //参数校验
        Assert.isServiceValid(entity==null,"保存对象不能为空");
        Assert.isServiceValid(StringUtils.isEmpty(entity.getName()),"角色名称不能为空");
        //mapper插入
        entity.setCreatedTime(new Date())
                .setModifiedTime(entity.getCreatedTime())
                .setCreatedUser("test");
        int rows = sysRoleMapper.saveObjects(entity);
        return rows;
    }

    /**
     * 根据id回显数据
     * @param id    role表id字段
     * @return
     */
    @Override
    public SysRole doFindObjectById(Long id) {
        //参数校验
        Assert.isArgumentValid(id == null || id < 1,"id值无效");
        //执行mapper
        SysRole entity = sysRoleMapper.doFindObjectById(id);
        Assert.isServiceValid(entity == null,"未找到对应的记录");
        return entity;
    }

    /**
     * 基于id修改role记录
     * @param entity
     * @return
     */
    @Override
    public int doUpdateObject(SysRole entity) {
        //校验参数
        Assert.isArgumentValid(entity.getId()==null||entity.getId()<1,"id值无效");
        //执行mapper
        int rows = sysRoleMapper.updateObject(entity);
        Assert.isServiceValid(rows == 0,"没有修改成功");
        return rows;
    }

    @Transactional
    @Override
    public int doDeleteObject(Long id) {
        //参数校验
        Assert.isArgumentValid(id == null || id< 1,"id值无效");
        Assert.isServiceValid(id == 1 ,"不能删除管理员");
        //执行mapper'
        //1.更新用户_角色表信息
        sysUserRoleMapper.updateObjectAfterDelete(id);
        //2.删除用户自身信息
        int rows = sysRoleMapper.deleteObject(id);
        //业务校验
        Assert.isServiceValid(rows == 0 ,"未删除任何记录");
        return rows;
    }

    @Override
    public List<SysRole> doFindRoles() {
        return sysRoleMapper.findRoles();
    }
}
