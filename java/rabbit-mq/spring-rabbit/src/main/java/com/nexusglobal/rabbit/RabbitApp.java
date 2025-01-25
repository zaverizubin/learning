package com.nexusglobal.rabbit;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.nexusglobal.rabbit.view.LoginView;
import com.nexusglobal.rabbit.view.AppView;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@SpringBootApplication
public class RabbitApp 
{
	public static void main(String[] args ){
    	
    	
		ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(RabbitApp.class)
				.headless(false)
				.run(args);
		
		EventQueue.invokeLater(() -> {
			AppView appView = applicationContext.getBean(AppView.class);
			appView.setVisible(true);
			appView.setLocationRelativeTo(null);
		});
    }
   
   
}
