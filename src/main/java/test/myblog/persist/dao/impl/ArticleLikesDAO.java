package test.myblog.persist.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import test.myblog.mapper.ArticleLikesMapper;
import test.myblog.model.ArticleLikes;
import test.myblog.persist.dao.DAO;
import test.myblog.persist.dao.DAOException;
import test.myblog.repository.AritcleLikesRepository;

@Repository
public class ArticleLikesDAO implements DAO<ArticleLikes, Integer>{
	
//	@Autowired
//	AritcleLikesRepository alr;

	@Autowired
	private ArticleLikesMapper alm;
	
	@Override
	public ArticleLikes findOne(Integer alId) throws DAOException {
		// TODO Auto-generated method stub
		return alm.findById(alId);
//		return alr.findById(aLid).get();
	}

	@Override
	public List<ArticleLikes> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return alm.findAll();
//		return alr.findAll();
	}

	@Override
	public ArticleLikes create(ArticleLikes al) throws DAOException {
		// TODO Auto-generated method stub
		return alm.findById(alm.save(al));
//		return alm.save(al);
	}

	@Override
	public void update(ArticleLikes t) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ArticleLikes t) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	public Integer getLikeCountByAid(Integer aId) {
		// TODO Auto-generated method stub
		return alm.getLikeCountByAid(aId);
//		return alr.getLikeCountByAid(aId);
	}

	public void createByMidAndAid(Integer mId, Integer aId) {
		// TODO Auto-generated method stub
		alm.createByMidAndAid(mId, aId);
//		alr.createByMidAndAid(mId, aId);
	}

	public void deleteByMidAndAid(Integer mId, Integer aId) {
		// TODO Auto-generated method stub
		alm.deleteByMidAndAid(mId, aId);
//		alr.deleteByMidAndAid(mId, aId);
	}

	public Integer checkIfMemberLikedArticle(Integer mId, Integer aId) {
		// TODO Auto-generated method stub
		return alm.checkIfMemberLikedArticle(mId, aId);
//		return alr.checkIfMemberLikedArticle(mId, aId);
	}

	
	
}
