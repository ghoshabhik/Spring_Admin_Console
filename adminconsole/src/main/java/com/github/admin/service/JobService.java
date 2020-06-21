package com.github.admin.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.admin.repository.TalendArtifactRepository;
import com.github.admin.messaging.AdminProducer;
import com.github.admin.model.TalendArtifact;

@Service
public class JobService {
	
	@Autowired
	private TalendArtifactRepository talendRepo;
	
	@Autowired
	private AdminProducer adminProducer;
	
	public List<TalendArtifact> getAllDeployedJobs(){
		return talendRepo.findAll();
	}
	
	public List<TalendArtifact> getAllDIJobs(){
		List<TalendArtifact> data = getAllDeployedJobs();
		return data.stream().filter(d -> d.getJobType().equalsIgnoreCase("ZIP"))
							.collect(Collectors.toList());  
	}
	
	public List<TalendArtifact> getAllESBJobs(){
		List<TalendArtifact> data = getAllDeployedJobs();
		return data.stream().filter(d -> d.getJobType().equalsIgnoreCase("KAR"))
							.collect(Collectors.toList());  
	}
	
	public TalendArtifact getArtifactById(String artifactId) {
		Optional<TalendArtifact> artifact = talendRepo.findById(artifactId);
		if(artifact.isPresent())
			return artifact.get();
		else
			return new TalendArtifact();
	}
	
	public TalendArtifact addJobWithArtifact(TalendArtifact artifact) {
		String id = artifact.getJobName()+"_"+artifact.getJobVersion();
		artifact.setId(id.replaceAll("[^a-zA-Z0-9]", ""));
		artifact.setDeployStatus("Created");
		artifact.setCreatedTimeStamp(new Date());
		return talendRepo.save(artifact);
	}
	
	public String deleteJobById(String artifactId) {
		talendRepo.deleteById(artifactId);
		return "Deleted: "+artifactId;
	}
	
	public TalendArtifact updateJobWithArtifact(TalendArtifact artifact) {
		String id = artifact.getId();
		TalendArtifact existingArtifact = getArtifactById(id);
		if(artifact.getDeployStatus()==null) {
			artifact.setDeployStatus("Modified");
		}
		artifact.setCreatedTimeStamp(existingArtifact.getCreatedTimeStamp());
		System.out.println(existingArtifact.getCreatedTimeStamp());
		artifact.setModifiedTimeStamp(new Date());
		return talendRepo.save(artifact);
	}
	
	public String deployJob(String id) {
		TalendArtifact existingArtifact = getArtifactById(id);
		if(existingArtifact.getJobType().equalsIgnoreCase("kar")) {
			return deployEsbJob(id);
		}
		if(existingArtifact.getDeployStatus().equalsIgnoreCase("CREATED") || existingArtifact.getDeployStatus().equalsIgnoreCase("MODIFIED") || existingArtifact.getDeployStatus().equalsIgnoreCase("UNDEPLOYED") || existingArtifact.getDeployStatus().equalsIgnoreCase("DEPLOY_FAILED")) {
			String msg = "DEPLOY|"+ id +"|"+existingArtifact.getJobName() +"|"+existingArtifact.getJobVersion()+"|"+existingArtifact.getDownloadUrl();
			adminProducer.sendMessage("test.queue", msg);
			return "DEPLOY Message Queued";
		} else {
			return "Job Deploy Status is not valid for this operation: "+existingArtifact.getDeployStatus();
		}
		
	}
	
	public String undeployJob(String id) {
		TalendArtifact existingArtifact = getArtifactById(id);
		if(existingArtifact.getJobType().equalsIgnoreCase("kar")) {
			return undeployEsbJob(id);
		}
		if(existingArtifact.getDeployStatus().equalsIgnoreCase("DEPLOYED") || existingArtifact.getDeployStatus().equalsIgnoreCase("RAN")) {
			String msg = "UNDEPLOY|"+ id +"|"+existingArtifact.getJobName() +"|"+existingArtifact.getJobVersion();
			adminProducer.sendMessage("test.queue", msg);
			return "UNDEPLOY Message Queued";
		}else {
			return "Job Deploy Status is not valid for this operation: "+existingArtifact.getDeployStatus();
		}
	}
	
	public String triggerJob(String id) {
		TalendArtifact existingArtifact = getArtifactById(id);
		if(existingArtifact.getDeployStatus().equalsIgnoreCase("DEPLOYED") || existingArtifact.getDeployStatus().equalsIgnoreCase("RAN")) {
			String msg = "TRIGGER|"+ id +"|"+existingArtifact.getJobName() +"|"+existingArtifact.getJobVersion();
			adminProducer.sendMessage("test.queue", msg);
			return "TRIGGER Message Queued";
		}else {
			return "Job Deploy Status is not valid for this operation: "+existingArtifact.getDeployStatus();
		}
	}
	
	public String killJob(String id) {
		TalendArtifact existingArtifact = getArtifactById(id);
		if(existingArtifact.getDeployStatus().equalsIgnoreCase("DEPLOYED")) {
			String msg = "KILL|"+ id +"|"+existingArtifact.getJobName() +"|"+existingArtifact.getJobVersion();
			adminProducer.sendMessage("test.queue", msg);
			return "KILL Message Queued";
		}else {
			return "Job Deploy Status is not valid for this operation: "+existingArtifact.getDeployStatus();
		}
	}
	
	public String deployEsbJob(String id) {
		TalendArtifact existingArtifact = getArtifactById(id);
		if(existingArtifact.getDeployStatus().equalsIgnoreCase("CREATED") || existingArtifact.getDeployStatus().equalsIgnoreCase("MODIFIED") || existingArtifact.getDeployStatus().equalsIgnoreCase("UNDEPLOYED") || existingArtifact.getDeployStatus().equalsIgnoreCase("DEPLOY_FAILED")) {
			String msg = "DEPLOY|"+ id +"|"+existingArtifact.getJobName() +"|"+existingArtifact.getJobVersion()+"|"+existingArtifact.getDownloadUrl();
			adminProducer.sendMessage("esb.test.queue", msg);
			return "DEPLOY Message Queued";
		} else {
			return "Job Deploy Status is not valid for this operation: "+existingArtifact.getDeployStatus();
		}
		
	}
	
	public String undeployEsbJob(String id) {
		TalendArtifact existingArtifact = getArtifactById(id);
		if(existingArtifact.getDeployStatus().equalsIgnoreCase("DEPLOYED") || existingArtifact.getDeployStatus().equalsIgnoreCase("ACTIVE")) {
			String msg = "UNDEPLOY|"+ id +"|"+existingArtifact.getJobName() +"|"+existingArtifact.getJobVersion();
			adminProducer.sendMessage("esb.test.queue", msg);
			return "UNDEPLOY Message Queued";
		}else {
			return "Job Deploy Status is not valid for this operation: "+existingArtifact.getDeployStatus();
		}
	}

}
