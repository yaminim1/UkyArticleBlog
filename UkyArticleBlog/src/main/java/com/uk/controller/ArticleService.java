package com.uk.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;

public interface ArticleService {

	public ArticleDTO create(String userName, ArticleDTO articleDTO);

	public ArticleDTO findById(Integer id);

	public ArticleDTO updateVerify(Integer id);

	public List<ArticleDTO> findByTitle(String title);

	public List<ArticleDTO> findAllByVerify();

	public void removeArticle(Integer articleId);

	public ReplyDTO createReply(Integer articleTd, String userName, ReplyDTO replyDTO);

	public List<ArticleDTO> findAllByCreatedBy(String createdBy);

	public ArticleDTO updateDelFlg(Integer id);

	List<ArticleDTO> findAll(PageRequest pageRequest);

	public ReplyDTO updateVerifyReply(Integer id);

	public ReplyDTO updateDelFlgReply(Integer id);

	public List<ReplyDTO> getComments(int id);


}
