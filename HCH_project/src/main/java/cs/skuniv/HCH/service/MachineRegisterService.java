package cs.skuniv.HCH.service;

import java.time.LocalDateTime;

import cs.skuniv.HCH.dao.MachineDao;
import cs.skuniv.HCH.dto.Machine;
import cs.skuniv.HCH.request.MachineRegisterRequest;

public class MachineRegisterService {
	
	private MachineDao machineDao;
	
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

}
