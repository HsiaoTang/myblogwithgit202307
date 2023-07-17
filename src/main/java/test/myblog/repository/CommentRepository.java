package test.myblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import test.myblog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
	@Query(value = "SELECT * FROM comment WHERE a_id = :aId", nativeQuery = true)
    List<Comment> getCommentListByAid(Integer aId);
	
	@Query(value="SELECT * FROM comment ORDER BY c_id DESC LIMIT 1" ,nativeQuery=true)
	Comment getCountOfCommentTable();
	
	@Query(value="SELECT COUNT(*) FROM comment WHERE a_id = :aId" ,nativeQuery=true)
	Integer getCommentCountByAid(Integer aId);
	
	
	@Query(value="SELECT c_likes FROM comment WHERE c_id = :cId", nativeQuery=true)
	Integer getCurrentCommentLikes(Integer cId);
	
	@Transactional
	@Modifying
	@Query(value="UPDATE comment SET c_likes = :newCLikes WHERE c_id = :cId", nativeQuery=true)
	void setNewCommentLikes(Integer newCLikes, Integer cId);
}
