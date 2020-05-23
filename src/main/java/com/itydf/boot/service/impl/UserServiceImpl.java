package com.itydf.boot.service.impl;


import com.itydf.boot.dao.UserDao;
import com.itydf.boot.pojo.User;
import com.itydf.boot.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    /**
     * 根据用户名查询用户信息
     * @param name
     * @return
     */
    @Override
    public Map<String,Object> findUserByName(String name) {
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(name)){
            //1、传入查询的参数值
            User user = new User(name);
            Example<User> example = Example.of(user);
            //2、执行查询
            Optional<User> user1 = userDao.findOne(example);
            System.out.println(user1);
            user1.ifPresent(o -> {
                User user2 = user1.get();
                map.put("data",user2.toString());  //返回一个对象
            });
        }else {
            map.put("data",null);
        }
        return map;
    }

    /**
     * 自定义：根据用户名查询用户信息
     * @param name
     * @return
     */
    @Override
    public User findCustomByName(String name) {
        if(StringUtils.isNotBlank(name)){
            return userDao.findCustomByName(name);
        }else {
            return null;
        }
    }

    /**
     * 自定义：根据用户名查询用户信息 (模糊查询)
     * @param name
     * @return
     */
    @Override
    public List<User> findVagueByName(String name) {
        if(StringUtils.isNotBlank(name)){
            return userDao.findVagueByName(name);
        }else {
            return null;
        }
    }
}
