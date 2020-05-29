package com.itydf.boot.controller;

import com.itydf.boot.dao.UserDao;
import com.itydf.boot.pojo.User;
import com.itydf.boot.query.UserQuery;
import com.itydf.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.*;

@Controller
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    /***
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @GetMapping(value = "getById/{id}")
    @ResponseBody
    public User getUser(@PathVariable("id") Integer id){
        Optional<User> user = userDao.findById(id);  //根据ID查询
        return user.ofNullable(user).map(user1->user.get()).orElse(null);
    }

    /***
     * 查询所有用户List集合
     * @return
     */
    @GetMapping(value = "list")
    @ResponseBody
    public List<User> findAll(){
        return userDao.findAll();
    }

    /**
     * 添加用户信息
     * @return
     */
    @PostMapping(value = "save")
    @ResponseBody
    public Map<String,Object> saveUser(){
        Map<String,Object> map = new HashMap<>();
        User user = userDao.save(new User("DT开发者002"));
        if (!StringUtils.isEmpty(user)){
            map.put("code",2000);
            map.put("message","添加成功");
            map.put("data",user);
        }else {
            map.put("code",20001);
            map.put("message","添加失败");
        }
        return map;
    }

    /***
     * 根据id修改用户信息
     * @return
     */
    @PostMapping(value = "update/{id}")
    @ResponseBody
    public Map<String,Object> updateUserById(@PathVariable Integer id){
        Map<String,Object> map = new HashMap<>();
        //1、根据id查询用户信息
        Optional<User> user = userDao.findById(id);
        if(user.isPresent()){
            //2、调用接口进行更新
            User update = userDao.save(new User(user.get().getId(), "DT开发者0022"));
            //3、判断是否成功
            if(!StringUtils.isEmpty(update)){
                map.put("code",2000);
                map.put("message","修改成功");
                map.put("data",user);
            }else {
                map.put("code",2001);
                map.put("message","事务回滚");
                map.put("data",user);
            }
            System.out.println("ID==="+user.get().getId());
        }else {
            map.put("code",2001);
            map.put("message","修改失败");
            map.put("data",user);
        }
        return map;
    }

    /**
     *根据主键id删除用户信息
     * @return
     */
    @DeleteMapping(value = "remove/{id}")
    @ResponseBody
    public Map<String,Object> removeUserById(@PathVariable Integer id){
        Map<String,Object> map = new HashMap<>();
        userDao.deleteById(id);
        map.put("code",2000);
        map.put("message","删除成功");
        map.put("data",id);
        return map;
    }

    /**
     * 根据用户名查询用户信息
     * @param name
     * @return
     */
    @GetMapping(value = "findUserByName")
    @ResponseBody
    public Map<String,Object> findUserByName(@PathParam("name") String name){
        return userService.findUserByName(name);
    }

    /**
     * 自定义：根据用户名查询用户信息
     * @param name
     * @return
     */
    @GetMapping(value = "findCustomByName")
    @ResponseBody
    public User findCustomByName(@PathParam("name") String name){
        return userService.findCustomByName(name);
    }

    /**
     * 自定义：根据用户名查询用户信息 (模糊查询)
     * @param name
     * @return
     */
    @GetMapping(value = "findVagueByName")
    @ResponseBody
    public List<User> findVagueByName(@PathParam("name") String name){
        return userService.findVagueByName(name);
    }

    /**
     * 自定义：根据用户名id更新用户信息
     * @param user
     * @return
     */
    @PostMapping(value = "updateCustomByById")
    @ResponseBody
    public Map<String,Object> updateCustomByById(User user){
        return userService.updateCustomByById(user);
    }


    /**
     * 自定义：根据用户名id删除用户信息
     * @param id
     * @return
     */
    @DeleteMapping(value = "removeCustomByById/{id}")
    @ResponseBody
    public Map<String,Object> removeCustomByById(@PathVariable Integer id){
        return userService.removeCustomByById(id);
    }


    /**
     * 分页（无条件查询）
     * @param modelMap
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GetMapping(value = "listUsers")
    public String listUsers(ModelMap modelMap,
                                @RequestParam(value = "pageNumber",defaultValue = "0") Integer pageNumber,
                                @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){
        //设置分页
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.Direction.DESC, "id");
        //进行查询
        Page<User> userPage = userDao.findAll(pageable);
        modelMap.addAttribute("data",userPage);
        return "index";
    }

    /**
     * 分页（带条件查询）
     * @param modelMap
     * @param pageNumber
     * @param pageSize
     * @param userQuery
     * @return
     */
    @GetMapping(value = "listUserQuery")
    public String listUserQuery(ModelMap modelMap,
                                    @RequestParam(value = "pageNumber",defaultValue = "0") Integer pageNumber,
                                    @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                    UserQuery userQuery){
        //设置分页
        Pageable pageable = PageRequest.of(pageNumber,pageSize,Sort.Direction.ASC, "id");
        //进行查询
        Page<User> userPage = userService.findUserQuery(pageable,userQuery);
        modelMap.addAttribute("data",userPage);
        modelMap.addAttribute("name",userQuery.getName());
        modelMap.addAttribute("sex",userQuery.getSex());
        modelMap.addAttribute("position",userQuery.getPosition());
        return "home";
    }

    /**
     * 批量添加
     * @return
     */
    @PostMapping(value = "saveAll")
    @ResponseBody
    public Map<String,Object> saveAll(){
        Map<String,Object> map = new HashMap<>();
        for (int i = 0; i < 5000; i++) {
            User user = new User("李四"+i,1,"研发部");
            userDao.save(user);
        }
        map.put("code",2000);
        map.put("message","success");
        return map;
    }


}
