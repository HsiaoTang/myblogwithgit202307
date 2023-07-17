package test.myblog.persist.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import test.myblog.mapper.CommentLikesMapper;
import test.myblog.model.CommentLikes;
import test.myblog.persist.dao.DAO;
import test.myblog.persist.dao.DAOException;
import test.myblog.repository.CommentLikesRepository;

@Repository
public class CommentLikesDAO implements DAO<CommentLikes, Integer> {
	
//	@Autowired
//	CommentLikesRepository clr;
	
	@Autowired
	private CommentLikesMapper clm;
	
	@Override
	public CommentLikes findOne(Integer clId) throws DAOException {
		// TODO Auto-generated method stub
		return clm.findById(clId);
//		return clr.findById(clId).get();
	}

	@Override
	public List<CommentLikes> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return clm.findAll();
//		return clr.findAll();
	}

	@Override
	public CommentLikes create(CommentLikes cl) throws DAOException {
		// TODO Auto-generated method stub
		return clm.findById(clm.save(cl));
//		return clr.save(cl);
	}

	@Override
	public void update(CommentLikes t) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(CommentLikes t) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	public Integer checkIfMemberLikedComment(Integer mId, Integer cId) {
		// TODO Auto-generated method stub
		return clm.checkIfMemberLikedComment(mId, cId);
//		return clr.checkIfMemberLikedComment(mId, cId);
	}

	public void createByMidAndCid(Integer mId, Integer cId) {
		// TODO Auto-generated method stub
		clm.createByMidAndCid(mId, cId);
//		clr.createByMidAndCid(Mid, Cid);
	}

	public void deleteByMidAndCid(Integer mId, Integer cId) {
		// TODO Auto-generated method stub
		clm.deleteByMidAndCid(mId, cId);
//		clr.deleteByMidAndCid(mId, cId);
	}

}
