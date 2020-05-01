package cn.jobseek.config;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;

import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
public class SpringShiroConfig {

    public SessionManager SessionManager(){
        //定义默认会话管理
        DefaultSessionManager defaultSessionManager = new DefaultSessionManager();
        //设置会话时间
        defaultSessionManager.setGlobalSessionTimeout(60*60*1000);
        return defaultSessionManager;
    }


    //记住我
    @Bean
    public RememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        SimpleCookie rememberMeCookie = new SimpleCookie("rememberMe");
        rememberMeCookie.setMaxAge(7*24*60*60);
        cookieRememberMeManager.setCookie(rememberMeCookie);
        return cookieRememberMeManager;
    }

    //shiro中的缓存管理器，通过此缓存管理器对象中的缓存对象来对授权信息进行缓存
    @Bean
    public CacheManager shiroCacheManager(){
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public SecurityManager securityManager(Realm realm,CacheManager cacheManager,RememberMeManager rememberMeManager){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);//设置领域对象
        defaultWebSecurityManager.setCacheManager(cacheManager);//设置缓存管理器
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager);//设置记住我管理器
        return defaultWebSecurityManager;
    }

    @Autowired
    @Bean
    //shiro过滤器工厂bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        //1.构建ShiroFilterFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //2.shiroFilterFactoryBean注入securityManager对象
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //3.设置登录url
        shiroFilterFactoryBean.setLoginUrl("/doLoginUI");
        //4.设置过滤规则
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/bower_components/**","anon");
        filterChainDefinitionMap.put("/build/**","anon");
        filterChainDefinitionMap.put("/dist/**","anon");
        filterChainDefinitionMap.put("/plugins/**","anon");

        filterChainDefinitionMap.put("/user/doLogin","anon");
        filterChainDefinitionMap.put("/doLogout","logout");

        filterChainDefinitionMap.put("/**","user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
}
