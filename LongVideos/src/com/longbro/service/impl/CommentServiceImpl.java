package com.longbro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longbro.bean.Comment;
import com.longbro.dao.CommentDao;
import com.longbro.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
	CommentDao dao;
	@Override
	public int deleteComById(Integer cId) {
		// TODO Auto-generated method stub
		return dao.deleteComById(cId);
	}
	@Override
	public int insert(Comment record) {
		// TODO Auto-generated method stub
		return dao.insert(record);
	}
	@Override
	public int updateComById(Comment record) {
		// TODO Auto-generated method stub
		return dao.updateComById(record);
	}
	@Override
	public int insertSelective(Comment record) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Comment selectByPrimaryKey(Integer cId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int updateByPrimaryKeySelective(Comment record) {
		// TODO Auto-generated method stub
		return 0;
	}
}
