package cs.skuniv.HCH.service;

import java.util.ArrayList;
import java.util.List;

import cs.skuniv.HCH.dao.CoffeeDao;
import cs.skuniv.HCH.dto.Coffee;
import cs.skuniv.HCH.request.CoffeeSearchDetailRequest;

public class CoffeeSearchDetailService {
	
	private CoffeeDao coffeeDao;
	
	public CoffeeSearchDetailService(CoffeeDao coffeeDao) {
		this.coffeeDao = coffeeDao;
	}
	
	/* 상세 검색 */
	public List<Coffee> search(CoffeeSearchDetailRequest req) {
		String sql = "";
		List<String> searchData = new ArrayList<>();
		// 제조사 - 1
		if(req.getManufacturer() != null) {
			sql += " manufacturer like ?";
			searchData.add("%" + req.getManufacturer() + "%");
		}
		// 가격
		if(!req.getPrice().equals("null")) {
			if(!sql.equals("")) sql += " and";
			if(req.getPrice().equals("20001")) sql += " price >= 20000";
			else sql += (" price < " + req.getPrice());
		}
		// 로스팅 정도 - 2
		if(!req.getRoastlevel().equals("null")) {
			if(!sql.equals("")) sql += " and";
			sql += " roastlevel = ?";
			searchData.add(req.getRoastlevel());
		}
		// 맛(노트) - 3
		if(!req.getTaste().equals("null")) {
			if(!sql.equals("")) sql += " and";
			sql += " taste like ?";
			if(!req.getTaste().equals("other")) searchData.add("%" + req.getTaste() + "%");
			else if(req.getTasteOther() != null) searchData.add("%" + req.getTasteOther() + "%");
		}
		// 용량
		if(!req.getVolume().equals("null")) {
			if(!sql.equals("")) sql += " and";
			if(req.getVolume().equals("1001")) sql += " volue >= 1000";
			else sql += (" volume < " + req.getVolume());
		}
		// 평점 평균: 임시로 게시자 등록 평균 검사
		if(!sql.equals("")) sql += " and";
		sql += (" rating >= " + req.getRating());
		
		// System.out.println(sql);
		
		return coffeeDao.selectSearchDetail(sql, searchData);
	}

}
