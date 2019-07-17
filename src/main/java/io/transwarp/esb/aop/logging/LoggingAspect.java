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
 * @description
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

        //���յ����󣬼�¼��������
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // ��������ʼʱ��
        Long startTime = System.currentTimeMillis();
        // ��ȡȫ������ paramJson
        Enumeration<String> paramNames = request.getParameterNames();
        JSONObject paramJson = new JSONObject();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            paramJson.put(paramName, request.getParameter(paramName));
        }
        //controller�н��ղ�����ʵ����
        StringBuilder param = new StringBuilder();
        if (joinPoint.getArgs() != null) {
            // param.append(StringUtils.join(joinPoint.getArgs(),','));
            Arrays.asList(joinPoint.getArgs()).forEach(obj -> param.append(",").append(obj));
        }
        //��Ӧ��������׳��쳣����
        Object result = null;
        Throwable exception = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            exception = throwable;
            throw throwable;
        } finally {
            logger.info(request.getMethod() + " "
                    + request.getRequestURL() + " ������" + paramJson.toJSONString() + param + " ," + (System.currentTimeMillis() - startTime) + "ms," + " ��Ӧ�����" + result, exception);
        }
        return result;
    }

}
