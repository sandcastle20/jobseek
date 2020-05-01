package cn.jobseek.service.impl;

import cn.jobseek.config.PaginationProperties;
import cn.jobseek.mapper.SysPositionMapper;
import cn.jobseek.service.SysPositionService;
import cn.jobseek.util.Assert;
import cn.jobseek.vo.PageObject;
import cn.jobseek.vo.SysPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SysPositionServiceImpl implements SysPositionService {

    @Autowired
    private SysPositionMapper sysPositionMapper;
    
    @Autowired
    private PaginationProperties paginationProperties;

    /**
     * 显示分页数据
     * @param positionName  职位名称
     * @param pageCurrent   页码值
     * @return  页面数据
     */
    @Override
    public PageObject findPageObjects(String positionName, Integer pageCurrent) {
        //1参数校验
        Assert.isArgumentValid(pageCurrent==null||pageCurrent<1,"页码值不正确");
        //2基于用户名查询记录数据
        //2.1查询总记录数
        int rowCount = sysPositionMapper.getRowCount(positionName);
        //2.2查询页面记录
        Assert.isServiceValid(rowCount==0,"没有找到对应记录");
        Integer pageSize = paginationProperties.getPageSize();      //页面显示数据
        Integer startIndex = paginationProperties.getstartIndex(pageCurrent);  //起始索引
        List<SysPosition> records = sysPositionMapper.getPageObject(positionName, startIndex, pageSize); //页面数据
        //3封装PageObject
        return new PageObject(rowCount,records,pageSize,pageCurrent);
    }

    /**
     * 审核职位
     * @param ids 职位id
     * @return
     */
    @Transactional
    @Override
    public int checkObjects(Long... ids) {
        //参数校验
        //情况一：ids为空，数组长度为空
        Assert.isArgumentValid(ids == null || ids.length == 0,"请选择对应的职位");
        //执行mapper操作
        int rows = sysPositionMapper.checkObjects(ids);
        sysPositionMapper.saveCheckTime(new Date(),ids);
        //情况二：数据库查不到对应的记录
        Assert.isServiceValid(rows==0,"记录不存在");
        return rows;
    }
}
