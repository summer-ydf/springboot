package com.itydf.boot.pojo;

import javax.persistence.*;

@Entity                     //告诉JPA这是一个实体类（和数据表映射的类）
@Table(name = "user")       //@Table指定和哪个表对应，如果省略该注解默认表名就是user
public class User {

    @Id      //这是一个主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //自增主键
    @Column(name = "id", unique = true, length = 11)      //unique = true 是指这个字段的值在这张表里不能重复，所有记录值都要唯一，就像主键那样;
    private Integer id;

    @Column(nullable=false,name = "name")  //省略默认列名就是属性名、nullable=false是这个字段在保存时必需有值，不能还是null值就调用save去保存入库;
    private String name;

    @Column(nullable=true,name = "sex")   //nullable默认可以为空，如果改为false，参数没有值的时候就save会报错
    private Integer sex;

    private String position;

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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    //无参数的构造器
    public User() {
    }

    //添加信息：有参数的构造器
    public User(String name){
        this.name = name;
    }

    //批量添加信息：有参数的构造器
    public User(String name,Integer sex,String position){
        this.name = name;
        this.sex = sex;
        this.position = position;
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
                ", sex=" + sex +
                ", position='" + position + '\'' +
                '}';
    }
}
