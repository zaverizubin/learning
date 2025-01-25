package com.nexusglobal.rabbit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PublisherDirect extends Publisher{
	
	protected String exchange = "direct exchange";
	
	public PublisherDirect(RabbitWorker rabbitWorker, int publisherId) {
		this.rabbitWorker = rabbitWorker;
		this.publisherId = publisherId;
		this.exchangeTypeEnum = ExchangeTypeEnum.DIRECT;
	}
	
	@Override
	protected void declareQueues() throws IOException {
    	boolean durable = false;    //durable - RabbitMQ will never lose the queue if a crash occurs
		boolean exclusive = false;  //exclusive - if queue only will be used by one connection
		boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes
		
		for(String queue: this.rabbitWorker.getQueueManager().getDirectQueues().keySet()) {
			this.channel.queueDeclare(queue, durable, exclusive, autoDelete, null);
			this.channel.queueBind(queue, this.exchange, this.rabbitWorker.getQueueManager().getDirectQueues().get(queue));
		}
	}
	
	@Override
	protected String getRandomRountingKey() {
		List<String> routingKeys = new ArrayList<>();
		for(String queue: this.rabbitWorker.getQueueManager().getDirectQueues().keySet()) {
			routingKeys.add(this.rabbitWorker.getQueueManager().getDirectQueues().get(queue));
		}
		return routingKeys.get(new Random().nextInt(routingKeys.size())); 
	}
	
	
}
