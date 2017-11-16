package com.uk.controller;

import com.uk.model.Article;

public interface ArticleRepositoryCustom<A extends Article> {
	public void updateArticle(Integer articleId, String description);

	public void removeArticle(Integer articleId);


}
