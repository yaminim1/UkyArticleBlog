package com.uk.controller;

import java.util.Date;

public class ReplyDTO {
	private int id;
	private int replyId;
	private String description;
	private String createdBy;
	private Date createdDate;
	private boolean verifyFlag;
	private ArticleDTO article;
	private boolean markAsDeleted;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isVerifyFlag() {
		return verifyFlag;
	}

	public void setVerifyFlag(boolean verifyFlag) {
		this.verifyFlag = verifyFlag;
	}

	public ArticleDTO getArticle() {
		return article;
	}

	public boolean isMarkAsDeleted() {
		return markAsDeleted;
	}

	public void setMarkAsDeleted(boolean markAsDeleted) {
		this.markAsDeleted = markAsDeleted;
	}

	public void setArticle(ArticleDTO article) {
		this.article = article;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReplyDTO [id=");
		builder.append(id);
		builder.append(", replyId=");
		builder.append(replyId);
		builder.append(", description=");
		builder.append(description);
		builder.append(", verifyFlag=");
		builder.append(verifyFlag);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", markAsDeleted=");
		builder.append(markAsDeleted);
		builder.append("]");
		return builder.toString();
	}

}
