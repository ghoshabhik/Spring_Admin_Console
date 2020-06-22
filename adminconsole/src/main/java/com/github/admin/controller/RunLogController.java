package com.github.admin.controller;

import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.admin.model.JobRunLog;
import com.github.admin.service.JobRunLogService;

@RestController
@RequestMapping("/talend")
@CrossOrigin
public class RunLogController {
	
	@Autowired
	private JobRunLogService logService;
	
	@GetMapping("/getAllRunLog")
	public List<JobRunLog> getAllRunLog(){
		List<JobRunLog> logData = logService.getAllLogs();
		/*
		 * List<JobRunLog> logDataFormattedDate = logData.stream() .map(e ->
		 * e.setCreatedAt(e.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).
		 * toLocalDate())) .collect(Collectors.toList());
		 */
		return logData
				.stream()
				.sorted(Comparator.comparing(JobRunLog::getCreatedAt))
				.collect(Collectors.toList());
		 //return logService.getAllLogs();
	}
	
	@GetMapping("/getLog")
	public JobRunLog getLog(@RequestParam Integer id){
		 return logService.getLog(id);
	}

}
