package com.nexusglobal.rabbit;

import java.io.IOException;

import com.rabbitmq.client.Channel;

public abstract class Publisher {
	
	protected RabbitWorker rabbitWorker;
	protected ExchangeTypeEnum exchangeTypeEnum;
	protected Channel channel;
	
	protected boolean canPublish = true;
	protected int publisherId;
	protected String exchange;

	public void publish(String message) {
		try {
			buildChannel();
			declareExchange();
			declareQueues();
			publishMessage(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void buildChannel() throws IOException {
    	this.channel = this.rabbitWorker.getPublisherConnection().createChannel();
    }
	
	private void declareExchange() throws IOException {
		this.channel.exchangeDeclare(this.exchange, this.exchangeTypeEnum.getName(), true);
    }
	
	protected abstract void declareQueues() throws IOException;
	
	private void publishMessage(String message)  {
		while(this.canPublish) {
			try {
				this.channel.basicPublish(this.exchange, getRandomRountingKey(), null, message.getBytes());
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	protected abstract String getRandomRountingKey();

	public int getId() {
		return publisherId;
	}
	
	public void pause() {
		this.canPublish = false;
	}
	
	public void resume() {
		this.canPublish = true;
	}
}
