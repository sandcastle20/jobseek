package cn.jobseek.mapper;

import cn.jobseek.vo.IndexPositionItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface SsoWebJobDetailMapper  {

    List<IndexPositionItem> findIndexPositionItem(String positionName, String address, int startIndex, int pageSize);

    int getIndexPositionItem(String positionName, String address,int startIndex, int pageSize);
}
