package cn.jobseek.mapper;

import cn.jobseek.vo.PositionItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SsoWebPositionitemMapperTests {

    @Autowired
    private SsoWebPositionitemMapper webPositionitemMapper;

    @Test
    public void selectObjectByPositionId(){
        Long[] id = new Long[]{2L, 14L, 15L};
        List<PositionItem> positionItems = webPositionitemMapper.selectObjectByPositionId(id);
        positionItems.forEach(System.out::println);
    }
}
