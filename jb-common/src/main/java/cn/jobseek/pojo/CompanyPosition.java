package cn.jobseek.pojo;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CompanyPosition implements Serializable {

    private Long id;
    private Long companyId;
    private Long pubpersonId;
    private Long positionId;

}
