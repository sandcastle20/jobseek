package cn.jobseek.mapper;

import cn.jobseek.vo.PositionItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SsoWebPositionitemMapper {

    List<PositionItem> selectObjectByPositionId(@Param("positionIds") Long[] positionIds);

}
