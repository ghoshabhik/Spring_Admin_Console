package com.github.diagent.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.diagent.io.utils.DiskIOHandler;
import com.github.diagent.io.utils.ProcessHandler;
import com.github.diagent.messaging.DiAgentProducer;

@Service
public class JobService {
	
	@Value("${download.location}")
	private String workspacePath;
	
	@Autowired
	private DiskIOHandler handler;
	
	@Autowired
	private DiAgentProducer producer;
	
	@Autowired
	private ProcessHandler processHandler;
	
	public String deployJob(String jobId, String deployUrl, String fileName) {
		producer.sendMessage("test.status.queue", "DI_AGENT|"+jobId+"|DEPLOYING");
		//System.out.println(workspacePath+"~~~~~"+jobId+"~~~~~"+deployUrl);
		if(handler.dirExists(workspacePath+"\\"+jobId)) {
			
		}
		else {
			if(handler.mkDir(workspacePath+"\\"+jobId)) {
				handler.downloadArtifact(deployUrl, workspacePath+"\\"+jobId+"\\"+fileName);
			}
			
		}
		if(handler.fileExists(workspacePath+"\\"+jobId+"\\"+fileName)) {
			handler.unzip(workspacePath+"\\"+jobId+"\\"+fileName, workspacePath+"\\"+jobId+"\\job");
			producer.sendMessage("test.status.queue", "DI_AGENT|"+jobId+"|DEPLOYED");
			return "Deployed Successfully";
		} else {
			producer.sendMessage("test.status.queue", "DI_AGENT|"+jobId+"|DEPLOY_FAILED");
			return "Deployment Error, Please check manually";
		}
		
	}
	
	public String triggerJob(String jobId, String jobName) {
		producer.sendMessage("test.status.queue", "DI_AGENT|"+jobId+"|RUNNING");
		//System.out.println(workspacePath+"\\"+jobId+"\\job\\"+jobName+"~~~~~"+jobName+"_run.bat");
		String output = processHandler.triggerJob(workspacePath+"\\"+jobId+"\\job\\"+jobName, workspacePath+"\\"+jobId+"\\job\\"+jobName+"\\"+jobName+"_run.bat");
		producer.sendMessage("test.status.queue", "DI_AGENT|"+jobId+"|RAN|"+output);
		return output;
	}

	public void undeployJob(String jobId) {
		producer.sendMessage("test.status.queue", "DI_AGENT|"+jobId+"|UNDEPLOYING");
		handler.deleteFolder(new File(workspacePath+"\\"+jobId));
		producer.sendMessage("test.status.queue", "DI_AGENT|"+jobId+"|UNDEPLOYED");
		
	}

}
