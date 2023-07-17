package test.myblog.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import test.myblog.persist.dao.DAOException;
import test.myblog.service.ArticleLikesService;
import test.myblog.service.ArticleService;
import test.myblog.service.CommentLikesService;
import test.myblog.service.CommentService;

@RestController
@RequestMapping("/api/v1/post")
@CrossOrigin(origins="http://localhost:5173")
public class PostController {
	
	@Autowired
	private ArticleService as;
	
	@Autowired
	private ArticleLikesService als;
	
	@Autowired
	private CommentService cs;
	
	@Autowired
	private CommentLikesService cls;
	
	@PostMapping("/getArticleInfo")
	public ResponseEntity<Map<String, Object>> getArticleInfo(@RequestParam("a_id") Integer aId) throws DAOException{
		as.addOneView(aId);
		return ResponseEntity.ok(as.getArticleMap(aId));
	}
	
	@PostMapping("/checkIfLiked")
	public ResponseEntity<Boolean> checkIfLiked(@RequestBody Map<String, Integer> postViewing) throws DAOException{
		return ResponseEntity.ok(als.checkIfUserLiked(postViewing));
	}
	
	@PostMapping("/articleLiked")
	public ResponseEntity<Integer> articleLiked(@RequestBody Map<String, Integer> postViewing) throws DAOException{
		return ResponseEntity.ok(als.userLiked(postViewing));
	}
	
	@PostMapping("/articleUnliked")
	public ResponseEntity<Integer> articleUnliked(@RequestBody Map<String, Integer> postViewing) throws DAOException{
		return ResponseEntity.ok(als.userUnliked(postViewing));
	}
	
	@PostMapping("/leaveCmt")
	public ResponseEntity<Integer> leaveCmt(@RequestBody Map<String, Object> cmtInfo) throws DAOException{
		return ResponseEntity.ok(cs.createCmt(cmtInfo));
	}
	
	@PostMapping("/getCmtList")
	public ResponseEntity<List<Map<String, Object>>> getCmtList(@RequestBody Map<String, Integer> cmtListInfo) throws DAOException{
		return ResponseEntity.ok(cs.getCmtListByAid(cmtListInfo));
		
	}
	
	@PostMapping("/cmtLiked")
	public ResponseEntity<Integer> cmtLiked(@RequestBody Map<String, Integer> likedCmtInfo) throws DAOException{
		return ResponseEntity.ok(cls.userLikedCmt(likedCmtInfo));
		
	}
	
	@PostMapping("/cmtUnliked")
	public ResponseEntity<Integer> cmtUnliked(@RequestBody Map<String, Integer> unlikedCmtInfo) throws DAOException{
		System.out.println(unlikedCmtInfo);
		return ResponseEntity.ok(cls.userUnlikedCmt(unlikedCmtInfo));
	}
	
}
