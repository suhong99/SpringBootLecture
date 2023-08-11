package ssg.com.a.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ssg.com.a.dto.BbsComment;
import ssg.com.a.dto.BbsDto;
import ssg.com.a.dto.BbsParam;
import ssg.com.a.dto.MemberDto;
import ssg.com.a.service.BbsService;

@RestController
public class BbsController {

	@Autowired
	BbsService service;
	
	@GetMapping("bbslist")
	public Map<String,Object> bbslist(BbsParam param){
		System.out.println("BbsController bbslist " + new Date());
		//글목록
		List<BbsDto> list = service.bbslist(param);
		
		//글의 총수
		int count = service.getallbbs(param);
		// jqeury용 계산해서 넘겼음
//		int pageBbs = count/10;
//		if((count%10)>0) {
//			pageBbs= pageBbs+1;
//		}
		// 현재 페이지
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("bbslist", list);
		// jquery용
//		map.put("pageBbs", pageBbs);
//		map.put("pageNumber", param.getPageNumber());
		
		//react용
		map.put("cnt", count);
		return map;
	}
	
	@PostMapping("bbswrite")
	public String bbswrite(BbsDto dto) {
		System.out.println("BbsController bbswrite(BbsDto dto) " + new Date());
		
		int count = service.bbswrite(dto);
		if(count > 0) {
			return "YES";
		}
		return "NO";
	}
	
	@GetMapping("bbsdetail")
	public BbsDto bbsdetail(int seq) {
		System.out.println("BbsController bbsdetail() " + new Date());
		
		BbsDto dto = service.bbsdetail(seq);		
		
		return dto;
	}
	
	@PostMapping("bbsdelete")
	public String bbsdelete(int seq) {
		System.out.println("BbsController bbsdelete(int seq) " + new Date());
		
		int count = service.bbsdelete(seq);
		if(count > 0) {
			return "YES";
		}
		return "NO";
	}
	
	@PostMapping("bbsupdate")
	public String bbsupdate(BbsDto dto) {
		System.out.println("BbsController bbsupdate(BbsDto dto) " + new Date());
		
		int count = service.bbsupdate(dto);
		if(count > 0) {
			return "YES";
		}
		return "NO";
	}
	
	@PostMapping("bbsanswer")
	public String bbsanswer(BbsDto dto) {
		System.out.println("BbsController bbsanswer(BbsDto dto) " + new Date());
		
		int count = service.bbsanswer(dto);
		if(count > 0) {
			return "YES";
		}
		return "NO";
	}
	
	@PostMapping("commentWrite")
	public String commentWrite(BbsComment bbsComment) {
		System.out.println("BbsController commentWrite " + new Date());
		int count = service.commentWrite(bbsComment);
		if(count>0) {
			return "YES";
		}else {
			return "NO";
		}		
	}
	
	@GetMapping("commentList")
	public List<BbsComment> commentList(int seq){
		System.out.println("BbsController coomentList " + new Date());
		
		return service.commentList(seq);
	}
}




