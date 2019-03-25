package com.longbro.service;

import java.util.List;
import java.util.Map;

import com.longbro.bean.Comment;
import com.longbro.bean.Toast;

public interface ToastService {
	List<Map<Object,Object>> findAllToast();
	int deleteToaById(Integer cId);

    int insert(Toast toast);

    int insertSelective(Toast toast);

    Toast selectByPrimaryKey(Integer cId);

    int updateByPrimaryKeySelective(Toast toast);

    int updateToaById(Toast toast);
}
