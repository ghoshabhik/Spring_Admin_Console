package com.github.esbagent.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.esbagent.utils.DiskIOHandler;
import com.github.esbagent.messaging.ESBAgentProducer;

@Service
public class JobService {

	@Value("${download.location}")
	private String workspacePath;

	@Value("${deploy.location}")
	private String deployPath;

	@Autowired
	private DiskIOHandler handler;

	@Autowired
	private ESBAgentProducer producer;

	public String deployJob(String jobId, String deployUrl, String fileName) {
		producer.sendMessage("test.status.queue", "DI_AGENT|" + jobId + "|DEPLOYING");
		if (handler.dirExists(workspacePath + "\\" + jobId)) {

		} else {
			if (handler.mkDir(workspacePath + "\\" + jobId)) {
				handler.downloadArtifact(deployUrl, workspacePath + "\\" + jobId + "\\" + fileName);
			}

		}
		if(handler.fileExists(workspacePath+"\\"+jobId+"\\"+fileName)) {
			handler.moveFile(workspacePath+"\\"+jobId+"\\"+fileName, deployPath+"\\"+fileName);
			handler.deleteFolder(new File(workspacePath+"\\"+jobId));
			producer.sendMessage("test.status.queue", "ESB_AGENT|"+jobId+"|DEPLOYED");
			return "Deployed Successfully";
		} else {
			producer.sendMessage("test.status.queue", "ESB_AGENT|"+jobId+"|DEPLOY_FAILED");
			return "Deployment Error, Please check manually";
		}

	}

	public void undeployJob(String jobId, String fileName) {
		producer.sendMessage("test.status.queue", "DI_AGENT|" + jobId + "|UNDEPLOYING");
		File file = new File(deployPath+"\\"+fileName); 
		file.delete();
		producer.sendMessage("test.status.queue", "DI_AGENT|" + jobId + "|UNDEPLOYED");

	}

}
