package test.rest;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.star.cms.common.config.AppConfig;

public class RestServerLoader{
	Log log = LogFactory.getLog(RestServerLoader.class);
	private int port = 8080;
	private String ip = "0.0.0.0";

	public void afterPropertiesSet() throws Exception {
		List<Object> services = new ArrayList<Object>();
//		services.add(new )
		AppConfig.setServices(services);
		Server server = new Server(new InetSocketAddress(ip, port));
		// Register and map the dispatcher servlet
		final ServletHolder servletHolder = new ServletHolder(new CXFServlet());
		final ServletContextHandler context = new ServletContextHandler();
		context.setContextPath("/");
		context.addServlet(servletHolder, "/*");
		context.addEventListener(new ContextLoaderListener());

		context.setInitParameter("contextClass",
				AnnotationConfigWebApplicationContext.class.getName());
		context.setInitParameter("contextConfigLocation",
				AppConfig.class.getName());

		server.setHandler(context);
		server.start();
		server.join();

	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
