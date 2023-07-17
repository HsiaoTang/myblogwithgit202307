package test.myblog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import test.myblog.model.CommentLikes;


public interface CommentLikesRepository extends JpaRepository<CommentLikes, Integer>{
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM commentlikes WHERE m_id = :mId AND c_id = :cId", nativeQuery = true)
    void deleteByMidAndCid(Integer mId, Integer cId);
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO commentlikes (m_id, c_id) VALUES (:mId, :cId)", nativeQuery = true)
    void createByMidAndCid(Integer mId, Integer cId);
    
    @Query(value = "SELECT COUNT(*) FROM commentlikes WHERE c_id = :cId", nativeQuery = true)
    Integer getLikeCountByCid(Integer cId);
    
    @Query(value = "SELECT COUNT(*) FROM commentlikes WHERE m_id = :mId AND c_id = :cId", nativeQuery = true)
    Integer checkIfMemberLikedComment(Integer mId, Integer cId);
    
    
}
