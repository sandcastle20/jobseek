package cn.jobseek.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	/**
	 * 1.删除cookie
	 * 参数：1.传递response对象
	 * 	   2.cookieName
	 *     3.domain
	 *     4.path
	 */
	public static void deleteCookie(HttpServletResponse response,
									String cookieName,
									String domain,
									String path) {
		//删除cookie需要覆盖之前的数据
		Cookie cookie = new Cookie(cookieName, "");
		cookie.setDomain(domain);
		cookie.setPath(path);
		cookie.setMaxAge(0);
		//cookie.setMaxAge(-1);//关闭会话，立即删除cookie
		response.addCookie(cookie);
		
	}
	
	/**
	 * 根据cookie名称获取cookie对象
	 */
	public static Cookie getCookie(HttpServletRequest request,String cookieName) {
		//动态获取指定的cookie信息
		Cookie[] cookies = request.getCookies();
		String ticket = null;
		if (cookies !=null && cookies.length>0 ) {	//遍历数组一定要先判断是否为空
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					ticket = cookie.getValue();
					return cookie;
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据cookie 名称获取value值
	 */
	public static String getCookieValue(HttpServletRequest request,String cookieName) {
		//动态获取指定的cookie信息
		Cookie[] cookies = request.getCookies();
		String ticket = null;
		if (cookies !=null && cookies.length>0 ) {	//遍历数组一定要先判断是否为空
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					ticket = cookie.getValue();
					break;
				}
			}
		}
		return ticket;
	}
}
