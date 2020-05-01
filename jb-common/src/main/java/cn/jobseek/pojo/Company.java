package cn.jobseek.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Company implements Serializable {
    private static final long serialVersionUID = -9032632238911037981L;
    private Long id;
    private Long userId;            //用户id
    private String companyName;     //公司名
    private String location;        //地址 国家 省 市
    private String phone;           //手机
    private String email;           //邮箱
    private String scale;          //公司员工数
    private String tip;             //公司简介
    private String avatar;          //头像url
    private Date createdTime;        //创建时间
    private Date modifiedTime ;      //修改时间
    private String createdUser ;       //创建人

}
