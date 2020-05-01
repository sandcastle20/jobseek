package cn.jobseek.controller;

import cn.jobseek.pojo.SysRole;
import cn.jobseek.service.SysRoleService;
import cn.jobseek.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role/")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * url:/role/doFindPageObjects
     * @param roleName
     * @param pageCurrent
     * @return
     */
    @RequestMapping("doFindPageObjects")
    public JsonResult doFindPageObjects(String roleName,Integer pageCurrent){
        return JsonResult.success(sysRoleService.findPageObjects(roleName, pageCurrent));
    }

    @RequestMapping("doSaveObject")
    public JsonResult doSaveObjects(SysRole entity){
        sysRoleService.saveObjects(entity);
        return JsonResult.message("保存成功");
    }

    //根据角色id修改角色信息
    @RequestMapping("doFindObjectById")
    public JsonResult doFindObjectById(Long id){
        SysRole entity = sysRoleService.doFindObjectById(id);
        return JsonResult.success(entity);
    }

    //根据id修改表单
    @RequestMapping("doUpdateObject")
    public JsonResult doUpdateObject(SysRole entity){
        sysRoleService.doUpdateObject(entity);
        return JsonResult.message("修改成功");
    }

    @RequestMapping("doDeleteObject")
    public JsonResult doDeleteObject(Long id){
        sysRoleService.doDeleteObject(id);
        return JsonResult.message("删除成功");
    }

    @RequestMapping("doFindRoles")
    public JsonResult  doFindRoles(){
        List<SysRole> sysRoles = sysRoleService.doFindRoles();
        return JsonResult.success(sysRoles);
    }
}
