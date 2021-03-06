package com.github.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.github.admin.model.NexusArtifact;
import com.github.admin.model.NexusArtifactDao;
import com.github.admin.model.TalendArtifact;

@Service
public class NexusService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private JobService jobService;
	
	final String ROOT_URI = "http://localhost:8081/service/rest/v1/search?repository=talend-third-party";
	
	
	public List<NexusArtifactDao> getAllArtifacts() {
		ResponseEntity<NexusArtifact> response = restTemplate.getForEntity(ROOT_URI, NexusArtifact.class);
		List<NexusArtifactDao> nexusArtifactDaos = new ArrayList<NexusArtifactDao>();
		NexusArtifact nexusArtifact = response.getBody();
		nexusArtifact.getItems().stream().forEach(
				(item) -> {
					NexusArtifactDao artifactDao = new NexusArtifactDao(
							item.getId(), 
							item.getName().substring(0 , item.getName().indexOf('/')), 
							item.getName().substring(item.getName().indexOf("/") + 1).substring(0, item.getName().substring(item.getName().indexOf("/") + 1).indexOf("/")), 
							item.getAssets().get(0).getDownloadUrl(), 
							item.getName().substring(item.getName().lastIndexOf('.')+1,item.getName().length())
						);
					nexusArtifactDaos.add(artifactDao);
				}
		);
		
		List<NexusArtifactDao> nexusArtifacts = nexusArtifactDaos.stream()
				.sorted(Comparator.comparing(NexusArtifactDao::getJobName))
				.collect(Collectors.toList());
		return nexusArtifacts;

	}
	
	public List<NexusArtifactDao> getArtifacts() {
		List<NexusArtifactDao> nexusArtifacts = getAllArtifacts();
		List<TalendArtifact> artifacts = jobService.getAllDeployedJobs();
		
		
		return nexusArtifacts.stream().filter(item -> 
			!artifacts.stream()
				.map(x -> x.getJobName()+x.getJobVersion())
				.collect(Collectors.toList())
				.contains(item.getJobName()+item.getJobVersion())
		).collect(Collectors.toList());
	}

}
