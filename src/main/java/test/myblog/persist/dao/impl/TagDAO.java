package test.myblog.persist.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import test.myblog.mapper.TagMapper;
import test.myblog.model.Tag;
import test.myblog.persist.dao.DAO;
import test.myblog.persist.dao.DAOException;
import test.myblog.repository.TagRepository;

@Repository
public class TagDAO implements DAO<Tag, Integer>{
	
	@Autowired
	private TagMapper tm;

	@Override
	public Tag findOne(Integer tId) throws DAOException {
		// TODO Auto-generated method stub
		return tm.findById(tId);
//		return tr.findById(tId).get();
	}

	@Override
	public List<Tag> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return tm.findAll();
	}

	@Override
	public Tag create(Tag t) throws DAOException {
		// TODO Auto-generated method stub
//		return tr.save(t);
		return null;
	}

	@Override
	public void update(Tag t) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Tag t) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	public Optional<Tag> findById(Integer TID) {
		// TODO Auto-generated method stub
//		return tr.findById(TID);
		return null;
	}

}
