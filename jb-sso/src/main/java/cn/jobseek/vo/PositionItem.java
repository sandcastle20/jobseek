package cn.jobseek.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 企业发布的职位浏览
 */
@Data
@Accessors(chain = true)
public class PositionItem implements Serializable {

    private static final long serialVersionUID = -2087989253409121779L;
    private Long id;
    private String pubName;
    private String positionName;
    private String address;
    private String companyName;
    private Boolean checkStatus;

}
