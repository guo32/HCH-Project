package cs.skuniv.HCH.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cs.skuniv.HCH.dao.CommentDao;
import cs.skuniv.HCH.dao.EtcDao;
import cs.skuniv.HCH.dao.FavoriteDao;
import cs.skuniv.HCH.dto.Comment;
import cs.skuniv.HCH.dto.Etc;
import cs.skuniv.HCH.dto.Favorite;
import cs.skuniv.HCH.request.EtcRegisterRequest;

public class EtcRegisterService {
	
	private EtcDao etcDao;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private FavoriteDao favoriteDao;
	
	public EtcRegisterService(EtcDao etcDao) {
		this.etcDao = etcDao;
	}
	
	/* 제품(가전) 등록 */
	public int regist(EtcRegisterRequest req) throws Exception {
		Etc newEtc = new Etc(
				req.getCategory(),
				req.getName(),
				req.getBrand(),
				Integer.parseInt(req.getPrice()),
				req.getType(),
				Double.parseDouble(req.getRating()),
				Double.parseDouble(req.getRating()),
				req.getReview(),
				LocalDateTime.now(),
				req.getRegistrant(),
				req.getFilename());
		etcDao.insert(newEtc);
		
		return newEtc.getId();
	}
	
	/* 제품(가전) 수정 */
	public void edit(EtcRegisterRequest regReq, int id) throws Exception {
		Etc etc = etcDao.selectById(id);
		if(etc == null) {
			throw new Exception("NonexistentEtcException");
		}
		etc.setName(regReq.getName());
		etc.setBrand(regReq.getBrand());
		etc.setPrice(Integer.parseInt(regReq.getPrice()));
		etc.setType(regReq.getType());
		etc.setReview(regReq.getReview());
		etc.setFilename(regReq.getFilename());
		
		etcDao.update(etc);
	}
	
	/* 제품(가전) 삭제 */
	public void remove(int id) throws Exception {
		Etc etc = etcDao.selectById(id);
		if(etc == null) {
			throw new Exception("NonexistentEtcException");
		} else {
			// 댓글 삭제
			List<Comment> commentList = commentDao.selectByPost(id, etc.getCategory());
			if(!commentList.isEmpty()) { for(Comment comment : commentList) commentDao.delete(comment); }
			// 관심 삭제
			List<Favorite> favoriteList = favoriteDao.selectPosting(id, etc.getCategory());
			if(!favoriteList.isEmpty()) { for(Favorite favorite : favoriteList) favoriteDao.delete(favorite); }
			
			etcDao.delete(etc);
		}		
	}

}
