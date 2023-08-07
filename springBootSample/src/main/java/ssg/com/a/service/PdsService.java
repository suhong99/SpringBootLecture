package ssg.com.a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.a.dao.PdsDao;
import ssg.com.a.dto.PdsDto;

@Service
@Transactional
public class PdsService {
	
	@Autowired
	PdsDao dao;
	public List<PdsDto> pdslist() {
		return dao.pdslist();
	}
	
	public boolean uploadPds(PdsDto dto) {
		return dao.uploadPds(dto)>0;
	}
	
	public PdsDto getPds(int seq) {
		return dao.getPds(seq);
	}
	
	public boolean updateWithoutPds(PdsDto dto) {
		return dao.updateWithoutPds(dto)>0;
	}
	
	public boolean updatePds(PdsDto dto) {
		return dao.updatePds(dto)>0;
	}
}
