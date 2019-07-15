package io.transwarp.esb.aop.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static jdk.nashorn.internal.objects.Global.print;

/**
 * @author wangyan
 * @description
 * @date 2019/7/12 10:13
 */

@Component
public class BaseInterceptor extends HandlerInterceptorAdapter {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        info("***************************【RequestBeginning】***************************");
        info("----------------StartProcessingRequest----------------");
        try {
            Long currentTime = System.currentTimeMillis();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date(currentTime);
            info("CurrentTime：{}",formatter.format(date));
            info("ResponseTime:{}",(System.currentTimeMillis() - currentTime) + "ms");

            String requestURL = request.getRequestURI();
            info("RequestURL： {} ", requestURL);
            info("GetMethod: {}", handler);

//            Map<String, ?> params = request.getParameterMap();
//            if (!params.isEmpty()) {
//                info("RequestParams：");
//                print(request.getParameterMap(), "参数");
//            }
            //获取controller实体内容
//            StringBuilder stringBuilder = new StringBuilder();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//            if (null != stringBuilder) {
//                info("RequestBody： {}",stringBuilder.toString());
//            }
        } catch (Exception e) {
            error("MVC业务处理-拦截器异常：", e);
        }
        info("-------------------------End-------------------------");

        return true;

    }

    /**
     * This implementation is empty.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        logger.info("after");

    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        logger.info("Completion");

    }
    /**
     * This implementation is empty.
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response,
                                               Object handler) throws Exception {
        logger.info("Concurrent");

    }

    protected final void error(String msg, Object... objects) {
        logger.error(msg, objects);
    }
    protected final void info(String msg, Object... objects) {
        logger.info(msg, objects);
    }
}
