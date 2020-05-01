package cn.jobseek.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class SysRole implements Serializable {

    private Long id;     //角色id
    private String name;    //角色名字
    private String note;    //角色简介
    private Date createdTime ;       //创建时间
    private Date modifiedTime ;      //修改时间
    private String createdUser ;       //创建人


}
