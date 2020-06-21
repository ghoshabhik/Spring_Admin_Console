package com.github.admin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Asset {

	private String downloadUrl;
	private String path;
	private String id;
	private String repository;
	private String format;
	private Object checksum;
}
