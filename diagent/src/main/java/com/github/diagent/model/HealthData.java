package com.github.diagent.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HealthData {
	private String isUp;
	private String upTime;
	private JvmMemory memDetails;
}
