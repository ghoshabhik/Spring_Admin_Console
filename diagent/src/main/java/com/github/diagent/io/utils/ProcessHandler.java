package com.github.diagent.io.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ProcessHandler {

	public String triggerJob(String workDir, String cmd) {
		// .\demo_di_job_run.bat
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.directory(new File(workDir));
		processBuilder.command(cmd);

		String output="Console Output for Job Run: "+cmd+"Timestamp: "+new Date()+" : \n";
		try {
			Process process = processBuilder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				output = output+line+"\n";
			}
			int exitCode = process.waitFor();
			System.out.println("\nExited with error code : " + exitCode);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return output;
	}

}
