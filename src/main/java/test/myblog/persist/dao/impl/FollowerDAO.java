package test.myblog.persist.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import test.myblog.mapper.FollowerMapper;
import test.myblog.model.Follower;
import test.myblog.persist.dao.DAO;
import test.myblog.persist.dao.DAOException;
import test.myblog.repository.FollowerRepository;

@Repository
public class FollowerDAO implements DAO<Follower, Integer> {
	
//	@Autowired
//	FollowerRepository fr;
	
	@Autowired
	private FollowerMapper fm;

	@Override
	public Follower findOne(Integer fId) throws DAOException {
		// TODO Auto-generated method stub
		return fm.findById(fId);
//		return fr.findById(fId).get();
	}

	@Override
	public List<Follower> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return fm.findAll();
//		return fr.findAll();
	}

	@Override
	public Follower create(Follower f) throws DAOException {
		// TODO Auto-generated method stub
		return fm.findById(fm.save(f));
//		return fr.save(f);
	}

	@Override
	public void update(Follower t) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Follower t) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	public void deleteByMidAndFollowerid(Integer Mid, Integer Followerid) {
		// TODO Auto-generated method stub
		fm.deleteByMidAndFollowerid(Mid, Followerid);
//		fr.deleteByMidAndFollowerid(Mid, Followerid);
	}

	public void createByMidAndFollowerid(Integer mId, Integer followerId) {
		// TODO Auto-generated method stub
		fm.createByMidAndFollowerid(mId, followerId);
//		fr.createByMidAndFollowerid(Mid, Followerid);
	}

	public Integer countFollowed(Integer mId) {
		// TODO Auto-generated method stub
		return fm.countFollowed(mId);
//		return fr.countFollowed(mId);
	}

	public Integer countFollowing(Integer mId) {
		// TODO Auto-generated method stub
		return fm.countFollowing(mId);
//		return fr.countFollowing(mId);
	}

	public List<Integer> findFollowingListByMid(Integer mId) {
		// TODO Auto-generated method stub
		return fm.findFollowingListByMid(mId);
//		return fr.findFollowingListByMid(mId);
	}

	public List<Integer> findFollowedListByMid(Integer mId) {
		// TODO Auto-generated method stub
		return fm.findFollowedListByMid(mId);
//		return fr.findFollowedListByMid(mId);
	}

	public Integer checkIfUserIsFollower(Integer userId, Integer mId) {
		// TODO Auto-generated method stub
		return fm.checkIfUserIsFollower(userId, mId);
//		return fr.checkIfUserIsFollower(userId, mId);
	}

	

}
