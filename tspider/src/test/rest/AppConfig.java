package test.rest;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.star.cms.common.rest.JaxRsApiApplication;



@Configuration
public class AppConfig {

	private static List<Object> services;

	public static void setServices(List<Object> services) {
		AppConfig.services = services;
	}

	@Bean(destroyMethod = "shutdown")
	public SpringBus cxf() {
		return new SpringBus();
	}

	@Bean
	@DependsOn("cxf")
	public Server jaxRsServer() {
		JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance()
				.createEndpoint(jaxRsApiApplication(),
						JAXRSServerFactoryBean.class);
		// add more services
		factory.setServiceBeans(services);
		factory.setAddress(factory.getAddress());
		factory.setProviders(Arrays.<Object> asList(jsonProvider()));
		return factory.create();
	}
	@Bean
	public JaxRsApiApplication jaxRsApiApplication() {
		return new JaxRsApiApplication();
	}
	@Bean
	public JacksonJsonProvider jsonProvider() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Inclusion.NON_NULL);
		return new JacksonJsonProvider(mapper);
	}
}