package test.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import test.myblog.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer>{
	
}
