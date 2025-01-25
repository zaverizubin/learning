package com.nexusglobal.rabbit;

public enum ExchangeTypeEnum {
	
	DIRECT("direct"),
	TOPIC("topic"),
	FANOUT("fanout");
	
	private String name;
	
	ExchangeTypeEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
}
