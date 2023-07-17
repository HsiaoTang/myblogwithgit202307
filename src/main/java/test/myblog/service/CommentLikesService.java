package test.myblog.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.myblog.persist.dao.impl.CommentDAO;
import test.myblog.persist.dao.impl.CommentLikesDAO;

@Service
public class CommentLikesService {
	
	@Autowired
	private CommentDAO cd;
	
	@Autowired
	private CommentLikesDAO cld;
	
	public Integer userLikedCmt(Map<String, Integer> likedCmtInfo) {
		Integer cId = likedCmtInfo.get("c_id");
		cld.createByMidAndCid(likedCmtInfo.get("userId"), cId);
		Integer cl= cd.getCurrentCommentLikes(cId);
		cd.setNewCommentLikes(cl + 1, cId);
		return cd.getCurrentCommentLikes(cId);
	}
	
	public Integer userUnlikedCmt(Map<String, Integer> unlikedCmtInfo) {
		Integer cId = unlikedCmtInfo.get("c_id");
		cld.deleteByMidAndCid(unlikedCmtInfo.get("userId"), cId);
		Integer cl= cd.getCurrentCommentLikes(cId);
		cd.setNewCommentLikes(cl - 1, cId);
		return cd.getCurrentCommentLikes(cId);
	}
}
