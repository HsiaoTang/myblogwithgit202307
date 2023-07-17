package test.myblog.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.myblog.persist.dao.impl.ArticleDAO;
import test.myblog.persist.dao.impl.ArticleLikesDAO;

@Service
public class ArticleLikesService {
	
	@Autowired
	private ArticleLikesDAO ald;
	
	@Autowired
	private ArticleDAO ad;
	
	public Boolean checkIfUserLiked(Map<String, Integer> postViewing) {
		Integer isLiked = ald.checkIfMemberLikedArticle(postViewing.get("userId"), postViewing.get("a_id"));
		if(isLiked > 0) {
			return true;
		}
		return false;
	}
	
	public Integer userLiked(Map<String, Integer> postViewing) {
		Integer userId = postViewing.get("userId"); 
		Integer aId = postViewing.get("a_id");
		ad.setNewLikes(ad.getCurrentLikes(aId) + 1, aId);
		ald.createByMidAndAid(userId, aId);
		return ald.getLikeCountByAid(aId);
	}
	
	public Integer userUnliked(Map<String, Integer> postViewing) {
		Integer userId = postViewing.get("userId"); 
		Integer aId = postViewing.get("a_id");
		ad.setNewLikes(ad.getCurrentLikes(aId) - 1, aId);
		ald.deleteByMidAndAid(userId, aId);
		return ald.getLikeCountByAid(aId);
	}
	
}
