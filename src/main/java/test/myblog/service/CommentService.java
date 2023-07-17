package test.myblog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.myblog.persist.dao.DAOException;
import test.myblog.persist.dao.impl.ArticleDAO;
import test.myblog.persist.dao.impl.CommentDAO;
import test.myblog.persist.dao.impl.CommentLikesDAO;
import test.myblog.persist.dao.impl.MemberDAO;
import test.myblog.model.Comment;

@Service
public class CommentService {
	
	@Autowired
	private CommentDAO cd;
	
	@Autowired
	private ArticleDAO ad;
	
	@Autowired
	private MemberDAO md;
	
	@Autowired
	private CommentLikesDAO cld;
	
	public Integer createCmt(Map<String, Object> cmtInfo) throws DAOException {
		Comment c = new Comment();
		Integer aId = (Integer)cmtInfo.get("a_id");
		c.setA(ad.findOne(aId));
		c.setM(md.findOne(Integer.parseInt((String)cmtInfo.get("userId"))));
		c.setC_content((String)(cmtInfo.get("cmtContent") + ""));
		c.setC_likes(0);
		c.setC_time(LocalDateTime.now());
		cd.create(c);
		return cd.getCommentCountByAid(aId);
	}
	
	public List<Map<String, Object>> getCmtListByAid(Map<String, Integer> cmtListInfo) throws DAOException {
		Integer userId = cmtListInfo.get("userId");
		List<Map<String, Object>> cmtList = cd.getCommentListByAid(cmtListInfo.get("a_id")).stream()
											.map(c -> {
												Integer cId = c.getC_id();
												Map<String, Object> cmtMap = new HashMap<>();
												cmtMap.put("c_id", cId);
												cmtMap.put("c_time", c.getC_time());
												cmtMap.put("c_likes", c.getC_likes());
												cmtMap.put("c_content", c.getC_content());
												cmtMap.put("m_id", c.getM().getM_id());
												cmtMap.put("m_username", c.getM().getM_username());
												cmtMap.put("m_pic", c.getM().getM_pic());
												cmtMap.put("userLiked", (cld.checkIfMemberLikedComment(userId, cId) > 0));
												return cmtMap;
											})
											.collect(Collectors.toList());
		return cmtList;
	}

}
