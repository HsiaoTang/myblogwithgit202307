package test.myblog.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import test.myblog.model.Member;
import test.myblog.persist.dao.DAOException;
import test.myblog.persist.dao.impl.ArticleDAO;
import test.myblog.persist.dao.impl.FollowerDAO;
import test.myblog.persist.dao.impl.MemberDAO;

@Service
public class MemberService {
	
	@Autowired
	private MemberDAO md;
	
	@Autowired
	private ArticleDAO ad;
	
	@Autowired
	private FollowerDAO fd;
	
	@Autowired
    private ServletContext servletContext;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Member saveMemberProfileImg(Member m) throws IOException {
		byte[] imageBytes = Base64.getDecoder().decode(m.getM_pic());
		String fileName = UUID.randomUUID().toString() + ".jpg";
		//getRealPath 可能是null
		String staticPath = servletContext.getResource("/member/profileImgs").getPath();
	    String imagePath = staticPath + File.separator + fileName;
	    String imageURL = "http://localhost:8080/member/profileImgs" + File.separator + fileName;
	    BufferedOutputStream stream = new BufferedOutputStream(
				new FileOutputStream(new File(imagePath)));
		stream.write(imageBytes);
		stream.flush();
		stream.close();
		m.setM_pic(imageURL);
		return m;
	}
	
	public Member createMember(Member m) throws DAOException {
		m.setM_password(passwordEncoder.encode(m.getM_password()));
		return md.create(m);
	}
	
	public Member getMemberByMusername(String mUsername) {
		return md.findMemberByUsername(mUsername).get(0);
	}
	
	public List<Map<String, Object>> topLiked() {
		List<Map<String, Object>> topLikedByUsername = ad.getALikesRanking().stream()
											   .map(ary -> {
												   Member memberInList = null;
												try {
													memberInList = md.findOne(ary[0]);
												} catch (DAOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												   Map<String, Object> memberMap = new HashMap<>();
												   memberMap.put("m_id", memberInList.getM_id());
												   memberMap.put("m_username", memberInList.getM_username());
												   memberMap.put("m_pic", memberInList.getM_pic());
												   memberMap.put("m_likes", ary[1]);
												   memberMap.put("m_views",ary[2]);
;												   return memberMap;
											   }).collect(Collectors.toList());
		return topLikedByUsername;
	}
	
	public List<Map<String, Object>> topViewed(){
		List<Map<String, Object>> topViewedByUsername = ad.getAViewsRanking().stream()
												.map(ary -> {
												   Member memberInList = null;
												try {
													memberInList = md.findOne(ary[0]);
												} catch (DAOException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												   Map<String, Object> memberMap = new HashMap<>();
												   memberMap.put("m_id", memberInList.getM_id());
												   memberMap.put("m_username", memberInList.getM_username());
												   memberMap.put("m_pic", memberInList.getM_pic());
												   memberMap.put("m_likes", ary[1]);
												   memberMap.put("m_views",ary[2]);
;												   return memberMap;
											   }).collect(Collectors.toList());
		return topViewedByUsername;
	}
	
	public String checkIfUsernameUsed(String mUsername) throws DAOException{
		if(md.findMemberByUsername(mUsername).size() > 0) {
			return "*此帳號已被使用";
		}
		return "";
	}
	
	public Map<String, Object> getMemberInfoByMid(Integer mId) throws DAOException{
		Member m = md.findOne(mId);
		Map<String, Object> memberMap = new HashMap<>();
		memberMap.put("m_pic", m.getM_pic());
		memberMap.put("m_about", m.getM_about());
		memberMap.put("m_username", m.getM_username());
		memberMap.put("m_about", m.getM_about());
		return memberMap;
	}

}
