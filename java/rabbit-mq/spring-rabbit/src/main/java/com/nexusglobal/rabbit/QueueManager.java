package com.nexusglobal.rabbit;

import java.util.HashMap;
import java.util.Map;

public class QueueManager {

	HashMap<String, String> directQueues =  new HashMap<>();
	HashMap<String, String> fanoutQueues =  new HashMap<>();
	HashMap<String, String> topicQueues =  new HashMap<>();
	
	public Map<String, String> getDirectQueues() {
		if(this.directQueues.isEmpty()) {
			this.directQueues.put("direct-queue-1", "direct-key-1");
			this.directQueues.put("direct-queue-2", "direct-key-2");
		}
		return this.directQueues;
	}
	
	public Map<String, String> getTopicQueues() {
		if(this.topicQueues.isEmpty()) {
			this.topicQueues.put("topic-queue-1", "topic-key.*");
			this.topicQueues.put("topic-queue-2", "topic-key.*");
		}
		return this.topicQueues;
	}
	
	public Map<String, String> getFanoutQueues() {
		if(this.fanoutQueues.isEmpty()) {
			this.fanoutQueues.put("fanout-queue-1", "fanout-key-1");
			this.fanoutQueues.put("fanout-queue-2", "fanout-key-2");
		}
		return this.fanoutQueues;
	}

	public void addDirectQueue(String queueName, String routingKey) {
		if(!this.directQueues.containsKey(queueName)) {
			this.directQueues.put(queueName, routingKey);
		}
	}
	
	public void addTopicQueue(String queueName, String routingKey) {
		if(!this.topicQueues.containsKey(queueName)) {
			this.topicQueues.put(queueName, routingKey);
		}
	}
	
	public void addFanoutQueue(String queueName, String routingKey) {
		if(!this.fanoutQueues.containsKey(queueName)) {
			this.fanoutQueues.put(queueName, routingKey);
		}
	}
}
