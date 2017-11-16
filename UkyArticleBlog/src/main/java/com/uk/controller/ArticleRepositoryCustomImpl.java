package com.uk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uk.model.Article;
@Service
public class ArticleRepositoryCustomImpl<A extends Article> implements ArticleRepositoryCustom<Article> {

	private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);
	@Autowired
	private ArticleRepository<Article> articleRepository;
	@Autowired
	private ArticleServiceImpl serviceImpl;
	
	
	@Override
	public void updateArticle(Integer articleId, String description) {
	       log.info("Update a Article with information: {}", articleId);

	       Article article = articleRepository.findOne(articleId);

	        log.info("Found article: {}", article);
	        
	        ArticleDTO articleDTO = serviceImpl.convertToDTO(article);
	        Article updatedArticle = serviceImpl.convertToPersistedModel(articleDTO);

	        populateUpdateFields(updatedArticle);
	        
	        article = articleRepository.save(article);
	        log.info("Created a new Article with information: {}", article);
	}
	private Article populateUpdateFields(Article article){
		article.setLastUpdatedDate(new Date());
		
		return article;
	}

	@Override
	public void removeArticle(Integer articleId) {
		 log.info(" Deleting Article with information: {}", articleId);
		articleRepository.delete(articleId);
		 log.info("Deleted Article with information: {}", articleId);
	}

}
