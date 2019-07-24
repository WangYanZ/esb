package io.transwarp.esb.aop.logging;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Aspect
@Component
//@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LoggingAspect {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Environment env;
    public LoggingAspect(Environment env) {
        this.env = env;
    }

    // 定义一个 Pointcut, 使用 切点表达式函数 来描述对哪些 Join point 使用 advise.
    @Pointcut("within(io.transwarp.esb.controller.EsbController)")
    public void controllerLog(){

    }

    // 定义 advise
    @Before("controllerLog()")
    public void logMethodInvokeParam(JoinPoint joinPoint) {
        logger.info("---Before method {} invoke, param: {}---", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "controllerLog()", returning = "retVal")
    public void logMethodInvokeResult(JoinPoint joinPoint, Object retVal) {
        logger.info("---After method {} invoke, result: {}---", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    @AfterThrowing(pointcut = "controllerLog()", throwing = "exception")
    public void logMethodInvokeException(JoinPoint joinPoint, Exception exception) {
        logger.info("---method {} invoke exception: {}---", joinPoint.getSignature().toShortString(), exception.getMessage());
    }
//    //在方法执行前拦截
//    @Before(value = "controllerLog()")
//    public void before(JoinPoint point) {
//        logger.info("BBBBBBBBBBBBBBBBBBBBBBBBefore method invoke,{}");
//    }



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



//    @Around("controllerLog()")
//    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        logger.info("test-------");
//
//        //���յ����󣬼�¼��������
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        // ��������ʼʱ��
//        Long startTime = System.currentTimeMillis();
//        // ��ȡȫ������ paramJson
//        Enumeration<String> paramNames = request.getParameterNames();
//        JSONObject paramJson = new JSONObject();
//        while (paramNames.hasMoreElements()) {
//            String paramName = paramNames.nextElement();
//            paramJson.put(paramName, request.getParameter(paramName));
//        }
//        //controller�н��ղ�����ʵ����
//        StringBuilder param = new StringBuilder();
//        if (joinPoint.getArgs() != null) {
//            // param.append(StringUtils.join(joinPoint.getArgs(),','));
//            Arrays.asList(joinPoint.getArgs()).forEach(obj -> param.append(",").append(obj));
//        }
//        //��Ӧ��������׳��쳣����
//        Object result = null;
//        Throwable exception = null;
//        try {
//            result = joinPoint.proceed();
//        } catch (Throwable throwable) {
//            exception = throwable;
//            throw throwable;
//        } finally {
//            logger.info(request.getMethod() + " "
//                    + request.getRequestURL() + " ������" + paramJson.toJSONString() + param + " ," + (System.currentTimeMillis() - startTime) + "ms," + " ��Ӧ�����" + result, exception);
//        }
//        return result;
//    }

}
