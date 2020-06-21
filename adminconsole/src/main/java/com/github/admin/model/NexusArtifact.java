package com.github.admin.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NexusArtifact {

	private List<Item> items;
	private String continuationToken;
}