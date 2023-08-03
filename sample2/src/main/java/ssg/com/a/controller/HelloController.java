package ssg.com.a.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import ssg.com.a.MediaTypeUtiles;
import ssg.com.a.dto.HumanDto;

@RestController
public class HelloController {
	//upload
	@PostMapping("fileupload")
	public String fileupload(HumanDto human,@RequestParam("uploadFile")MultipartFile uploadFile, 
								HttpServletRequest request) {
		System.out.println("HelloController fileupload " + new Date());
		System.out.println(human.toString());
		//경로
		String path  = request.getServletContext().getRealPath("/upload");
		// String path = "d:\tmp";
		String filename = uploadFile.getOriginalFilename();
		String filepath = path +"/" + filename;
		
		System.out.println(filepath);
		File f = new File(filepath);
		
		try {
			BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(f));
			os.write(uploadFile.getBytes());
			os.close();
		} catch (Exception e) {
			return "file upload fail";
		}
		return "file upload success";

	}
	//download
	@Autowired
	ServletContext servletContext;
	
	@GetMapping("fileDownload")
	public ResponseEntity<InputStreamResource> filedownload(String filename, HttpServletRequest request)throws Exception{
		System.out.println("HelloController filedownload " + new Date());
		//경로
		String path  = request.getServletContext().getRealPath("/upload");
		//String path ="d:\tmp";
		
		//파일의 타입을 조사
		MediaType mediaType =MediaTypeUtiles.getMediaTypeForFileName(servletContext, filename);
		System.out.println("filename :"+filename);
		System.out.println("mediaType :"+mediaType);
		
		// File file = new File(path+"/"+filename); // "/"는 os따라 달라짐
		 File file = new File(path+File.separator+filename);
		 
		 InputStreamResource is = new InputStreamResource(new FileInputStream(file));
		 
		 //db download count를 증가
		 return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+ file.getName()) // 원본 파일명
				 .contentType(mediaType)
				 .contentLength(file.length()).body(is);
	}
}
