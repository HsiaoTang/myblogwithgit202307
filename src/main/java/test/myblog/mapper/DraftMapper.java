package test.myblog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import test.myblog.model.Draft;


public interface DraftMapper {
	Integer save(Draft d);
	List<Draft> findByMid(@Param("mId") Integer mId);
	Integer deleteByDidAndMid(@Param("dId") Integer dId, @Param("mId") Integer mId);
	Integer updateByDidAndMid(Draft d);
	Draft findLastDraft();
}
