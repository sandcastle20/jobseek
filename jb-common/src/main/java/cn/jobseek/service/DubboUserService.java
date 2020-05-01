package cn.jobseek.service;

import cn.jobseek.pojo.Company;
import cn.jobseek.pojo.Employee;
import cn.jobseek.pojo.User;

public interface DubboUserService {

    int doRegisterUser(Employee employee, User user, Long roleId);

    String doLogin(String account, String pwd);

    int doRegisterCompany(Company company, User user, Long roleId);


}
