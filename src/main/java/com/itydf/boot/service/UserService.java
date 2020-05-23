package com.itydf.boot.service;


import java.util.Map;

public interface UserService {

    Map<String,Object> findUserByName(String name);
}
