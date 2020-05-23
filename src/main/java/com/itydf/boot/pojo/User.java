package com.itydf.boot.pojo;

import javax.persistence.*;
import java.util.Objects;

@Entity                     //告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "user")       //@Table指定和哪个表对应，如果省略该注解默认表名就是user
public class User {

    @Id      //这是一个主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //自增主键
    private Integer id;

    @Column  //省略默认列名就是属性名
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //无参数的构造器
    public User() {
    }

    //添加信息：有参数的构造器
    public User(String name){
        this.name = name;
    }

    //更新信息：有参数的构造器
    public User(Integer id,String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
