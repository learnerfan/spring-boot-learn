package com.sc.zhenli.controller;

import com.sc.zhenli.bean.UserBean;
import com.sc.zhenli.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Created by songsf on 2019/3/31.
 */
@RestController
@RequestMapping("/api/v1.0/user")
public class UserController {
    @Autowired
    private UserDao userDao;
    @RequestMapping(value = "/current",method = RequestMethod.GET)
    public UserBean me(@RequestParam()String userName) {
        List<UserBean> userBeen = userDao.getUserInfo(userName);
        if (userBeen != null && userBeen.size() > 0){
            return userBeen.get(0);
        }
        return new UserBean();
    }
}
