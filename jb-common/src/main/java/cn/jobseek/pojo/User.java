package cn.jobseek.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
public class User implements Serializable {
    private static final long serialVersionUID = -5092112538501509589L;
    private Long id;                //id
    private String account;         //账号
    private String pwd;             //密码
    private String salt;            //盐值
    private String email;           //邮箱
    private String mobile;          //手机
    private Boolean valid ;    //合法性
    private Date createdTime ;       //创建时间
    private Date modifiedTime ;      //修改时间
    private String createdUser ;       //创建人

}
