package com.github.admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NexusArtifactDao {

	private String artifactId;
	private String jobName;
	private String jobVersion;
	private String downloadUrl;
	private String jobType;
}