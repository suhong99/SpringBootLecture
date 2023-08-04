package ssg.com.a.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.com.a.dao.BbsDao;
import ssg.com.a.dto.BbsComment;
import ssg.com.a.dto.BbsDto;
import ssg.com.a.dto.BbsParam;

@Service
@Transactional
public class BbsService {

	@Autowired
	BbsDao dao;
	
	public List<BbsDto> bbslist(BbsParam param) {
		return dao.bbslist(param);
	}
	
	public int getallbbs(BbsParam param) {
		return dao.getallbbs(param);
	}
	
	public int bbswrite(BbsDto dto) {
		return dao.bbswrite(dto);
	}
	
	public BbsDto bbsdetail(int seq) {
		return dao.bbsdetail(seq);
	}
	
	public int bbsupdate(BbsDto dto) {
		return dao.bbsupdate(dto);
	}
	
	public int bbsdelete(int seq) {
		return dao.bbsdelete(seq);
	}
	
	public int bbsanswer(BbsDto dto) {
		dao.BbsAnswerUpdate(dto);
		return dao.bbsanswer(dto);
	}
	
	public int commentWrite(BbsComment comment) {
		return dao.commentWrite(comment);
	}
	
	public List<BbsComment> commentList(int seq) {
		return dao.commentList(seq);
	}
}



