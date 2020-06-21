package com.github.esbagent.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ESBAgentProducer {
	
	@Autowired
	JmsTemplate jmsTemplate;

	public void sendMessage(final String queueName, final String message) {

		final String textMessage = message; 
		System.out.println("Sending message " + textMessage + " to queue - " + queueName);
		jmsTemplate.convertAndSend(queueName, message);
	}

}
