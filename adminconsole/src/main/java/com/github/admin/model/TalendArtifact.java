package com.github.admin.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TalendArtifact {

	@Id
	private String id;
	private String artifactId;
	private String jobName;
	private String jobVersion;
	private String downloadUrl;
	private String jobType;
	private String deployStatus;
	private String deployServer;
	private Date lastRunTimeStamp;
	private Date lastDeployedTimeStamp;
	private Date createdTimeStamp;
	private Date modifiedTimeStamp;
}
