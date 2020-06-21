package com.github.admin.messaging;

import java.util.Date;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.github.admin.model.TalendArtifact;
import com.github.admin.service.JobRunLogService;
import com.github.admin.service.JobService;

@Component
public class AdminListener {
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private JobRunLogService logService;
	
	@JmsListener(destination = "test.status.queue")
	public void receiveMessage(String status) throws JMSException {
		System.out.println("Received message " + status);
		String[] message = status.split("\\|",10);
		TalendArtifact existingArtifact = jobService.getArtifactById(message[1]);
		System.out.println(existingArtifact);
		existingArtifact.setDeployStatus(message[2]);
		if(message[2].equalsIgnoreCase("DEPLOYED")) {
			existingArtifact.setLastDeployedTimeStamp(new Date());
		}
		if(message[2].equalsIgnoreCase("RAN")) {
			existingArtifact.setLastRunTimeStamp(new Date());
			logService.saveLog(message[1], message[3]);
		}
		jobService.updateJobWithArtifact(existingArtifact);
		System.out.println(existingArtifact);
	}
}
