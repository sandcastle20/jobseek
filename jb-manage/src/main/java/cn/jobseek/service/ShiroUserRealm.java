package cn.jobseek.service;

import cn.jobseek.mapper.SysUserMapper;
import cn.jobseek.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShiroUserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserMapper sysUserMapper;

    //设置凭证匹配器
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        //1.构建加密对象和指定算法
        HashedCredentialsMatcher md5 = new HashedCredentialsMatcher("MD5");
        //2.设置加密次数
        md5.setHashIterations(1);
        super.setCredentialsMatcher(md5);
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("jobseek:sys:login:--认证开始--");
        //1.基于token获取客户端输入的用户名
        UsernamePasswordToken uptoken = (UsernamePasswordToken)token;
        String account = uptoken.getUsername();
        //2.基于用户吗查询用户是否存在或者被禁用
        User user = sysUserMapper.findUserByUsername(account);
        if (user==null)
            throw new UnknownAccountException();//没有找到用户信息

        if (user.getValid()==false)
            throw new LockedAccountException();//用户被禁用
        //3.对查询到的用户信息进行封装
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                user, //principal 用户身份，结合业务自己赋予具体对象
                user.getPwd(), //hashedCredentials 已加密的密码
                credentialsSalt, //credentialsSalt 对登录密码进行加密时使用的盐
                getName());//realmName
        return simpleAuthenticationInfo;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }


}
