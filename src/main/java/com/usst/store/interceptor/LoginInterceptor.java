package com.usst.store.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义一个拦截器，防止恶意登录
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 在调用所有处理请求的方法之前被自动调用执行的方法
     * 检测全局session对象中是否有uid数据，有则放行，无则重定向到登录页面
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器(url和controller的映射)
     * @return 如果返回值为true，表示放行，否则拦截
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // HttpServletRequest对象来获取session对象
        Object uid = request.getSession().getAttribute("uid");
        if (uid == null) {  // 说明用户没有登陆过系统，则重定向到登录页面
            response.sendRedirect("/web/login.html");
            return false;
        }
        return true;
    }

    // 在ModelAndView对象返回之后被调用的方法
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    // 在整个请求所有关联的资源被执行完毕最后执行的方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
