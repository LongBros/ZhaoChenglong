package com.longbro.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.longbro.bean.Comment;
import com.longbro.dao.CommentDao;

@Repository
public class CommentDaoImpl implements CommentDao{
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	@Override
	public int deleteComById(Integer cId) {
		// TODO Auto-generated method stub		
		return sqlSessionTemplate.delete("com.longbro.bean.comment.deleteComById", cId);
	}
	@Override
	public int insert(Comment record) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert("com.longbro.bean.comment.insert", record);
	}
	@Override
	public int updateComById(Comment record) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("com.longbro.bean.comment.updateComById", record);
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
