package com.kute.util.spring;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * @author bl
 *
 */
public class ApplicationContextPostListener implements ServletContextListener{

	private static final Logger logger = LoggerFactory.getLogger(ApplicationContextPostListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("Invoke ApplicationContextPostListener ContextInitialized");
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		SpringContextHolder.setApplicationContext(applicationContext);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("Invoke ApplicationContextPostListener ContextDestroyed");
	}

}
