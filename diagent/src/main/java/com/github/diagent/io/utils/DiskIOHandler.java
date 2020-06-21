package com.github.diagent.io.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.springframework.stereotype.Component;

@Component
public class DiskIOHandler {

	public Boolean dirExists(String path) {
		return new File(path).exists();
	}
	
	public Boolean fileExists(String path) {
		return new File(path).exists();
	}

	public Boolean mkDir(String path) {
		File file = new File(path);
		return file.mkdirs();
	}
	
	public Boolean deleteFolder(File file){
	      for (File subFile : file.listFiles()) {
	         if(subFile.isDirectory()) {
	            deleteFolder(subFile);
	         } else {
	            subFile.delete();
	         }
	      }
	      file.delete();
	      return true;
	   }

	public void downloadArtifact(String FILE_URL, String FILE_NAME) {
		try (BufferedInputStream in = new BufferedInputStream(new URL(FILE_URL).openStream());
				FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME)) {
			byte dataBuffer[] = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				fileOutputStream.write(dataBuffer, 0, bytesRead);
			}
		} catch (IOException e) {
			// handle exception
		}
	}
	
	public void unzip(String zipFilePath, String destDir) {
		try(ZipFile file = new ZipFile(zipFilePath))
        {
            FileSystem fileSystem = FileSystems.getDefault();
            //Get file entries
            Enumeration<? extends ZipEntry> entries = file.entries();
             
            //We will unzip files in this folder
            String uncompressedDirectory = destDir+"//";
            Files.createDirectory(fileSystem.getPath(uncompressedDirectory));
             
            //Iterate over entries
            while (entries.hasMoreElements()) 
            {
                ZipEntry entry = entries.nextElement();
                //If directory then create a new directory in uncompressed folder
                if (entry.isDirectory()) 
                {
                    System.out.println("Creating Directory:" + uncompressedDirectory + entry.getName());
                    Files.createDirectories(fileSystem.getPath(uncompressedDirectory + entry.getName()));
                } 
                //Else create the file
                else
                {
                    InputStream is = file.getInputStream(entry);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    String uncompressedFileName = uncompressedDirectory + entry.getName();
                    Path uncompressedFilePath = fileSystem.getPath(uncompressedFileName);
                    Files.createFile(uncompressedFilePath);
                    FileOutputStream fileOutput = new FileOutputStream(uncompressedFileName);
                    while (bis.available() > 0) 
                    {
                        fileOutput.write(bis.read());
                    }
                    fileOutput.close();
                    System.out.println("Written :" + entry.getName());
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        
    }

}
