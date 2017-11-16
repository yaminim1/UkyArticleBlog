package com.uk.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uk.model.Article;

@Repository
public interface ArticleRepository<A extends Article> extends CrudRepository<Article, Integer> {
	public A getById(Integer id);

	public Page<A> findAllByMarkAsDeleted(boolean flag,Pageable pageable);
	@Query("select A from Article A where "+
	"Lower(A.title) Like Lower(CONCAT('%',:title,'%')) or lower(A.description) like lower(concat('%',:title,'%'))")
	List<Article> findAllByTitle(@Param("title") String title);

	List<Article> findAllByCreatedBy(String createdBy);

	List<Article> findAllByVerifyFlag(boolean flag);
}