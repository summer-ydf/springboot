package com.itydf.boot.dao;

import com.itydf.boot.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

//继承就是子类继承父类的特征和行为,使得子类对象(实例)具有父类的实例域和方法
public interface UserDao extends JpaRepository<User,Integer> {
}
