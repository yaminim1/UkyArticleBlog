package com.uk.controller;

import java.util.Date;
import java.util.List;

public class ArticleDTO {
	private int id;
	private String title;
	private String description;
	private int replyCount;
	private boolean repliedFlag;
	private boolean verifyFlag;
	private List<ReplyDTO> reply;
	
	private boolean markAsDeleted;
	private String createdBy;
	private Date createdDate;
	private String lastUpdatedBy;
	private Date lastUpdatedDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	
	public boolean isVerifyFlag() {
		return verifyFlag;
	}
	public void setVerifyFlag(boolean verifyFlag) {
		this.verifyFlag = verifyFlag;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getreplyCount() {
		return replyCount;
	}
	public void setreplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public boolean isRepliedFlag() {
		return repliedFlag;
	}
	public void setRepliedFlag(boolean repliedFlag) {
		this.repliedFlag = repliedFlag;
	}
	public List<ReplyDTO> getReply() {
		return reply;
	}
	public void setReply(List<ReplyDTO> reply) {
		this.reply = reply;
	}
	public boolean isMarkAsDeleted() {
		return markAsDeleted;
	}
	public void setMarkAsDeleted(boolean markAsDeleted) {
		this.markAsDeleted = markAsDeleted;
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
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArticleDTO [id=");
		builder.append(id);
		builder.append(", title=");
		builder.append(title);
		builder.append(", description=");
		builder.append(description);
		builder.append(", replyCount=");
		builder.append(replyCount);
		builder.append(", repliedFlag=");
		builder.append(repliedFlag);
		builder.append(", verifyFlag=");
		builder.append(verifyFlag);
		builder.append(", reply=");
		builder.append(reply);
		builder.append(", markAsDeleted=");
		builder.append(markAsDeleted);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", lastUpdatedBy=");
		builder.append(lastUpdatedBy);
		builder.append(", lastUpdatedDate=");
		builder.append(lastUpdatedDate);
		builder.append("]");
		return builder.toString();
	}
}
