package fr.formation.inti.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
 
@Component
public class LogInterceptor extends HandlerInterceptorAdapter {

	private static final Log log = LogFactory.getLog(LogInterceptor.class);
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("\n-------- LogInterception.preHandle --- ");
        log.info("Request URL: " + request.getRequestURL());
        log.info("Start Time: " + System.currentTimeMillis());
 
        request.setAttribute("startTime", startTime);
          
        return true;
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    	log.info("\n-------- LogInterception.postHandle --- ");
    	log.info("Request URL: " + request.getRequestURL());
 
        // You can add attributes in the modelAndView
        // and use that in the view page
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    	log.info("\n-------- LogInterception.afterCompletion --- ");
 
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        log.info("Request URL: " + request.getRequestURL());
        log.info("End Time: " + endTime);
 
        log.info("Time Taken: " + (endTime - startTime));
    }
 
}