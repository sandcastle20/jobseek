package cn.jobseek.service;


import cn.jobseek.vo.IndexPositionItem;
import cn.jobseek.vo.IndexPositionPageObject;
import cn.jobseek.vo.JobDetail;

import java.util.List;

public interface DubboJobService {

    JobDetail findJobDetail(Long positionId);

    int deleteOnejob(Long positionId);

    int doApplyForPosition(Long positionId, String token);

    IndexPositionPageObject doFindIndexPositionItem(String positionName, String address);

}
