package com.itydf.boot.controller;

import com.itydf.boot.dao.UserDao;
import com.itydf.boot.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserDao userDao;

    /***
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @GetMapping(value = "getUserById/{id}")
    @ResponseBody
    public User getUser(@PathVariable("id") Integer id){
        Optional<User> user = userDao.findById(id);  //根据ID查询
        return user.ofNullable(user).map(user1->user.get()).orElse(null);
    }

    /***
     * 查询所有用户List集合
     * @return
     */
    @GetMapping(value = "findAll")
    @ResponseBody
    public List<User> findAll(){
        List<User> userList = userDao.findAll();
        return userList;
    }


}
