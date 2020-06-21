package com.github.esbagent.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("talendagent")
public class AgentPage {

	@GetMapping("deployed")
	public String isDeployed() {
		System.out.println("ESB Agent Logging");
		return "ESB Agent Deployed";
	}
}
