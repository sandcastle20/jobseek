package cn.jobseek.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Position implements Serializable {

    private static final long serialVersionUID = 3938215495831213230L;
    private Long id;
    private String positionName;        //职位名
    private String salary;      //薪资
    private String address;     //地址
    private String duty;        //工作职责
    private String demanding;      //岗位要求
    private String welfare;     //薪资福利
    private String workTime;    //工作时间
    private Boolean checkStatus;     //职位状态 1：发布 0：未发布
    private Date createdTime ;       //创建时间
    private Date modifiedTime ;      //修改时间
    private String createdUser ;       //创建人

}
