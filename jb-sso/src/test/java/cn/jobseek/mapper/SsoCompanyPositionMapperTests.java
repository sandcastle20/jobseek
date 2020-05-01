package cn.jobseek.mapper;

import cn.jobseek.pojo.CompanyPosition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class SsoCompanyPositionMapperTests {

    @Autowired
    private SsoCompanyPositionMapper companyPositionMapper;


    @Test
    public void StreamTests(){
        List<CompanyPosition> companyPositions = companyPositionMapper.doSelectByCompId((long) 7);
        companyPositions.forEach(System.out::println);

        List pIds = new ArrayList<>();
        companyPositions.stream().forEach(companyPosition-> pIds.add(companyPosition.getPositionId()));
        Long positionId[] = Arrays.stream(pIds.toArray()).toArray(Long[]::new);

    }
}
