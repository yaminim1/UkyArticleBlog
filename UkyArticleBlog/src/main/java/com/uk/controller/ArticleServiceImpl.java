package com.uk.controller;

import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;

import com.uk.model.Article;
import com.uk.model.Reply;

@Service
@EnableJpaRepositories("com.uk.controller")
public class ArticleServiceImpl implements ArticleService {

	private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

	@Autowired
	private ArticleRepository<Article> repository;

	@Autowired
	private ReplyRepository<Reply> replyRepository;

	@Autowired
	private ArticleRepositoryCustom<Article> articleRepositoryCustom;

	Mapper dozerBeanMapper = new DozerBeanMapper();

	/*
	 * Method to create a new Article
	 */
	@Override
	public ArticleDTO create(String userName, ArticleDTO articleDTO) {
		log.info("Creating a Article with information: {}", articleDTO);

		Article article = convertToPersistedModel(articleDTO);

		article.setCreatedBy(userName);
		article.setCreatedDate(new Date());
		article.setReplyCount(0);
		article = repository.save(article);
		log.info("Created a new Article with information: {}", article);

		return convertToDTO(article);
	}
/* Method to update hte verify flag when admin approves an article*/
	public ArticleDTO updateVerify(Integer id) {
		Article article = repository.findOne(id);
		article.setVerifyFlag(true);
		article = repository.save(article);
		return convertToDTO(article);
	}
/* Method to update DelFlg when admin rejects the article*/
	public ArticleDTO updateDelFlg(Integer id) {
		log.info("in updateDelFlg");
		Article article = repository.findOne(id);
		article.setMarkAsDeleted(true);
		article = repository.save(article);
		return convertToDTO(article);
	}

	@Override
	public List<ArticleDTO> findAll(PageRequest pageRequest) {
		log.info("Finding all Article requests.");

		List<Article> articles = new ArrayList<>();
		repository.findAllByMarkAsDeleted(false, pageRequest).forEach(articles::add);

		log.info("Found {} Article requests", articles.size());

		List<ArticleDTO> articleDTOs = convertToDTOs(articles);

		articleDTOs = setRepliesToNull(articleDTOs);

		return articleDTOs;

	}

	public List<ArticleDTO> findAllByVerify() {
		log.info("Finding all Article requests.");

		List<Article> articles = new ArrayList<>();
		repository.findAllByVerifyFlag(true).forEach(articles::add);
		log.info("Found {} Article requests", articles.size());

		List<ArticleDTO> articleDTOs = convertToDTOs(articles);

		articleDTOs = setRepliesToNull(articleDTOs);
		return articleDTOs;
	}

	@Override
	public List<ArticleDTO> findByTitle(String title) {
		log.info("Finding all Article requests.");

		// List<Article> articles = repository.findByMarkAsDeleted(false);
		List<Article> articles = new ArrayList<>();

		repository.findAllByTitle(title).forEach(articles::add);

		log.info("Found by title {} Article requests", articles.size());

		List<ArticleDTO> articleDTOs = convertToDTOs(articles);

		return articleDTOs;
	}

	public ReplyDTO createReply(Integer articleId, String userName, ReplyDTO replyDTO) {
		log.info("Creating a reply with information: {}", replyDTO);
		log.info("article id", articleId);
		Reply reply = convertToPersistedModel(replyDTO);
		Article article = repository.findOne(articleId);
		log.info("getReplyCount" + article.getreplyCount());
		article.setreplyCount(article.getreplyCount() + 1);
		log.info("article id{}", articleId);
		log.info("reply id{}", reply.getReplyId());

		reply.setCreatedBy(userName);
		reply.setCreatedDate(new Date());
		reply.setArticle(article);
		reply.setMarkAsDeleted(false);

		reply = replyRepository.save(reply);

		log.info("Created a new reply with information: {}", reply);

		return convertToReplyDTO(reply);
	}

	private Reply convertToPersistedModel(ReplyDTO replyDTO) {
		Reply reply = dozerBeanMapper.map(replyDTO, Reply.class);
		return reply;
	}

	public ReplyDTO convertToReplyDTO(Reply model) {

		ReplyDTO dto = dozerBeanMapper.map(model, ReplyDTO.class);

		return dto;
	}

	private List<ArticleDTO> setRepliesToNull(List<ArticleDTO> articlesDTO) {
		for (ArticleDTO ArticleDTO : articlesDTO) {
			ArticleDTO.setReply(null);
		}

		return articlesDTO;
	}

	private List<ArticleDTO> convertToDTOs(List<Article> models) {
		return models.stream().map(this::convertToDTO).collect(toList());
	}

	private List<ReplyDTO> convertToReplyDTOs(List<Reply> models) {
		return models.stream().map(this::convertToReplyDTO).collect(toList());
	}

	public ArticleDTO convertToDTO(Article model) {

		ArticleDTO dto = dozerBeanMapper.map(model, ArticleDTO.class);

		return dto;
	}

	public Article convertToPersistedModel(ArticleDTO dto) {

		Article Article = dozerBeanMapper.map(dto, Article.class);

		return Article;
	}

	@Override
	public ArticleDTO findById(Integer id) {
		log.info("Finding topic with id: {}", id);

		Article article = repository.findOne(id);

		log.info("Found topic: {}", article);

		ArticleDTO articleDTO = convertToDTO(article);

		return articleDTO;
	}

	@Override
	public void removeArticle(Integer articleId) {
		articleRepositoryCustom.removeArticle(articleId);
	}

	@Override
	public List<ArticleDTO> findAllByCreatedBy(String createdBy) {
		log.info("Finding all Article requests.");

		List<Article> articles = new ArrayList<>();
		repository.findAllByCreatedBy(createdBy).forEach(articles::add);
		log.info("Found {} Article requests", articles.size());

		List<ArticleDTO> articleDTOs = convertToDTOs(articles);

		articleDTOs = setRepliesToNull(articleDTOs);
		return articleDTOs;
	}

	@Override
	public ReplyDTO updateVerifyReply(Integer id) {
		// List<Reply> replies = repository.findOne(id).getReply();
		Reply reply = replyRepository.getByReplyId(id);
		// replyRepository.findAllByCreatedBy(createdBy).forEach(articles::add);
		reply.setVerifyFlag(true);
		reply = replyRepository.save(reply);
		return convertToReplyDTO(reply);
	}

	@Override
	public ReplyDTO updateDelFlgReply(Integer id) {
		// List<Reply> replies = repository.findOne(id).getReply();
		Reply reply = replyRepository.getByReplyId(id);
		Article article = repository.findOne(reply.getArticle().getId());
		log.info("getReplyCount" + article.getreplyCount());
		article.setreplyCount(article.getreplyCount() - 1);
		// replyRepository.findAllByCreatedBy(createdBy).forEach(articles::add);
		reply.setMarkAsDeleted(true);
		reply.setArticle(article);
		reply = replyRepository.save(reply);
		return convertToReplyDTO(reply);

	}

	@Override
	public List<ReplyDTO> getComments(int articleId) {
		List<Reply> replies = repository.findOne(articleId).getReply();
		log.info("Comments by id{}:" + repository.findOne(articleId));
		log.info("replies Size {}:" + replies.size());
		List<ReplyDTO> replyDTOs = convertToReplyDTOs(replies);
		return replyDTOs;
	}
}
