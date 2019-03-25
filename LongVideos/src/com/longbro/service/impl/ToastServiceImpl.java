package com.longbro.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longbro.bean.Toast;
import com.longbro.dao.ToastDao;
import com.longbro.service.ToastService;
/**
 * 吐槽内容处理-service层
 * @author 赵成龙
 * @website www.longqcloud.cn & www.zy52113.com
 * @date 2019年3月10日 下午2:29:38
 * @description
 * @version
 */
@Service
public class ToastServiceImpl implements ToastService{
	@Autowired
	ToastDao dao;
	@Override
	public List<Map<Object,Object>> findAllToast() {
		// TODO Auto-generated method stub
		return dao.findAllToast();
	}
	@Override
	public int deleteToaById(Integer cId) {
		// TODO Auto-generated method stub
		return dao.deleteToaById(cId);
	}
	@Override
	public int insert(Toast toast) {
		// TODO Auto-generated method stub
		return dao.insert(toast);
	}
	@Override
	public int updateToaById(Toast toast) {
		// TODO Auto-generated method stub
		return dao.updateToaById(toast);
	}
	@Override
	public int insertSelective(Toast toast) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Toast selectByPrimaryKey(Integer cId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int updateByPrimaryKeySelective(Toast record) {
		// TODO Auto-generated method stub
		return 0;
	}
}
