package com.nexusglobal.rabbit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@Component
public class RabbitWorker {
	
	Connection publisherConnection;
	Connection consumerConnection;
	QueueManager queueManager;
	
	List<Publisher> publishers = new ArrayList<>();
	
	
	public RabbitWorker() throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException {
    	
		this.queueManager = new QueueManager();
    }
    
    public void buildConnection(String uri) throws KeyManagementException, NoSuchAlgorithmException, URISyntaxException, IOException {
    	ConnectionFactory factory = new ConnectionFactory();
    	factory.setUri(uri);
		factory.setRequestedHeartbeat(30);
		factory.setConnectionTimeout(30000);
		
		this.publisherConnection = factory.newConnection();
		this.consumerConnection = factory.newConnection();
    	  
    }
    
    public Connection getPublisherConnection() {
    	return this.publisherConnection;
    }
    
    public Connection getConsumerConnection() {
    	return this.consumerConnection;
    }
    
    public QueueManager getQueueManager() {
    	return queueManager;
    }
    
	public void addQueue(ExchangeTypeEnum exchangeType,  String queueName, String routingKey) {
		switch(exchangeType) {
			case DIRECT:
				this.queueManager.addDirectQueue(queueName, routingKey);
				break;
			case TOPIC:
				this.queueManager.addTopicQueue(queueName, routingKey);
				break;
			case FANOUT:
				this.queueManager.addFanoutQueue(queueName, routingKey);
				break;
		}
	}
	
   public void addPublisher(ExchangeTypeEnum exchangeType) {
	   Runnable runnable;
	   Publisher publisher;
	   
	   switch(exchangeType) {
			case DIRECT:
				publisher = new PublisherDirect(this, this.publishers.size()+1);
				this.publishers.add(publisher);
				runnable = ()->{publisher.publish("Publisher Direct - " + this.publishers.size());};
				break;
			case TOPIC:
				publisher = new PublisherTopic(this, this.publishers.size()+1);
				this.publishers.add(publisher);
				runnable = ()->{publisher.publish("Publisher Topic - " + this.publishers.size());};
				break;
			case FANOUT:
				default:
				publisher = new PublisherFanout(this, this.publishers.size()+1);
				this.publishers.add(publisher);
				runnable = ()->{publisher.publish("Publisher Fanout - " + this.publishers.size());};
				break;
	  }
	  Thread thread = new Thread(runnable); 
	  thread.start();
   }
   
   public void pausePublisher(int publisherId) {
	  for(Publisher publisher:this.publishers) {
		  if(publisher.getId() == publisherId) {
			  publisher.pause();
			  break;
		  }
	  }
   }
   
   public void resumePublisher(int publisherId) {
	  for(Publisher publisher:this.publishers) {
		  if(publisher.getId() == publisherId) {
			  publisher.resume();
			  break;
		  }
	  }
   }
}
