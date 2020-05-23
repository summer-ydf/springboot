package com.itydf.boot.service;


import com.itydf.boot.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    Map<String,Object> findUserByName(String name);

    User findCustomByName(String name);

    List<User> findVagueByName(String name);
}
