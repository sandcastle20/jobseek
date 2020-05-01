package cn.jobseek.web;

import cn.jobseek.vo.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import redis.clients.jedis.exceptions.JedisClusterException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public JsonResult doHandleRuntimeException(RuntimeException e){
        e.printStackTrace();
        return JsonResult.fail(e);
    }



    @ExceptionHandler(JedisClusterException.class)
    public JsonResult doHandleRuntimeException(JedisClusterException e){
        e.printStackTrace();
        return JsonResult.fail(e,"CLUSTERDOWN The cluster is down");
    }

}
