package com.github.diagent.io.utils;

import org.springframework.stereotype.Component;

import com.github.diagent.model.JvmMemory;

@Component
public class JvmHealth {

	public JvmMemory memoryStats() {
		int mb = 1024 * 1024;
		// get Runtime instance
		Runtime instance = Runtime.getRuntime();
		System.out.println("***** Heap utilization statistics [MB] *****\n");
		// available memory
		System.out.println("Total Memory: " + instance.totalMemory() / mb);
		// free memory
		System.out.println("Free Memory: " + instance.freeMemory() / mb);
		// used memory
		System.out.println("Used Memory: " + (instance.totalMemory() - instance.freeMemory()) / mb);
		// Maximum available memory
		System.out.println("Max Memory: " + instance.maxMemory() / mb);
		JvmMemory jvmMem = new JvmMemory((instance.totalMemory() / mb) + "", (instance.freeMemory() / mb) + "",
				((instance.totalMemory() - instance.freeMemory()) / mb) + "", (instance.maxMemory() / mb) + "");
		return jvmMem;
	}
}
