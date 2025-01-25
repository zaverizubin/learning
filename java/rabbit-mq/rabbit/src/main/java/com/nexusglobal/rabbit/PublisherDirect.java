package com.nexusglobal.rabbit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PublisherDirect extends Publisher{
	
	protected String exchange = "direct exchange";
	
	public PublisherDirect(RabbitApp rabbitApp, int publisherId) {
		this.rabbitApp = rabbitApp;
		this.publisherId = publisherId;
		this.exchangeTypeEnum = ExchangeTypeEnum.DIRECT;
	}
	
	@Override
	protected void declareQueues() throws IOException {
    	boolean durable = false;    //durable - RabbitMQ will never lose the queue if a crash occurs
		boolean exclusive = false;  //exclusive - if queue only will be used by one connection
		boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes
		
		for(String queue: this.rabbitApp.getQueueManager().getDirectQueues().keySet()) {
			this.channel.queueDeclare(queue, durable, exclusive, autoDelete, null);
			this.channel.queueBind(queue, this.exchange, this.rabbitApp.getQueueManager().getDirectQueues().get(queue));
		}
	}
	
	@Override
	protected String getRandomRountingKey() {
		List<String> routingKeys = new ArrayList<>();
		for(String queue: this.rabbitApp.getQueueManager().getDirectQueues().keySet()) {
			routingKeys.add(this.rabbitApp.getQueueManager().getDirectQueues().get(queue));
		}
		return routingKeys.get(new Random().nextInt(routingKeys.size())); 
	}
	
	
}
