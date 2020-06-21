package com.github.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.admin.model.TalendArtifact;
import com.github.admin.service.JobService;

@RestController
@RequestMapping("/talend")
@CrossOrigin
public class JobController {
	
	@Autowired
	private JobService jobservice;

	@GetMapping("/getAllDeployedJobs")
	public List<TalendArtifact> getAllDeployedJobs(){
		return jobservice.getAllDeployedJobs();
	}
	
	@GetMapping("/getAllDIJobs")
	public List<TalendArtifact> getAllDIJobs(){
		return jobservice.getAllDIJobs();
	}
	@GetMapping("/getAllESBJobs")
	public List<TalendArtifact> getAllESBJobs(){
		return jobservice.getAllESBJobs();
	}
	
	@GetMapping("/getJobById")
	public TalendArtifact getJobById(@RequestParam String id) {
		return jobservice.getArtifactById(id);
	}
	
	@PostMapping("addJob")
	public TalendArtifact addJob(@RequestBody TalendArtifact talendArtifact) {
		return jobservice.addJobWithArtifact(talendArtifact);
	}
	
	@PutMapping("updateJob")
	public TalendArtifact updateJob(@RequestBody TalendArtifact talendArtifact) {
		return jobservice.updateJobWithArtifact(talendArtifact);
	}
	
	@DeleteMapping("deleteJob")
	public String deleteJob(@RequestParam String id) {
		return jobservice.deleteJobById(id);
	}
	
	@GetMapping("deployJob")
	public String deployJob(@RequestParam String id) {
		return jobservice.deployJob(id);
	}
	
	@GetMapping("undeployJob")
	public String undeployJob(@RequestParam String id) {
		return jobservice.undeployJob(id);
	}
	
	@GetMapping("triggerJob")
	public String triggerJob(@RequestParam String id) {
		return jobservice.triggerJob(id);
	}
	
	@GetMapping("killJob")
	public String killJob(@RequestParam String id) {
		return jobservice.killJob(id);
	}

}
