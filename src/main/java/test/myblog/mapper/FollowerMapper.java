package test.myblog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import test.myblog.model.Follower;

public interface FollowerMapper {

	Follower findById(@Param("f_id") Integer f_id);
	List<Follower> findAll();
//	Integer save(@Param("follower_id") Integer follower_id, @Param("m_id") Integer m_id);
	Integer save(Follower f);
	void deleteByMidAndFollowerid(@Param("m_id") Integer m_id, @Param("follower_id") Integer follower_id);
	void createByMidAndFollowerid(@Param("m_id") Integer m_id, @Param("follower_id")Integer followerid);
	Integer countFollowed(@Param("m_id") Integer m_id);
	Integer countFollowing(@Param("m_id") Integer m_id);
	List<Integer> findFollowingListByMid(@Param("m_id") Integer m_id);
	List<Integer> findFollowedListByMid(@Param("m_id") Integer m_id);
	Integer checkIfUserIsFollower(@Param("userId") Integer userId, @Param("mId") Integer mId);
}
