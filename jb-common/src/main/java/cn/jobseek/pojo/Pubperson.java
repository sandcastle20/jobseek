package cn.jobseek.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Pubperson implements Serializable {

    private static final long serialVersionUID = 850785014562745863L;

    private Long id;
    private String deptName;        //发布人所属部门名称
    private String pubName;         //发布人姓名
    private Date createdTime ;       //创建时间
    private Date modifiedTime ;      //修改时间
    private String createdUser ;       //创建人

}
