package com.github.admin.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class JobRunLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer logId;
	private Date createdAt;
	private String talendArtId;
	private String logText;

}
