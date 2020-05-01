package cn.jobseek.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "page.config")
@Configuration
public class PaginationProperties {
    private Integer pageSize;

    public Integer getstartIndex(Integer pageCurrent){
        return (pageCurrent-1)*pageSize;
    }
}
