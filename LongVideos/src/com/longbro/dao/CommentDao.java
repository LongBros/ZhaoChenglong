package com.longbro.dao;

import org.springframework.stereotype.Repository;

import com.longbro.bean.Comment;

public interface CommentDao {
	int deleteComById(Integer cId);
    int insert(Comment record);
    int updateComById(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer cId);

    int updateByPrimaryKeySelective(Comment record);

}
