package io.transwarp.esb.aop.logging;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * @description 拦截请求-打印日志
 * @author wangyan
 * @date 2019/7/11 15:36
 */
//@Aspect
//@Component
//@EnableAspectJAutoProxy(proxyTarge
// Class=true)

public class LoggingAspect {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* io.transwarp.esb.controller.*(..))")
    public void controllerLog(){

    }

//    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
//            " || within(@org.springframework.stereotype.Service *)" +
//            " || within(@org.springframework.web.bind.annotation.RestController *)")
////    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
////            "|| @annotation(org.springframework.web.bind.annotation.PostMapping) " +
////            "|| within(@org.springframework.web.bind.annotation.RestController *)")
//    public void controllerLog() {
//    }
//
//    @Pointcut("within(io.transwarp.esb.controller..*)"+
//             "||within(io.transwarp.esb.service..*)")
//    public void packageLog(){
//
//    }



    @Around("controllerLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("test-------");

        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 设置请求开始时间
        Long startTime = System.currentTimeMillis();
        // 提取全部参数 paramJson
        Enumeration<String> paramNames = request.getParameterNames();
        JSONObject paramJson = new JSONObject();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            paramJson.put(paramName, request.getParameter(paramName));
        }
        //controller中接收参数的实体类
        StringBuilder param = new StringBuilder();
        if (joinPoint.getArgs() != null) {
            // param.append(StringUtils.join(joinPoint.getArgs(),','));
            Arrays.asList(joinPoint.getArgs()).forEach(obj -> param.append(",").append(obj));
        }
        //相应参数类和抛出异常处理
        Object result = null;
        Throwable exception = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            exception = throwable;
            throw throwable;
        } finally {
            logger.info(request.getMethod() + " "
                    + request.getRequestURL() + " 参数：" + paramJson.toJSONString() + param + " ," + (System.currentTimeMillis() - startTime) + "ms," + " 响应结果：" + result, exception);
        }
        return result;
    }

}
