package cn.jobseek.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class JobDetail implements Serializable {
    private static final long serialVersionUID = -5818966601266372282L;
    private Long id;
    private String positionName;       //职位名
    private String salary;              //职位的月薪
    private String address;             //地址
    private Boolean checkStatus;        //职位状态 1：发布 0：未发布
    private String duty;                //工作职责
    private String demanding;           //岗位要求
    private String welfare;             //薪资福利
    private String workTime;            //工作时间

    private String avatar;              //头像url
    private String companyName;         //公司名
    private String location;            //地址 国家 省 市
    private String scale;               //公司员工数

    private String pubName;             //负责人名字
    private String deptName;            //部门名字

}
