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
		
		if(!req.getNation().equals("null")) {
			sql += " nation = " + req.getNation();
		}
		// 제조사 - 1
		if(req.getManufacturer() != null) {
			if(!sql.equals("")) sql += " and";
			sql += " manufacturer like ?";
			searchData.add("%" + req.getManufacturer() + "%");
		}
		// 가격
		if(!req.getPrice().equals("null")) {
			if(!sql.equals("")) sql += " and";
			if(req.getPrice().equals("25001")) sql += " price >= 25000";
			else sql += (" price >= " + String.valueOf((Integer.parseInt(req.getPrice()) - 5000)) + " and price < " + req.getPrice());
			// else sql += (" price < " + req.getPrice());
		}
		// 로스팅 정도 - 2
		if(!req.getRoastlevel().equals("null")) {
			if(!sql.equals("")) sql += " and";
			sql += " roastlevel = ?";
			searchData.add(req.getRoastlevel());
		}
		// 맛(노트) - 3
		if(!req.getTaste().equals("-1")) {
			if(!sql.equals("")) sql += " and";
			/*
			sql += " taste like ?";
			if(!req.getTaste().equals("other")) searchData.add("%" + req.getTaste() + "%");
			else if(req.getTasteOther() != null) searchData.add("%" + req.getTasteOther() + "%");
			*/
			sql += " taste = " + req.getTaste();
		}
		// 용량
		if(!req.getVolume().equals("null")) {
			if(!sql.equals("")) sql += " and";
			if(req.getVolume().equals("1001")) sql += " volume >= 1000";
			else sql += (" volume >= " + String.valueOf((Integer.parseInt(req.getVolume()) - 200)) + " and volume < " + req.getVolume());
			// else sql += (" volume < " + req.getVolume());
		}
		// 평점 평균
		if(!sql.equals("")) sql += " and";
		sql += (" ratingsum / comment >= " + req.getRating());
				
		return coffeeDao.selectSearchDetail(sql, searchData);
	}

}
