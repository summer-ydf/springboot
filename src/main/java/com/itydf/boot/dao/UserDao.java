package com.itydf.boot.dao;

import com.itydf.boot.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//继承就是子类继承父类的特征和行为,使得子类对象(实例)具有父类的实例域和方法
public interface UserDao extends JpaRepository<User,Integer> {

    /**
     * 自定义：根据用户名查询用户信息
     * @param name
     * @return
     */
    @Query(value = "select id,name from user where name = :name",nativeQuery = true)
    User findCustomByName(@Param("name") String name);

    /**
     * 自定义：根据用户名查询用户信息 (模糊查询)
     * @param name
     * @return
     */
    //@Query(value = "select id,name from user where name like %:name%",nativeQuery = true) //第一种写法
    @Query(value = "select t.id,t.name from user t where t.name like %?1%",nativeQuery = true)  //第二种写法 1表示占位符，从第一个参数开始
    List<User> findVagueByName(String name);

}
