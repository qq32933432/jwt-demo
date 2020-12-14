package com.bxoon.aspect;

import com.bxoon.exception.JeecgBootException;
import com.bxoon.util.JwtUtil;
import com.bxoon.util.SpringContextUtils;
import com.bxoon.util.oConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：GuangxiZhong
 * @date ：Created in 2020/12/14 10:38
 * @description：拦截用户有没有登录
 * @modified By：
 * @version: 1.0
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    // 定义切点Pointcut
    @Pointcut("execution(public * com.bxoon..*.*Controller.*(..))")
    public void excudeService() {
    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long time1=System.currentTimeMillis();
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        String requestMethod = request.getMethod();
        String requestPath = request.getRequestURI().substring(request.getContextPath().length());
        requestPath = filterUrl(requestPath);
        log.info("拦截请求>>"+requestPath+";请求类型>>"+requestMethod);

        // 实际项目中可以考虑通过配置的方式配置不需要拦截的URL LIST
        if ("/login".equals(requestPath)){
            Object result = pjp.proceed();
            long time2=System.currentTimeMillis();
            log.debug("获取JSON数据 耗时："+(time2-time1)+"ms");
            return result;
        }
        try {
            String username = JwtUtil.getUserNameByToken(request);
        }catch (JeecgBootException jeecgBootException){
            return jeecgBootException.getMessage();
        }
        // 用户登录成功之后考虑把用户放到session里面或者redis里面，以便controller中获取当前用户
        Object result = pjp.proceed();
        long time2=System.currentTimeMillis();
        log.debug("获取JSON数据 耗时："+(time2-time1)+"ms");
        return result;
    }

    private String filterUrl(String requestPath){
        String url = "";
        if(oConvertUtils.isNotEmpty(requestPath)){
            url = requestPath.replace("\\", "/");
            url = requestPath.replace("//", "/");
            if(url.indexOf("//")>=0){
                url = filterUrl(url);
            }
			/*if(url.startsWith("/")){
				url=url.substring(1);
			}*/
        }
        return url;
    }
}
