package com.github.admin.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.admin.model.JobRunLog;
import com.github.admin.repository.JobRunLogRepository;

@Service
public class JobRunLogService {

	@Autowired
	private JobRunLogRepository repository;
	
	public JobRunLog saveLog(String jobId, String log) {
		JobRunLog jobLog = new JobRunLog();
		jobLog.setCreatedAt(new Date());
		jobLog.setTalendArtId(jobId);
		if(log.length()>997) {
			log=log.substring(0, 997);
		}
		jobLog.setLogText(log);
		return repository.save(jobLog);
	}
	
	public List<JobRunLog> getAllLogs(){
		return repository.findAll();
	}
	
	public JobRunLog getLog(Integer id) {
		return repository.findById(id).get();
	}
}
