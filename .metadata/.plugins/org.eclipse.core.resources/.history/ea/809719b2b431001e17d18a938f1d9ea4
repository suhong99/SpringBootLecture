package ssg.com.a.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ssg.com.a.dto.MemberDto;
import ssg.com.a.service.MemberService;

@RestController
public class HelloController {
	@Autowired
	MemberService service;
	
	@GetMapping("allmember")
	public List<MemberDto> allmember(){
		System.out.println("HelloController allmember() " + new Date());
		
		return service.allMember();
	}
}
