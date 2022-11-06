package cs.skuniv.HCH.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cs.skuniv.HCH.dao.CommentDao;
import cs.skuniv.HCH.dao.FavoriteDao;
import cs.skuniv.HCH.dao.MachineDao;
import cs.skuniv.HCH.dto.Comment;
import cs.skuniv.HCH.dto.Favorite;
import cs.skuniv.HCH.dto.Machine;
import cs.skuniv.HCH.request.MachineRegisterRequest;

public class MachineRegisterService {
	
	private MachineDao machineDao;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private FavoriteDao favoriteDao;
	
	public MachineRegisterService(MachineDao machineDao) {
		this.machineDao = machineDao;
	}
	
	/* 제품(가전) 등록 */
	public int regist(MachineRegisterRequest req) throws Exception {
		Machine newMachine = new Machine(
				req.getCategory(),
				req.getName(),
				req.getBrand(),
				Integer.parseInt(req.getPrice()),
				req.getType(),
				req.getColor(),
				Double.parseDouble(req.getRating()),
				Double.parseDouble(req.getRating()),
				req.getReview(),
				LocalDateTime.now(),
				req.getRegistrant(),
				req.getFilename());
		machineDao.insert(newMachine);
		
		return newMachine.getId();
	}
	
	/* 제품(가전) 수정 */
	public void edit(MachineRegisterRequest regReq, int id) throws Exception {
		Machine machine = machineDao.selectById(id);
		if(machine == null) {
			throw new Exception("NonexistentMachineException");
		}
		machine.setName(regReq.getName());
		machine.setBrand(regReq.getBrand());
		machine.setPrice(Integer.parseInt(regReq.getPrice()));
		machine.setType(regReq.getType());
		machine.setColor(regReq.getColor());
		machine.setReview(regReq.getReview());
		machine.setFilename(regReq.getFilename());
		
		machineDao.update(machine);
	}
	
	/* 제품(가전) 삭제 */
	public void remove(int id) throws Exception {
		Machine machine = machineDao.selectById(id);
		if(machine == null) {
			throw new Exception("NonexistentMachineException");
		} else {
			// 댓글 삭제
			List<Comment> commentList = commentDao.selectByPost(id, machine.getCategory());
			if(!commentList.isEmpty()) { for(Comment comment : commentList) commentDao.delete(comment); }
			// 관심 삭제
			List<Favorite> favoriteList = favoriteDao.selectPosting(id, machine.getCategory());
			if(!favoriteList.isEmpty()) { for(Favorite favorite : favoriteList) favoriteDao.delete(favorite); }
			
			machineDao.delete(machine);
		}		
	}

}
