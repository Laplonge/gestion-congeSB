package fr.formation.inti.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class SessionInterceptor extends HandlerInterceptorAdapter {

	private static final Log log = LogFactory.getLog(SessionInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
        log.info("request.getRequestURL() = " + request.getRequestURL());
        log.info("\n-------- SessionInterceptor.preHandle ------ ");
        
        //exceptions à l'interceptor
		if (request.getRequestURI().equals("/") 
				|| request.getRequestURI().equals("/connexion")
				|| request.getRequestURI().equals("/css/style.css")) { 
			log.info("URI exception, ça va pour cette fois. mais connectez-vous !");
			return true; 
		}
		
		String loginSession = (String) request.getSession().getAttribute("loginSession");
		if (loginSession == null) {
			log.info("personne n'est connecté");
			loginSession = "personne";
		}
		if (loginSession.equals("personne")) {
			log.info("loginSession == null");
	        response.sendRedirect(request.getContextPath() + "/");
	        return false;
		}
		log.info("connecté en tant que " + loginSession);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

        log.info("\n-------- SessionInterceptor.postHandle ------ ");		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

        log.info("\n-------- SessionInterceptor.afterCompletion ------ ");
	}

}