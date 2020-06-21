package com.github.esbagent.messaging;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.github.esbagent.service.JobService;
@Component
public class ESBAgentListener {
	
	@Autowired
	private JobService jobService;
	
	@JmsListener(destination = "esb.test.queue")
	public void receiveInstruction(String status) throws JMSException {
		System.out.println("Received message : "+status);
		String[] message = status.split("\\|",10);
		if(message[0].equalsIgnoreCase("DEPLOY")) {
			System.out.println("Deployment Started");
			jobService.deployJob(message[1], message[4], message[2]+"_"+message[3]+".kar");
		}
		if(message[0].equalsIgnoreCase("UNDEPLOY")) {
			System.out.println("Job UNDEPLOY Started");
			jobService.undeployJob(message[1], message[2]+"_"+message[3]+".kar");
		}
		
	}
}
