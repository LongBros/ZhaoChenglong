package com.longbro.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.longbro.bean.Toast;
import com.longbro.dao.ToastDao;
/**
 * 吐槽内容处理-dao层
 * @author 赵成龙
 * @website www.longqcloud.cn & www.zy52113.com
 * @date 2019年3月10日 下午2:29:00
 * @description
 * @version
 */
@Repository
public class ToastDaoImpl implements ToastDao{
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List<Map<Object,Object>> findAllToast() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("com.longbro.bean.toast.findAllToast");
	}
	@Override
	public int deleteToaById(Integer wId) {
		// TODO Auto-generated method stub		
		return sqlSessionTemplate.delete("com.longbro.bean.toast.deleteToaById", wId);
	}
	@Override
	public int insert(Toast toast) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert("com.longbro.bean.toast.insert", toast);
	}
	@Override
	public int updateToaById(Toast toast) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("com.longbro.bean.toast.updateToaById", toast);
	}
	@Override
	public int insertSelective(Toast record) {
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
