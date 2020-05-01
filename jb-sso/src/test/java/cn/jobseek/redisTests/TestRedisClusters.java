package cn.jobseek.redisTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.JedisCluster;

@SpringBootTest
public class TestRedisClusters {

    @Autowired
    private JedisCluster jedisCluster;

    @Test
    public void testCluster(){
        String key = "redis";
        String value = "集群测试成功";
        jedisCluster.set(key,value);
        System.out.println(jedisCluster.get(key));
    }
}
