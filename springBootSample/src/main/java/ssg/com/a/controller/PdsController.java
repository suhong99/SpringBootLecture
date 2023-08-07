package ssg.com.a.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;


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
import ssg.com.a.dto.PdsDto;
import ssg.com.a.service.PdsService;
import ssg.com.a.util.PdsUtil;

@RestController
public class PdsController {
	
	@Autowired
	PdsService service;
	
	@Autowired
	ServletContext servletContext;  // 다운로드 관련
	
	@GetMapping("pdslist")
	public List<PdsDto> pdslist(){
		System.out.println("PdsController pdslist()" + new Date());
		
		return service.pdslist();
	}
	
	@PostMapping("fileupload")
	public String fileupload(PdsDto dto,@RequestParam("uploadFile")MultipartFile uploadFile, 
								HttpServletRequest request)throws Exception {
		System.out.println("HelloController fileupload " + new Date());
		System.out.println(dto.toString());
		//경로
		String path  = request.getServletContext().getRealPath("/upload");
		// String path = "d:\tmp";
		String filename = uploadFile.getOriginalFilename();
		System.out.println("업로드 과정 1");

		// db에 저장을 위해서 
		dto.setFilename(filename);
		// 파일명을 변경 data.txt -> 323423423.txt 
		String newfilename = PdsUtil.getNewfileName(filename);		
		System.out.println("newfilename:" + newfilename);
		String filepath = path +"/" + newfilename;
		
		
		// db에 저장을 위해서
		dto.setNewfilename(newfilename);
		
		File f = new File(filepath);
		PdsDto pds = new PdsDto(dto.getId(),
								dto.getTitle(),
								dto.getContent(),
								filename,
								newfilename);
		
		try {
			BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(f));
			os.write(uploadFile.getBytes());
			System.out.println("업로드 과정 3");
			System.out.println(dto.toString());

			boolean isS = service.uploadPds(pds);
			
			if(isS) {
				System.out.println("파일 업로드 성공!");
			}else {
				System.out.println("파일 업로드 실패");
			}
			os.close();
		} catch (Exception e) {
			return "file upload fail";
		}
		return "file upload success";

	}
	// 상세 정보
	@GetMapping("pdsdetail")
	public PdsDto pdsdetail(int seq) {
		System.out.println("PdsController pdsdetail() " + new Date());
		
		PdsDto pds = service.getPds(seq);
		if(pds != null) {		
			return pds;
		}		
		return null;

	}
			
	// 다운로드
	
	@GetMapping("filedownload")
	public ResponseEntity<InputStreamResource> filedownload(int seq, HttpServletRequest request)throws Exception{
		System.out.println("HelloController filedownload " + new Date());
		//경로
		String path  = request.getServletContext().getRealPath("/upload");
		//String path ="d:\tmp";
		
		PdsDto pds = service.getPds(seq);
		//파일의 타입을 조사
		MediaType mediaType =MediaTypeUtiles.getMediaTypeForFileName(servletContext, pds.getFilename());
		System.out.println("newfilename :"+pds.getFilename());
		System.out.println("mediaType :"+mediaType);
		
		// File file = new File(path+"/"+filename); // "/"는 os따라 달라짐
		 File file = new File(path+File.separator+pds.getNewfilename());
		 System.out.println(file);
		 InputStreamResource is = new InputStreamResource(new FileInputStream(file));
		// 한글파일의 경우
		 String filename=URLEncoder.encode(pds.getFilename(),"utf-8");
		 //db download count를 증가
		 return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+ filename) // 원본 파일명
				 .contentType(mediaType)
				 .contentLength(file.length()).body(is);
	}
	
	@PostMapping("fileupdate")
	public String fileupdate(PdsDto dto,@RequestParam("uploadFile")MultipartFile uploadFile, 
								HttpServletRequest request)throws Exception {
		System.out.println("HelloController fileupdate " + new Date());
		System.out.println(dto.toString());
		//경로
		String path  = request.getServletContext().getRealPath("/upload");
		// String path = "d:\tmp";
		String filename = uploadFile.getOriginalFilename();
		// db에 저장을 위해서 dto.setFilename(filename); // 파일명을 변경 data.txt ->323423423.txt 
		  String newfilename = PdsUtil.getNewfileName(filename);
		  System.out.println("newfilename:" + newfilename); 
		  
	  PdsDto pds = new PdsDto(dto.getSeq(),
				dto.getTitle(),
				dto.getContent(),
				filename,
				newfilename);  
		  
		if(filename!=null && filename!="") {
			  String filepath = path +"/" + newfilename;
			  System.out.println(filepath);
			  
			  //기존의 파일을 삭제
			  PdsDto pdsOriginal = service.getPds(dto.getSeq());
			  File df = new File(path+"/"+ pdsOriginal.getNewfilename());
			  df.delete();
			  
			  
			  File f = new File(filepath); 
			  
			  try {
					BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(f));
					os.write(uploadFile.getBytes());
					System.out.println(pds.toString());
					os.close();
					boolean isS = service.updatePds(pds);
					
					if(isS) {
						System.out.println("파일 업데이트 성공!");
					}else {
						System.out.println("파일 업데이트 실패");
						return "file upload fail";
						
					} 
					
				} catch (Exception e) {
					return "file upload fail";
				}
		}else {
			boolean isS = service.updateWithoutPds(pds);
			
			if(isS) {
				System.out.println("파일 업데이트 성공!");
			}else {
				System.out.println("파일 업데이트 실패");
				return "file upload fail";
				
			} 
		}
			
				 		    		 
		return "file upload success";

	}
	
	
	
}
