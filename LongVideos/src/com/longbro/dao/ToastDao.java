package com.longbro.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.longbro.bean.Comment;
import com.longbro.bean.Toast;

public interface ToastDao {
	List<Map<Object,Object>> findAllToast();
	int deleteToaById(Integer cId);
    int insert(Toast toast);
    int updateToaById(Toast toast);

    int insertSelective(Toast toast);

    Toast selectByPrimaryKey(Integer cId);

    int updateByPrimaryKeySelective(Toast toast);

}
