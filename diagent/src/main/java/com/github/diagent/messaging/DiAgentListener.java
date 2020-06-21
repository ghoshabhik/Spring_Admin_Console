package com.github.diagent.messaging;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.github.diagent.service.JobService;
@Component
public class DiAgentListener {
	
	@Autowired
	private JobService jobService;
	
	@JmsListener(destination = "test.queue")
	public void receiveInstruction(String status) throws JMSException {
		System.out.println("Received message : "+status);
		String[] message = status.split("\\|",10);
		if(message[0].equalsIgnoreCase("DEPLOY")) {
			System.out.println("Deployment Started");
			jobService.deployJob(message[1], message[4], message[2]+"_"+message[3]+".zip");
		}
		if(message[0].equalsIgnoreCase("TRIGGER")) {
			System.out.println("Job Run Started");
			jobService.triggerJob(message[1], message[2]);
		}
		if(message[0].equalsIgnoreCase("UNDEPLOY")) {
			System.out.println("Job UNDEPLOY Started");
			jobService.undeployJob(message[1]);
		}
		
	}
}
