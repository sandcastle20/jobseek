package cn.jobseek.pojo;


import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.shiro.authc.IncorrectCredentialsException;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Employee implements Serializable {

    private static final long serialVersionUID = -1903166682651526852L;

    private Long id;         //求职id
    private Long userId;     //用户id
    private String userName;    //求职者姓名
    private Boolean sex;        //性别
    private Date birthday;      //生日
    private String school;      //学校
    private String major;       //专业
    private String salary;      //待遇
    private String tip;         //简介
    private String avatar;      //头像url
    private Date createdTime;        //创建时间
    private Date modifiedTime ;      //修改时间
    private String createdUser ;       //创建人

}
