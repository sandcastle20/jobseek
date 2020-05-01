package cn.jobseek.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class IndexPositionPageObject implements Serializable {
    private Integer records;
    private List<IndexPositionItem> positionItems;
}
