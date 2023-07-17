package test.myblog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import test.myblog.model.Comment;

public interface CommentMapper {

	Comment findById(@Param("c_id") Integer cId);
	List<Comment> findAll();
	Integer save(Comment c);
	List<Comment> getCommentListByAid(@Param("a_id") Integer aId);
	Comment getCountOfCommentTable();
	Integer getCurrentCommentLikes(@Param("c_id") Integer cId);
	void setNewCommentLikes(@Param("newCLikes") Integer newCLikes, @Param("cId") Integer cId);
	Integer getCommentCountByAid(@Param("aId") Integer aId);
	

}
