package test.myblog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import test.myblog.model.Tag;

public interface TagMapper {
	
	List<Tag> findAll();
	Tag findById(@Param("t_id") Integer tId);
}
