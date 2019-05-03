package com.sc.zhenli.dao;

import com.sc.zhenli.bean.UserBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    List<UserBean> getUserInfo(@Param("userName") String userName);
}