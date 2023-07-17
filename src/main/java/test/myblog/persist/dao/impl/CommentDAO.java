package test.myblog.persist.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import test.myblog.mapper.CommentMapper;
import test.myblog.model.Comment;
import test.myblog.persist.dao.DAO;
import test.myblog.persist.dao.DAOException;
import test.myblog.repository.CommentRepository;

@Repository
public class CommentDAO implements DAO<Comment, Integer>{
	
//	@Autowired
//	CommentRepository cr;
	@Autowired
	private CommentMapper cm;
	

	@Override
	public Comment findOne(Integer cId) throws DAOException {
		// TODO Auto-generated method stub
		return cm.findById(cId);
//		return cr.findById(cId).get();
	}

	@Override
	public List<Comment> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return cm.findAll();
//		return cr.findAll();
	}

	@Override
	public Comment create(Comment c) throws DAOException {
		// TODO Auto-generated method stub
		return cm.findById(cm.save(c));
//		return cr.save(c);
	}

	@Override
	public void update(Comment t) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Comment t) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	public List<Comment> getCommentListByAid(Integer aId) {
		// TODO Auto-generated method stub
		return cm.getCommentListByAid(aId);
//		return cr.getCommentListByAid(Aid);
	}

	public Comment getCountOfCommentTable() {
		// TODO Auto-generated method stub
		return cm.getCountOfCommentTable();
//		return cr.getCountOfCommentTable();
	}

	public Integer getCurrentCommentLikes(Integer cId) {
		// TODO Auto-generated method stub
		return cm.getCurrentCommentLikes(cId);
//		return cr.getCurrentCommentLikes(cId);
	}

	public void setNewCommentLikes(Integer newCLikes, Integer cId) {
		// TODO Auto-generated method stub
		cm.setNewCommentLikes(newCLikes, cId);
//		cr.setNewCommentLikes(newCLikes, cId);
	}
	
	public Integer getCommentCountByAid(Integer aId) {
		return cm.getCommentCountByAid(aId);
//		return cr.getCommentCountByAid(aId);
	}
	
	
	

}
