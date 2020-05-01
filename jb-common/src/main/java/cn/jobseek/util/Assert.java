package cn.jobseek.util;

import cn.jobseek.exception.ServiceException;

import javax.xml.ws.Service;

public class Assert {

    /**
     * 判断参数合法性
     * @param statement
     * @param message
     */
    public static void isArgumentValid(boolean statement,String message){
        if (statement){
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 验证业务合法性
     * @param statement
     * @param message
     */
    public static void isServiceValid(boolean statement,String message){
        if (statement){
            throw new ServiceException(message);
        }
    }

}
