package test.myblog.persist.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import test.myblog.mapper.DraftMapper;
import test.myblog.model.Draft;
import test.myblog.persist.dao.DAO;
import test.myblog.persist.dao.DAOException;

@Repository
public class DraftDAO implements DAO<Draft, Integer>{
	
	@Autowired
	private DraftMapper dm;
	
	@Override
	public Draft findOne(Integer p) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Draft> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional
	@Override
	public Draft create(Draft d) throws DAOException {
		// TODO Auto-generated method stub
		dm.save(d);
		return dm.findLastDraft();
	}

	@Override
	public void update(Draft d) throws DAOException {
		// TODO Auto-generated method stub
		dm.updateByDidAndMid(d);
	}

	@Override
	public void delete(Draft d) throws DAOException {
		// TODO Auto-generated method stub
		
	}
	
	public List<Draft> findDraftListByMid(Integer mId) throws DAOException {
		// TODO Auto-generated method stub
		return dm.findByMid(mId);
	}
	
	public void deleteByDidAndMid(Integer dId, Integer mId) throws DAOException {
		dm.deleteByDidAndMid(dId, mId);
	}

}
