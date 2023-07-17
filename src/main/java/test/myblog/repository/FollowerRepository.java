package test.myblog.repository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import test.myblog.model.Follower;

public interface FollowerRepository extends JpaRepository<Follower, Integer>{
	//粉絲數
	@Query(value = "SELECT COUNT(*) FROM follower WHERE m_id = :mId", nativeQuery = true)
    Integer countFollowed(Integer mId);
	
	//追蹤中
	@Query(value = "SELECT COUNT(*) FROM follower WHERE follower_id = :mId", nativeQuery = true)
    Integer countFollowing(Integer mId);
	
	//粉絲名單
    @Query(value="SELECT follower_id FROM follower WHERE m_id = ?1", nativeQuery = true)
    List<Integer> findFollowedListByMid(Integer mId);
    
    //追蹤名單
  	@Query(value = "SELECT m_id FROM follower WHERE follower_id = ?1", nativeQuery = true)
  	List<Integer> findFollowingListByMid(Integer mId);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM follower WHERE m_id = :mId AND follower_id = :followerId", nativeQuery = true)
    void deleteByMidAndFollowerid(Integer mId, Integer followerId);
    
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO follower (m_id, follower_id) VALUES (:mId, :followerId)", nativeQuery = true)
    void createByMidAndFollowerid(Integer mId, Integer followerId);
    
    @Query(value = "SELECT COUNT(*) FROM follower WHERE follower_id = :userId AND m_id = :mId", nativeQuery = true)
    Integer checkIfUserIsFollower(Integer userId, Integer mId);
}

