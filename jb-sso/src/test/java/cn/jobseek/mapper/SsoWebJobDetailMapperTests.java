package cn.jobseek.mapper;

import cn.jobseek.vo.IndexPositionItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SsoWebJobDetailMapperTests {

    @Autowired
    private SsoWebJobDetailMapper webJobDetailMapper;

    @Test
    public void webJobDetailMapper(){
        List<IndexPositionItem> indexPositionItem = webJobDetailMapper.findIndexPositionItem(null, null, 0, 8);
        indexPositionItem.forEach(System.out::println);
        int rowcount = webJobDetailMapper.getIndexPositionItem(null, null, 0, 8);
        System.out.println(rowcount);
    }

}
