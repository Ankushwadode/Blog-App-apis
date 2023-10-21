package com.blogapp.Services.Impl;

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

import com.blogapp.Services.FileService;

/*this code helps to upload any type of file so the constrains to upload a particular file type is not added
  for example images which are jpeg,jpg,png etc.*/

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadFile(String path, MultipartFile file) throws IOException {

		String fileName = file.getOriginalFilename();
		
		if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg") || fileName.toLowerCase().endsWith(".png")) {
			
			// random name generate
			String randomID = UUID.randomUUID().toString();
			String randomFileName = randomID.concat(fileName.substring(fileName.lastIndexOf(".")));

			String filePath = path + File.separator + randomFileName;

			File content = new File(path);
			if (!content.exists()) {
				content.mkdir();
			}

			Files.copy(file.getInputStream(), Paths.get(filePath));

			System.gc();
			
			return randomFileName;
			
		}
		return fileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {

		String fullPath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);

		return is;
	}

}
