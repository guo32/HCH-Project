package cs.skuniv.HCH.service;

import java.util.ArrayList;
import java.util.List;

import cs.skuniv.HCH.dao.MachineDao;
import cs.skuniv.HCH.dto.Machine;
import cs.skuniv.HCH.request.MachineSearchDetailRequest;

public class MachineSearchDetailService {

	private MachineDao machineDao;
	
	public MachineSearchDetailService(MachineDao machineDao) {
		this.machineDao = machineDao;
	}
	
	public List<Machine> search(MachineSearchDetailRequest req) {
		String sql = "";
		List<String> searchData = new ArrayList<>();
		// 브랜드 - 1
		if(req.getBrand() != null) {
			sql += " brand like ?";
			searchData.add("%" + req.getBrand() + "%");
		}
		// 가격
		if(!req.getPrice().equals("null")) {
			if(!sql.equals("")) sql += " and";
			if(req.getPrice().equals("700001")) sql += " price >= 700000";
			else sql += (" price >= " + String.valueOf((Integer.parseInt(req.getPrice()) - 50000)) + " and price < " + req.getPrice());
		}
		// 타입 - 2
		if(!req.getType().equals("null")) {
			if(!sql.equals("")) sql += " and";
			sql += " type = ?";
			searchData.add(req.getType());
		}
		// 색상 - 3
		if(!req.getColor().equals("null")) {
			if(!sql.equals("")) sql += " and";
			sql += " color = ?";
			searchData.add(req.getColor());
		}
		// 평점 평균
		if(!sql.equals("")) sql += " and";
		sql += (" ratingsum / comment >= " + req.getRating());
		
		return machineDao.selectSearchDetail(sql, searchData);
	}
	
}
