package cn.jobseek.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
public class UserRole implements Serializable {
    private static final long serialVersionUID = -4461468803430559540L;
    private Long id;                //id
    private Long userId;            //用户id
    private Long roleId;            //角色id
    private Date createdTime ;       //创建时间
    private Date modifiedTime ;      //修改时间
    private String createdUser ;       //创建人
}
