package test.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import test.myblog.model.ArticleLikes;

public interface AritcleLikesRepository extends JpaRepository<ArticleLikes, Integer>{
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM articlelikes WHERE m_id = :mId AND a_id = :aId", nativeQuery = true)
    void deleteByMidAndAid(Integer mId, Integer aId);
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO articlelikes (m_id, a_id) VALUES (:mId, :aId)", nativeQuery = true)
    void createByMidAndAid(Integer mId, Integer aId);
    
	@Query(value = "SELECT COUNT(*) FROM articlelikes WHERE a_id = :aId", nativeQuery = true)
    Integer getLikeCountByAid(Integer aId);
	
	@Query(value = "SELECT COUNT(*) FROM articlelikes WHERE m_id = :mId AND a_id = :aId", nativeQuery = true)
	Integer checkIfMemberLikedArticle(Integer mId, Integer aId);
}

