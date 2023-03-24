package com.example.spring;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		MqttServiceConfigurable service = context.getBean(MqttServiceConfigurable.class);

		CamelConnection clientRiddle = new CamelConnection(service);
		CamelResultConnection clientResult = new CamelResultConnection(service);
		CamelContext camelContext = new DefaultCamelContext();

		try {
			camelContext.addRoutes(clientRiddle);
			camelContext.addRoutes(clientResult);
			camelContext.start();
			//camelContext.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Configuration
	public class MqttServiceConfiguration{
		@Bean
		public MqttServiceConfigurable configurable(){
			MqttServiceConfigurable configurable = new MqttServiceConfigurable();
			configurable.setReport("");
			return configurable;
		}

	}

	public class MqttServiceConfigurable implements MqttService{

		private String report = "";

		@Override
		public String getReport() {
			return report;
		}

		public void setReport(String value){
			report = value;
		}

		public void addReport(String value){
			report += value + "<br>";
		}
	}
}
