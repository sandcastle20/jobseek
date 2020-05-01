package cn.jobseek.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

//来自视图jb_web_job_detail
@Data
@Accessors(chain = true)
public class IndexPositionItem implements Serializable {
    private static final long serialVersionUID = -2806854148570628735L;
    private Long id;        //jb_position的id
    private String avatar;  //头像url
    private String positionName;    //职位名
    private String companyName;    //公司名
    private String address;         //工作地点
    private Date  createdTime;      //创建时间
}


