package test.myblog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import test.myblog.model.CommentLikes;

public interface CommentLikesMapper {

	CommentLikes findById(@Param("cl_id")Integer cl_id);
	List<CommentLikes> findAll();
	Integer save(CommentLikes cl);
	Integer checkIfMemberLikedComment(@Param("m_id") Integer mId, @Param("c_id") Integer cId);
	void createByMidAndCid(@Param("m_id") Integer mId, @Param("c_id")Integer cId);
	void deleteByMidAndCid(@Param("m_id") Integer mId, @Param("c_id")Integer cId);
	
}
