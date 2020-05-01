package cn.jobseek.service;

import cn.jobseek.pojo.Employee;
import cn.jobseek.pojo.Resume;
import cn.jobseek.pojo.User;

public interface DubboEmployeeService {

    Employee doFindEmployee(String token);

    int doUpdateEmployee(Employee employee);

    int updateAvatarByUserId(String token,String rPath);

    User doFindContactWay(String token);

    int saveContactWay(User user, String token);

}
