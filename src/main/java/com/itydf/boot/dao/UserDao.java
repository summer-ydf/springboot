package com.itydf.boot.dao;

import com.itydf.boot.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    /**
     * 自定义：根据用户名id更新用户信息
     * @param id
     * @param name
     * @return
     */
    @Modifying
    @Query(value = "update user set name = :name where id = :id",nativeQuery = true)
    int updateCustomByById(Integer id,String name);  //入参绝对不能定义成 User对象，JPA不支持,只有查询可以使用SPEL表达式

    /**
     * 自定义：根据用户名id删除用户信息
     * 执行完modifying query， EntityManager可能会包含过时的数据，因为EntityManager不会自动清除实体。
     * 只有添加clearAutomatically属性，EntityManager才会自动清除实体对象。
     * @param id
     * @return
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "delete from user where id = :id",nativeQuery = true)
    int removeCustomByById(Integer id);

    /**
     * 分页（带条件查询）
     * @param userSpecification
     * @param pageable
     * @return
     */
    Page<User> findAll(Specification<User> userSpecification, Pageable pageable);
}
