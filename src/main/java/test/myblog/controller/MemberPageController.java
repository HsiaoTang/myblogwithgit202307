package test.myblog.controller;

import java.util.Map;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.sf.jasperreports.engine.JRException;
import test.myblog.persist.dao.DAOException;
import test.myblog.service.ArticleService;
import test.myblog.service.FollowerService;
import test.myblog.service.MemberService;

@RestController
@RequestMapping("/api/v1/member")
@CrossOrigin(origins="http://localhost:5173")
public class MemberPageController {
	
	@Autowired
	private MemberService ms;
	
	@Autowired
	private FollowerService fs;
	
	@Autowired
	private ArticleService as;
	
	@PostMapping("/getMemberInfo")
	public ResponseEntity<Map<String, Object>> getMemberInfo(@RequestParam("m_id") Integer mId) throws DAOException{
		return ResponseEntity.ok(ms.getMemberInfoByMid(mId));	
	}
	
	@PostMapping("/getFollowingList")
	public ResponseEntity<List<Map<String, Object>>> getFollowingList(@RequestParam("m_id") Integer mId) throws DAOException{
		return ResponseEntity.ok(fs.getFollowingListByMid(mId));
	}
	
	@PostMapping("/getFollowedList")
	public ResponseEntity<List<Map<String, Object>>> getFollowedList(@RequestParam("m_id") Integer mId) throws DAOException{
		return ResponseEntity.ok(fs.getFollowedListByMid(mId));
	}
	
	@PostMapping("/checkIfUserFollowing")
	public Boolean checkIfUserFollowing(@RequestBody Map<String, Integer> checkingMap) {
		return fs.checkIfUserFollowingByMid(checkingMap);
	}
	
	@PostMapping("/follow")
	public Integer follow(@RequestBody Map<String, Integer> followingMap) {
		return fs.userFollowMember(followingMap);
	}
	
	@PostMapping("/unfollow")
	public Integer unfollow(@RequestBody Map<String, Integer> followingMap) {
		return fs.userUnfollowMember(followingMap);
	}
	
	@PostMapping("/getArticleList")
	public ResponseEntity<List<Map<String, Object>>> getArticleList(@RequestParam("m_id") Integer mId) {
		return ResponseEntity.ok(as.getArticleListByMid(mId));
	}
	
	@PostMapping("/importArticlesCsv")
	public ResponseEntity<Integer> importArticlesCsv(@RequestPart("csvFile") MultipartFile csvFile, @RequestParam("memberId") Integer mId) throws ParseException, IOException, DAOException {
		return ResponseEntity.ok(as.importArticlesFromCsv(csvFile, mId));
	}
	
	@PostMapping("/generateExpListCsv")
	public ResponseEntity<String> generateExpListCsv(@RequestBody List<Integer> aIdExpList) throws IOException {
		return ResponseEntity.ok(as.genExpListCsv(aIdExpList));
    }
	
	@PostMapping("/generateExpListPdf")
	public ResponseEntity<String> generateExpListPdf(@RequestBody List<Integer> aIdExpList) throws IOException, JRException {
		return ResponseEntity.ok(as.genExpListPdf(aIdExpList));
    }
}
	

