package cs.skuniv.HCH.service;

import java.time.LocalDateTime;

import cs.skuniv.HCH.dao.CommentDao;
import cs.skuniv.HCH.dto.Comment;
import cs.skuniv.HCH.request.CommentRegisterRequest;

public class CommentRegisterService {
	
	private CommentDao commentDao;
	
	public CommentRegisterService(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	
	/* 댓글 등록 */
	public void regist(CommentRegisterRequest req) throws Exception {
		Comment newComment = new Comment(req.getRegistrant(),
				Integer.parseInt(req.getPosting()),
				req.getCategory(),
				req.getContent(),
				Double.parseDouble(req.getRating()),
				LocalDateTime.now());
		commentDao.insert(newComment);
	}

}
