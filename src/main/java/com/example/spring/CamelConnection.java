package com.example.spring;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class CamelConnection extends RouteBuilder {

    Application.MqttServiceConfigurable service;


    public CamelConnection(Application.MqttServiceConfigurable service){
        this.service = service;
    }

    @Override
    public void configure() throws Exception {
        // getting Riddle from MQTT Server

        String topic = "";
        String brokerUrl =  "";
        String userName = "";
        String password = "";

        from("paho:" + topic + "?" + "brokerUrl=" + brokerUrl +"&userName=" + userName + "&password=" + password)
                .process(new RiddleProcessor());
    }

    public class RiddleProcessor implements Processor {
        @Override
        public void process(Exchange exchange) throws Exception {
            service.addReport("-------------------------");
            service.addReport(exchange.getIn().getBody(String.class));
        }
    }
}