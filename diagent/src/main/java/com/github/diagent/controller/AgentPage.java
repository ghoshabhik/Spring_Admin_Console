package com.github.diagent.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("talendagent")
@CrossOrigin
public class AgentPage {

	@GetMapping("deployed")
	public String isDeployed() {
		System.out.println("DI Agent Logging");
		return "DI Agent Deployed";
	}
}
