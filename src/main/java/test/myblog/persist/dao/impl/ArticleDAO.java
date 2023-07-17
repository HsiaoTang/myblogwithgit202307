package test.myblog.persist.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import test.myblog.mapper.ArticleMapper;
import test.myblog.model.Article;
import test.myblog.persist.dao.DAO;
import test.myblog.persist.dao.DAOException;
import test.myblog.repository.ArticleRepository;

@Repository
public class ArticleDAO implements DAO<Article, Integer>{
	
//	@Autowired
//	ArticleRepository ar;
	@Autowired
	private ArticleMapper am;

	@Override
	public Article findOne(Integer aId) throws DAOException {
		// TODO Auto-generated method stub
		return am.findById(aId);
//		return ar.findById(aId).get();
	}

	@Override
	public List<Article> findAll() throws DAOException {
		// TODO Auto-generated method stub
		return am.findAll();
//		return ar.findAll();
	}
	
	@Transactional
	@Override
	public Article create(Article a) throws DAOException {
		am.save(a);
		return am.findLastArticle();
//		return ar.save(a);
	}

	@Override
	public void update(Article a) throws DAOException {
		// TODO Auto-generated method stub
		//throws an exception
		
	}

	@Override
	public void delete(Article a) throws DAOException {
		// TODO Auto-generated method stub
		
	}
	
	public void saveArticleWithId(Integer aId, String aContent, Date aDate, Integer aLikes, String aTitle, Integer aViews, Integer mId, Integer tId) {
		am.saveArticleWithId(aId, aContent, aDate, aLikes, aTitle, aViews, mId, tId);
//		ar.saveArticleWithId(aId, aContent, aDate, aLikes, aTitle, aViews, mId, tId);
	}
	
	public Article getCountOfArticleTable() {
		return am.getCountOfArticleTable();
//		return ar.getCountOfArticleTable();
	}

	public Integer getCurrentViews(Integer aId) {
		// TODO Auto-generated method stub
		return am.getCurrentViews(aId);
//		return ar.getCurrentViews(aId);
	}

	public void setNewViews(Integer newViews, Integer aId) {
		// TODO Auto-generated method stub
		am.setNewViews(newViews, aId);
//		ar.setNewViews(newViews, aId);
	}

	public List<Article> findArticleByMid(Integer mId) {
		// TODO Auto-generated method stub
		return am.findArticleByMid(mId);
//		return ar.findArticleByMid(mId);
	}

	public Integer getCurrentLikes(Integer aId) {
		// TODO Auto-generated method stub
		return am.getCurrentLikes(aId);
//		return ar.getCurrentLikes(aId);
	}

	public void setNewLikes(Integer newLikes, Integer aId) {
		// TODO Auto-generated method stub
		am.setNewLikes(newLikes, aId);
//		ar.setNewLikes(newLikes, aId);
	}

	public Integer countPostByMID(Integer mId) {
		// TODO Auto-generated method stub
		return am.countPostByMid(mId);
//		return ar.countPostByMid(mId);
	}

	public List<Integer[]> getALikesRanking() {
		// TODO Auto-generated method stub
		List<Integer[]> aLikesRanking = am.getALikesRanking().stream()
				.map(a -> {
					Integer[] aLikesArray = new Integer[3];
					aLikesArray[0] = (Integer) a.get("m_id");
					aLikesArray[1] = ((BigDecimal) a.get("sum_likes")).intValue();
					aLikesArray[2] = ((BigDecimal) a.get("sum_views")).intValue();
					return aLikesArray;
				}).collect(Collectors.toList());
		return aLikesRanking;
//		return am.getALikesRanking();
//		return ar.getALikesRanking();
	}

	public List<Article> findArticleListByTid(Integer tId) {
		// TODO Auto-generated method stub
		return am.findArticleListByTid(tId);
//		return ar.findArticleListByTid(tId);
	}

	public List<Integer[]> getAViewsRanking() {
		// TODO Auto-generated method stub
		List<Integer[]> aViewsRanking = am.getAViewsRanking().stream()
				.map(a -> {
					Integer[] aViewsArray = new Integer[3];
					aViewsArray[0] = (Integer) a.get("m_id");
					aViewsArray[1] = ((BigDecimal) a.get("sum_likes")).intValue();
					aViewsArray[2] = ((BigDecimal) a.get("sum_views")).intValue();
					return aViewsArray;
				}).collect(Collectors.toList());
		return aViewsRanking;
//		return am.getAViewsRanking();
//		return ar.getAViewsRanking();
	}
	
	
}
