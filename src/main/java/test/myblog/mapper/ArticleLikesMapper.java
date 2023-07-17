package test.myblog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import test.myblog.model.ArticleLikes;

public interface ArticleLikesMapper {

	ArticleLikes findById(@Param("al_id") Integer alId);
	List<ArticleLikes> findAll();
	Integer save(ArticleLikes al);
	Integer getLikeCountByAid(@Param("aId")Integer aId);
	void createByMidAndAid(@Param("mId")Integer mId, @Param("aId")Integer aId);
	void deleteByMidAndAid(@Param("mId")Integer mId, @Param("aId")Integer aId);
	Integer checkIfMemberLikedArticle(@Param("mId")Integer mId, @Param("aId")Integer aId);

}
