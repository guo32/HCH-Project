package cs.skuniv.HCH.service;

import java.time.LocalDateTime;

import cs.skuniv.HCH.dao.CoffeeDao;
import cs.skuniv.HCH.dto.Coffee;
import cs.skuniv.HCH.request.CoffeeRegisterRequest;

public class CoffeeRegisterService {
	
	private CoffeeDao coffeeDao;
	
	public CoffeeRegisterService(CoffeeDao coffeeDao) {
		this.coffeeDao = coffeeDao;
	}
	
	/* 제품(커피) 등록 */
	public int regist(CoffeeRegisterRequest req) throws Exception {
		/* auto increment라 id가 겹치지는 않음 
		
		Coffee coffee = coffeeDao.selectById(req.getId());
		if(coffee != null) {
			throw new Exception("DuplicateIdException");
		}
		*/
		Coffee newCoffee = new Coffee(req.getCategory(), req.getName(), req.getManufacturer(),
				Integer.parseInt(req.getPrice()), req.getRoastlevel(), req.getTaste(),
				Integer.parseInt(req.getVolume()), Double.parseDouble(req.getRating()),
				Double.parseDouble(req.getRating()), req.getReview(), LocalDateTime.now(), 
				req.getRegistrant(), req.getFilename());
		coffeeDao.insert(newCoffee);
		
		return newCoffee.getNum();
	}
	
	/* 제품(커피) 수정 */
	public void edit(CoffeeRegisterRequest regReq, int num) throws Exception {
		Coffee coffee = coffeeDao.selectByNum(num);
		if(coffee == null) {
			throw new Exception("NonexistentCoffeeException");
		}
		coffee.setName(regReq.getName());
		coffee.setManufacturer(regReq.getManufacturer());
		coffee.setPrice(Integer.parseInt(regReq.getPrice()));
		coffee.setRoastlevel(regReq.getRoastlevel());
		coffee.setTaste(regReq.getTaste());
		coffee.setVolume(Integer.parseInt(regReq.getVolume()));
		coffee.setReview(regReq.getReview());
		coffee.setFilename(regReq.getFilename());
		
		coffeeDao.update(coffee);
	}
		
	/* 제품(커피) 삭제 */
	public void remove(int num) throws Exception {
		Coffee coffee = coffeeDao.selectByNum(num);
		if(coffee == null) {
			throw new Exception("NonexistentCoffeeException");
		}
		coffeeDao.delete(coffee);
	}

}
