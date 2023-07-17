package test.myblog.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.myblog.model.Member;
import test.myblog.persist.dao.DAOException;
import test.myblog.persist.dao.impl.FollowerDAO;
import test.myblog.persist.dao.impl.MemberDAO;

@Service
public class FollowerService {
	
	@Autowired
	private FollowerDAO fd;
	
	@Autowired
	private MemberDAO md;
	
	
	public List<Map<String, Object>>getFollowedListByMid(Integer mId) {
		List<Map<String, Object>> followedList= fd.findFollowedListByMid(mId).stream()
				.map(m_id -> {
					Member m = null;
					try {
						m = md.findOne(m_id);
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Map<String, Object> followedMap = new HashMap<>();
					followedMap.put("m_id", m.getM_id());
					followedMap.put("m_username", m.getM_username());
					return followedMap;
				})
				.collect(Collectors.toList());
		return followedList;
		
	}
	
	public List<Map<String, Object>>getFollowingListByMid(Integer mId) {
		List<Map<String, Object>> followingList= fd.findFollowingListByMid(mId).stream()
				.map(m_id -> {
					Member m = null;
					try {
						m = md.findOne(m_id);
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Map<String, Object> followingMap = new HashMap<>();
					followingMap.put("m_id", m.getM_id());
					followingMap.put("m_username", m.getM_username());
					return followingMap;
				})
				.collect(Collectors.toList());
		return followingList;
	}
	
	public Boolean checkIfUserFollowingByMid(Map<String, Integer> checkingMap) {
		return (fd.checkIfUserIsFollower(checkingMap.get("userId"), checkingMap.get("m_id")) > 0);
	}
	
	public Integer userFollowMember(Map<String, Integer> followingMap) {
		Integer userId = followingMap.get("userId");
		Integer m_id = followingMap.get("m_id");
		fd.createByMidAndFollowerid(m_id, userId);
		return fd.countFollowed(m_id);
	}
	
	public Integer userUnfollowMember(Map<String, Integer> followingMap) {
		Integer userId = followingMap.get("userId");
		Integer m_id = followingMap.get("m_id");
		fd.deleteByMidAndFollowerid(m_id, userId);
		return fd.countFollowed(m_id);
	}
}
