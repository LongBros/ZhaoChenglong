package com.longbro.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import utils.AddressUtils;
import utils.SqlUtil;

import com.longbro.bean.Toast;
import com.longbro.service.ToastService;
/**
 * 吐槽内容操作-Controller层
 * @author 赵成龙
 * @website www.longqcloud.cn & www.zy52113.com
 * @date 2019年3月10日 下午2:56:54
 * @description
 * @version
 */
@Controller
public class ToastController {
	@Autowired
	ToastService service;
	/**
	 * 查询所有吐槽
	 * @desc 
	 * @author zcl
	 * @date 2019年3月10日
	 */
	@RequestMapping("findAllToast")
	@ResponseBody
	public List<Map<Object,Object>> findAllToast(){
		List<Map<Object,Object>> toast=service.findAllToast();
//		System.out.println(toast);
		for(Map<Object,Object> map:toast){
			System.out.println(map.get("w_Id"));
			System.out.println(map.get("w_Nick"));
			System.out.println(map.get("w_Tx"));
			System.out.println(map.get("w_Content"));
			System.out.println(map.get("w_Ip"));
			System.out.println(map.get("w_Add"));
			System.out.println(map.get("w_Time"));
			System.out.println("-------------------------------------------------------------------");
		}
		return toast;
	}
	/**
	 * 根据id找到吐槽并删除
	 */
	@RequestMapping(value="deleteToaById",method=RequestMethod.GET)
	public void deleteToaById(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		service.deleteToaById(Integer.parseInt(id));
	}
	/**
	 * 
	 * @desc 添加吐槽
	 * @author zcl
	 * @date 2019年3月9日
	 * @param request
	 * @param response
	 */
	@RequestMapping("writeToast")
	public void writeToast(HttpServletRequest request,HttpServletResponse response){
		Toast toast=new Toast();
		toast.setW_Content(request.getParameter("content"));
		toast.setW_Nick(request.getParameter("nick"));
		toast.setW_Tx(request.getParameter("tx"));
		
		toast.setW_Ip(SqlUtil.getIp(request));
		toast.setW_Add(AddressUtils.getAddByIp(SqlUtil.getIp(request)));
		toast.setW_Time(SqlUtil.time());	

		service.insert(toast);
	}
	/**
	 * 
	 * @desc 修改对应id的吐槽-可修改项有头像，昵称，和内容
	 * @author zcl
	 * @date 2019年3月9日
	 * @param request
	 * @param response
	 */
	
	@RequestMapping("updateToaById")
	public void updateToaById(HttpServletRequest request,HttpServletResponse response){
		Toast toast=new Toast();
		toast.setW_Content(request.getParameter("content"));
		toast.setW_Nick(request.getParameter("nick"));
		toast.setW_Tx(request.getParameter("tx"));

		service.updateToaById(toast);
	}
}
