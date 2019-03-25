package com.longbro.service;

import com.longbro.bean.Comment;

public interface CommentService {
	int deleteComById(Integer cId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer cId);

    int updateByPrimaryKeySelective(Comment record);

    int updateComById(Comment record);
}
