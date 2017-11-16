package com.uk.controller;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.uk.model.Reply;

public interface ReplyRepository<R extends Reply> extends CrudRepository<Reply, Integer>  {
	public R getByReplyId(Integer reply_id);
	
	List<Reply> findAllByDescription(String desc);
	public void delete(Reply reply);


}