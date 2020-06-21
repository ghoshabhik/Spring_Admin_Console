package com.github.admin.controller;

import java.util.List;

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
		 return logService.getAllLogs();
	}
	
	@GetMapping("/getLog")
	public JobRunLog getLog(@RequestParam Integer id){
		 return logService.getLog(id);
	}

}
