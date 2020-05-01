package cn.jobseek.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class Resume implements Serializable {

    private static final long serialVersionUID = 625361009942475019L;

    private Long id;
    private Long employeeId;       //求职者id
    private String resumeUrl;       //简历url
    private String resumeName;      //简历名字
    private String createdUser;     //创建人
    private Date createdTime;       //创建时间
    private Date modifiedTime;    //修改时间

}
