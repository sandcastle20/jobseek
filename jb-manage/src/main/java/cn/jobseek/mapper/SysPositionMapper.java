package cn.jobseek.mapper;

import cn.jobseek.vo.SysPosition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Mapper
public interface SysPositionMapper {

    //基于职位名，获取数据总记录数（从而计算总页数）
    int getRowCount(@Param("positionName") String positionName);

    //基于职位名，获取职位记录，分页显示
    List<SysPosition> getPageObject(@Param("positionName") String positionName,
                                           @Param("startIndex") Integer startIndex,
                                           @Param("pageSize") Integer pageSize);

    //基于职位id审核职位
    int checkObjects( @Param("ids") Long... ids);
    //基于职位修改时间
    int saveCheckTime(@Param("checkTime") Date checkTime,@Param("ids") Long... ids);

}
