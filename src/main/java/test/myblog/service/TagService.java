package test.myblog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.myblog.model.Tag;
import test.myblog.persist.dao.DAOException;
import test.myblog.persist.dao.impl.TagDAO;

@Service
public class TagService {
	
	@Autowired
	private TagDAO td;
	
	public List<Tag> getAllTags() throws DAOException{
		return td.findAll();
	}
	
	public Optional<Tag> getTagById(Integer Tid){
		return td.findById(Tid);
	}

}
