package test.myblog.mapper;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import test.myblog.model.Article;

public interface ArticleMapper {

	Article findById(@Param("a_id") Integer aId);
	List<Article> findAll();
	Article findLastArticle();
	Integer save(Article a);
	void saveArticleWithId(@Param("a_id") Integer aId, @Param("a_content") String aContent, @Param("a_date") Date aDate, @Param("a_likes") Integer aLikes, @Param("a_title") String aTitle, @Param("a_views") Integer aViews,
			@Param("m_id") Integer mId, @Param("t_id")Integer tId);
	Article getCountOfArticleTable();
	Integer getCurrentViews(@Param("a_id") Integer aId);
	void setNewViews(@Param("newViews") Integer newViews, @Param("aId") Integer aId);
	Integer getCurrentLikes(@Param("a_id") Integer aId);
	void setNewLikes(@Param("newLikes") Integer newLikes, @Param("aId") Integer aId);
	Integer countPostByMid(@Param("mId") Integer mId);
//	List<Integer[]> getALikesRanking();
	List<Map<String, Object>> getALikesRanking();
	List<Article> findArticleListByTid(@Param("tId") Integer tId);
//	List<Integer[]> getAViewsRanking();
	List<Map<String, Object>> getAViewsRanking();
	List<Article> findArticleByMid(@Param("mId") Integer mId);
}
