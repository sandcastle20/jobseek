package cn.jobseek.service;

import cn.jobseek.mapper.*;
import cn.jobseek.pojo.Company;
import cn.jobseek.pojo.CompanyPosition;
import cn.jobseek.pojo.Position;
import cn.jobseek.pojo.Pubperson;
import cn.jobseek.util.Assert;
import cn.jobseek.vo.IndexPositionItem;
import cn.jobseek.vo.IndexPositionPageObject;
import cn.jobseek.vo.JobDetail;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.ASCIICaseInsensitiveComparator;

import java.util.List;

@Service(timeout = 3000)
public class DubboJobServiceImpl implements DubboJobService{

    @Autowired private SsoCompanyPositionMapper companyPositionMapper;
    @Autowired private SsoPositionMapper positionMapper;
    @Autowired private SsoCompanyMapper companyMapper;
    @Autowired private SsoPubpersonMapper pubpersonMapper;

    @Autowired private SsoWebJobDetailMapper webJobDetailMapper;



    /**
     * 基于职位id 查询JobDetailvo
     * @param positionId
     * @return
     */
    @Transactional
    @Override
    public JobDetail findJobDetail(Long positionId) {
        Assert.isArgumentValid(positionId==null,"职位id不见了");
        //select jb_company_position
        CompanyPosition companyPosition = companyPositionMapper.selectByPositionId(positionId);
        Assert.isServiceValid(companyPosition==null,"未找到记录");
        //select jb_position
        Position position = positionMapper.selectByPositionId(companyPosition.getPositionId());
        Assert.isServiceValid(position==null,"未找到记录");
        //select jb_company
        Company company = companyMapper.selectByCompanyId(companyPosition.getCompanyId());
        Assert.isServiceValid(company==null,"未找到记录");
        //select jb_pubperson
        Pubperson pubperson = pubpersonMapper.selectByPubpersonId(companyPosition.getPubpersonId());
        Assert.isServiceValid(pubperson==null,"未找到记录");
        //封装vo
        // [jb_company] avatar company_name location scale
        // [jb_position] position_name address + duty demanding welfare work_time
        // [jb_pubperson] pub_name dept_name
        JobDetail jobDetail = new JobDetail();
        jobDetail.setId(position.getId()).setPositionName(position.getPositionName()).setSalary(position.getSalary()).setAddress(position.getAddress()).setDuty(position.getDuty()).setDemanding(position.getDemanding()).setWelfare(position.getWelfare()).setWorkTime(position.getWorkTime())
                .setAvatar(company.getAvatar()).setCompanyName(company.getCompanyName()).setLocation(company.getLocation()).setScale(company.getScale())
                .setPubName(pubperson.getPubName()).setDeptName(pubperson.getDeptName())
                .setCheckStatus(position.getCheckStatus());
        Assert.isServiceValid(jobDetail.getCheckStatus()==false,"该职位未审核");
        return jobDetail;
    }

    /**
     * 基于positionId删除职位
     * @param positionId
     * @return
     */
    @Override
    @Transactional
    public int deleteOnejob(Long positionId) {
        Assert.isArgumentValid(positionId==0,"职位id找不到");
        int rows = positionMapper.deleteOnejob(positionId);
        Assert.isServiceValid(rows==0,"未能删除记录");
        CompanyPosition companyPosition = companyPositionMapper.selectByPositionId(positionId);
        rows = companyPositionMapper.deleteOnejob(positionId);
        Assert.isServiceValid(rows==0,"未能删除记录");
        rows = pubpersonMapper.deleteOnejob(companyPosition.getPubpersonId());
        Assert.isServiceValid(rows==0,"未能删除记录");
        return rows;
    }

    /**
     * 基于token验证是否是求职者
     * 基于token获取employeeId
     * 基于positonId 新增记录
     * @param positionId
     * @param token
     * @return
     */
    @Override
    public int doApplyForPosition(Long positionId, String token) {
        return 0;
    }

    /**
     * 分页+模块查询查询视图jb_web_job_detail
     *
     * @param positionName
     * @param address
     * @return
     */
    @Transactional
    @Override
    public IndexPositionPageObject doFindIndexPositionItem(String positionName, String address) {
        int rowcount = webJobDetailMapper.getIndexPositionItem(positionName,address,0,8);
        List<IndexPositionItem> indexPositionItem = webJobDetailMapper.findIndexPositionItem(positionName, address, 0, 8);
        Assert.isServiceValid(indexPositionItem.isEmpty(),"未找到相关内容");
        //封装vo对象
        IndexPositionPageObject positionPageObject = new IndexPositionPageObject();
        positionPageObject.setRecords(rowcount).setPositionItems(indexPositionItem);
        return positionPageObject;
    }


}
