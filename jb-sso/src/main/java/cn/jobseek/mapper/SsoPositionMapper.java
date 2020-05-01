package cn.jobseek.mapper;

import cn.jobseek.pojo.Position;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

public interface SsoPositionMapper {

    int insertPosition(Position position);

    @Select("select * from jb_position where id = #{positionId}")
    Position selectByPositionId(Long positionId);


    int deleteOnejob(Long positionId);
}
