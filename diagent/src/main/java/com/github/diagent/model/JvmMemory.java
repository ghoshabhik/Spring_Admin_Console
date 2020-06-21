package com.github.diagent.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JvmMemory {

	private String totalMemory;
	private String freeMemory;
	private String usedMemory;
	private String maxMemory;
}
