package cs.skuniv.HCH.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import cs.skuniv.HCH.dao.CoffeeDao;
import cs.skuniv.HCH.dao.FavoriteDao;
import cs.skuniv.HCH.dto.Coffee;
import cs.skuniv.HCH.dto.Favorite;
import cs.skuniv.HCH.dto.Member;

public class FavoriteService {
	
	private FavoriteDao favDao;
	@Autowired
	private CoffeeDao coffeeDao;
	
	public FavoriteService(FavoriteDao favDao) {
		this.favDao = favDao;
	}
	
	/* 관심 등록 */
	public void add(String user, int posting, String category) throws Exception {		
		Favorite newFavorite = new Favorite(user, posting, category);
		favDao.insert(newFavorite);
		
		if(category.equals("cb")) {
			Coffee coffee = coffeeDao.selectByNum(posting);
			int count = coffee.getFavorite();
			coffee.setFavorite(++count);
			coffeeDao.updateFavorite(coffee);
		}
	}
	
	/* 관심 해제 */
	public void release(String user, int posting, String category) {
		Favorite favorite = favDao.selectFavorite(user, posting, category);
		
		if(favorite != null) {
			favDao.delete(favorite);
			if(category.equals("cb")) {
				Coffee coffee = coffeeDao.selectByNum(posting);
				int count = coffee.getFavorite();
				coffee.setFavorite(--count);
				coffeeDao.updateFavorite(coffee);
			}
		}
	}
	
	/* 관심 여부 확인 */
	public boolean check(Member member, String category, int posting) {
		List<Favorite> favorites = favDao.selectUser(member.getId());
		
		for(Favorite fav:favorites) {
			if(fav.getCategory().equals(category)) {
				if(fav.getPosting() == posting) {
					return true;
				}
			}
		}
		return false;
	}
	
}
