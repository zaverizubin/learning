package com.nexusglobal.rabbit.view;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

import com.nexusglobal.rabbit.RabbitWorker;


@Component
public class LoginPresenter {
	// Autowired fields
	
	private RabbitWorker rabbitWorker;
	
	
	public LoginPresenter(final RabbitWorker rabbitWorker) {
		this.rabbitWorker = rabbitWorker;
	}


	public void connectToServer(String uri) throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException  {
		this.rabbitWorker.buildConnection(uri);
		
	}
	
}
