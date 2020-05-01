package cn.jobseek.service;

import cn.jobseek.pojo.Company;
import cn.jobseek.pojo.Position;
import cn.jobseek.pojo.Pubperson;
import cn.jobseek.pojo.User;

public interface DubboCompanyService {

    Company doFindCompany(Long companyId);

    int doSaveCompany(Company company);

    User doFindCompanyContactWay(String userJson);

    int doSaveCompanyContactWay(String token, User user, Long companyId);

    int updateAvatarByUserId(String token, String url);

    int doPushPosition(String token, Long compId, Position position, Pubperson pubperson);
}
