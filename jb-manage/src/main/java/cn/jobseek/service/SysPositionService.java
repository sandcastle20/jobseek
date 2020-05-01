package cn.jobseek.service;

import cn.jobseek.vo.PageObject;

public interface SysPositionService {
    //显示分页数据
    PageObject findPageObjects(String positionName,Integer pageCurrent);

    //审核职位
    int checkObjects(Long ...ids);
}
