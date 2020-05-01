package cn.jobseek.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class SysPosition implements Serializable {

    private static final long serialVersionUID = -8631608958981895220L;
    private Long id;
    private String companyName;    //企业名字
    private String pubName;        //发布人名字
    private String positionName;   //职位名字
    private Integer applyCount;     //投递数
    private Integer collectCount;   //收藏数
    private Integer successCount;    //成功数
    private String isCheck;       //是否审核
    private Date checkTime;       //审核时间
}
