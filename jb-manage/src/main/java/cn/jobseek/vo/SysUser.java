package cn.jobseek.vo;

import cn.jobseek.pojo.SysRole;
import cn.jobseek.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysUser implements Serializable {
    private Long id;                //id
    private String account;         //账号
    private String roleName;        //角色名字
    private String email;           //邮箱
    private String mobile;          //手机
    private Boolean valid;           //合法性
    private Date createdTime ;       //创建时间
    private Date modifiedTime ;      //修改时间
    private String createdUser ;       //创建人

}
