package com.itydf.boot.service.impl;


import com.itydf.boot.dao.UserDao;
import com.itydf.boot.pojo.User;
import com.itydf.boot.query.UserQuery;
import com.itydf.boot.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

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

    /**
     * 自定义：根据用户名id更新用户信息
     * @param user
     * @return
     */
    @Override
    @Transactional  //必须加@Transactional，来代表这是一个事务级别的操作。jpa要求，'没有事务支持，不能执行更新和删除操作'
    public Map<String, Object> updateCustomByById(User user) {
        Map<String,Object> map = new HashMap<>();
        if(user !=null){
            //注意：1、返回值只有int和void 2、入参绝对不能定义成 User对象，JPA不支持,只有查询可以使用SPEL表达式
            int user1 = userDao.updateCustomByById(user.getId(),user.getName());
            if(user1 > 0){
                map.put("code","2000");
                map.put("message","修改成功");
                map.put("data",user1);
            }else {
                map.put("code","2001");
                map.put("message","修改失败");
            }
        }else {
            map.put("code","2001");
            map.put("message","参数不能为空");
        }
        return map;
    }

    /**
     * 自定义：根据用户名id删除用户信息
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Map<String, Object> removeCustomByById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        int count = userDao.removeCustomByById(id);
        if(count > 0){
            map.put("code","2000");
            map.put("message","删除成功");
            map.put("data",id);
        }else {
            map.put("code","2001");
            map.put("message","删除失败");
        }
        return map;
    }

    /**
     * 分页（带条件查询）
     * @param pageable
     * @param userQuery
     * @return
     */
    @Override
    public Page<User> findUserQuery(Pageable pageable, UserQuery userQuery) {
        Page<User> userPage = userDao.findAll(new Specification<User>(){
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                if (StringUtils.isNotBlank(userQuery.getName())){
                    list.add(criteriaBuilder.equal(root.get("name").as(String.class), userQuery.getName()));
                }
                if(userQuery.getSex() != null){
                    list.add(criteriaBuilder.equal(root.get("sex").as(Integer.class), userQuery.getSex()));
                }
                if (StringUtils.isNotBlank(userQuery.getPosition())){
                    list.add(criteriaBuilder.equal(root.get("position").as(String.class), userQuery.getPosition()));
                }
                Predicate[] p = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(p));
            }
        },pageable);
        return userPage;
    }
}
