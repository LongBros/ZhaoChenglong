package com.longbro.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.longbro.bean.Comment;
import com.longbro.service.CommentService;
import com.longbro.service.impl.CommentServiceImpl;

@Controller
public class CommentController {
	@Autowired
	CommentService service;
	/**
	 * 根据id找到评论并删除
	 */
	@RequestMapping(value="deleteComById",method=RequestMethod.GET)
	public void deleteComById(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		service.deleteComById(Integer.parseInt(id));
	}
	/**
	 * 
	 * @desc 添加评论
	 * @author zcl
	 * @date 2019年3月9日
	 * @param request
	 * @param response
	 */
	@RequestMapping("WriteComment")
	public void WriteComment(HttpServletRequest request,HttpServletResponse response){
		Comment comment=new Comment();
		comment.setcAddress(request.getParameter(""));
		comment.setcCnum(0);
		comment.setcContent(request.getParameter(""));
		comment.setcIp(request.getParameter(""));
		comment.setcTime(request.getParameter(""));
		comment.setcUser(request.getParameter(""));
		comment.setcVideo(request.getParameter(""));
		comment.setcZnum(0);
		
		service.insert(comment);
	}
	/**
	 * 
	 * @desc 修改对应id的评论内容
	 * @author zcl
	 * @date 2019年3月9日
	 * @param request
	 * @param response
	 */
	
	@RequestMapping("updateComById")
	public void updateComById(HttpServletRequest request,HttpServletResponse response){
		Comment comment=new Comment();
		comment.setcId(Integer.parseInt(request.getParameter("id")));
		comment.setcContent(request.getParameter("content"));
		service.updateComById(comment);
	}
}
