package com.github.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.admin.service.NexusService;
import com.github.admin.model.NexusArtifact;
import com.github.admin.model.NexusArtifactDao;

@RestController
@RequestMapping("/talend")
@CrossOrigin
public class SchedulePage {
	
	@Autowired
	private NexusService nexusService;
	
	@GetMapping("/getArtifacts")
	public List<NexusArtifactDao> getAllTask(){
		List<NexusArtifactDao> artifacts = nexusService.getAllArtifacts();
		 return artifacts;
	}
	
	@GetMapping("/getAddedArtifacts")
	public List<NexusArtifactDao> getAddedArtifacts(){
		List<NexusArtifactDao> artifacts = nexusService.getArtifacts();
		 return artifacts;
	}

	@GetMapping("/deployed")
	public String isDeployed() {
		return "Application Deployed Successfully";
	}
}
