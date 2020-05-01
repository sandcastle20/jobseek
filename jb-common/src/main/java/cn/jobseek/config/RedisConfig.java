package cn.jobseek.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {

    /**
     * springBoot整合Redis集群
     */
    @Value("${redis.clusters}")
    private String jedisClusters;

    @Bean
    @Scope("prototype")
    public JedisCluster jedisCluster() {
        Set<HostAndPort> setNodes = new HashSet<HostAndPort>();
        String[] nodes = jedisClusters.split(",");
        for (String node : nodes) {
            String host = node.split(":")[0];
            Integer port =Integer.parseInt(node.split(":")[1]);
            HostAndPort hostAndPort = new HostAndPort(host, port);
            setNodes.add(hostAndPort);
        }

        return new JedisCluster(setNodes);
    }
}
