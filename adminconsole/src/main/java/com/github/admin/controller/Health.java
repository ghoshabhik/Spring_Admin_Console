package com.github.admin.controller;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.admin.model.HealthData;
import com.github.admin.model.JvmMemory;
import com.github.admin.utils.JvmHealth;


@RestController
@RequestMapping("/talend")
@CrossOrigin
public class Health {

	@Autowired
	private JvmHealth jvmHealth;
	
	@GetMapping("/uptime")
	public String getUpTime() {
		RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
		long upTime = runtimeBean.getUptime();
		System.out.printf("Up Time = %d (ms)", upTime);
		return upTime+"";
	}
	@GetMapping("/memory")
	public JvmMemory getHealth() {
		return jvmHealth.memoryStats();
	}
	@GetMapping("/up")
	public String isUp() {
		return "OK";
	}
	@GetMapping("/health")
	public HealthData getAllHealth() {
		HealthData data = new HealthData(isUp(),getUpTime(),getHealth());
		return data;
	}
}
