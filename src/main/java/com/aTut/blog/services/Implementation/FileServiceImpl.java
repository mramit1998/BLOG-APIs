package com.aTut.blog.services.Implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aTut.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {
	
	

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
	// - 1 file name extract
		String name = file.getOriginalFilename();
		
		//random file name generate 
		String randomId = UUID.randomUUID().toString();
		String filename = randomId.concat(name.substring(name.lastIndexOf(".")));
		
	// - 2 get full path 
	String filepath = path + File.separator + filename;
	
	// -3 Create folder if not present
	File f = new File(path);
	
	if(f.exists()==false) {
		f.mkdir();
	}
	
	//- 4 Copy file to directory
		Files.copy(file.getInputStream(), Paths.get(filepath));
	
		return filename;
	}

	@Override
	public InputStream getResource(String path, String filename) throws FileNotFoundException {
		// get full path 
		String filepath = path + File.separator + filename;
		
		// get input stream 
		InputStream is = new FileInputStream(filepath);
		
		return is;
	}

}
