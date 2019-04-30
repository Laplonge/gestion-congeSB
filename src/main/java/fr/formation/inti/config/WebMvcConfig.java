package fr.formation.inti.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import fr.formation.inti.interceptors.LogInterceptor;
import fr.formation.inti.interceptors.SessionInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	LogInterceptor logInterceptor ;
	
	@Autowired
	SessionInterceptor sessionInterceptor ;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(logInterceptor); 
        registry.addInterceptor(sessionInterceptor);
	}

}