package com.github.admin.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Item {
	
	private String id;
	private String repository;
	private String format;
	private String group;
	private String name;
	private String version;
	private List<Asset> assets;

}
