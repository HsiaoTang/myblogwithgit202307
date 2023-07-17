package test.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import test.myblog.model.Article;


public interface ArticleRepository extends JpaRepository<Article, Integer>{
	@Query(value="SELECT * FROM article ORDER BY a_id DESC LIMIT 1" ,nativeQuery=true)
	Article getCountOfArticleTable();
	
	@Query(value="SELECT a_views FROM article WHERE a_id = :aId", nativeQuery=true)
	Integer getCurrentViews(Integer aId);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE article SET a_views = :newViews WHERE a_id = :aId", nativeQuery=true)
	void setNewViews(Integer newViews, Integer aId);
	
	@Query(value="SELECT a_likes FROM article WHERE a_id = :aId", nativeQuery=true)
	Integer getCurrentLikes(Integer aId);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE article SET a_likes = :newLikes WHERE a_id = :aId", nativeQuery=true)
	void setNewLikes(Integer newLikes, Integer aId);
	
	//計算id發文數
	@Query(value = "SELECT * FROM article WHERE m_id = :mId", nativeQuery = true)
    List<Article> findArticleByMid(Integer mId);
	
	//計算id發文數
	@Query(value = "SELECT COUNT(*) FROM article WHERE m_id = :mId", nativeQuery = true)
    Integer countPostByMid(Integer mId);
	
	@Query(value = "SELECT * FROM article WHERE t_id = :tId", nativeQuery = true)
    List<Article> findArticleListByTid(Integer tId);
	
	@Query(value = "SELECT m_id, SUM(a_likes), SUM(a_views) FROM article GROUP BY m_id ORDER BY SUM(a_likes) DESC LIMIT 10", nativeQuery = true)
	List<Integer[]> getALikesRandking();
	
	@Query(value = "SELECT m_id, SUM(a_likes), SUM(a_views) FROM article GROUP BY m_id ORDER BY SUM(a_views) DESC LIMIT 10", nativeQuery = true)
	List<Integer[]> getAViewsRandking();
	
	@Transactional
	@Modifying
	@Query(value="INSERT INTO article (a_id, a_content, a_date, a_likes, a_title, a_views, m_id, t_id) VALUES (:aId, :aContent, :aDate, :aLikes, :aTitle, :aViews, :mId, :tId)", nativeQuery=true)
	void saveArticleWithId(Integer aId, String aContent, Date aDate, Integer aLikes, String aTitle, Integer aViews, Integer mId, Integer tId);
}
