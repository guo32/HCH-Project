package cs.skuniv.HCH.service;

import java.util.ArrayList;
import java.util.List;

import cs.skuniv.HCH.dao.EtcDao;
import cs.skuniv.HCH.dto.Etc;
import cs.skuniv.HCH.request.EtcSearchDetailRequest;

public class EtcSearchDetailService {

	private EtcDao etcDao;
	
	public EtcSearchDetailService(EtcDao etcDao) {
		this.etcDao = etcDao;
	}
	
	public List<Etc> search(EtcSearchDetailRequest req) {
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
			if(req.getPrice().equals("150001")) sql += " price >= 150000";
			else sql += (" price >= " + String.valueOf((Integer.parseInt(req.getPrice()) - 20000)) + " and price < " + req.getPrice());
		}
		// 타입 - 2
		if(!req.getType().equals("null")) {
			if(!sql.equals("")) sql += " and";
			sql += " type = ?";
			searchData.add(req.getType());
		}
		// 평점 평균
		if(!sql.equals("")) sql += " and";
		sql += (" ratingsum / comment >= " + req.getRating());
		
		return etcDao.selectSearchDetail(sql, searchData);
	}
	
}
