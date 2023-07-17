package test.myblog.persist.dao.impl;

import test.myblog.persist.dao.DAO;
import test.myblog.persist.dao.DAOException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import test.myblog.batch.repository.TriggerRepository;
import test.myblog.batch.model.Trigger;

@Repository
public class TriggerDAO implements DAO<Trigger, Integer>{
	
	@Autowired
	private TriggerRepository trr;

	@Override
	public Trigger findOne(Integer trId) throws DAOException {
		// TODO Auto-generated method stub
		return trr.findById(trId).get();
	}

	@Override
	public List<Trigger> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return trr.findAll();
	}

	@Override
	public Trigger create(Trigger tr) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Trigger tr) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Trigger tr) throws DAOException {
		// TODO Auto-generated method stub
		
	}

}
