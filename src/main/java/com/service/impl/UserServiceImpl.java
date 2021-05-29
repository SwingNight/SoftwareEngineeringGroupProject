
package com.service.impl;


import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dao.UserDao;
import com.entity.UserEntity;
import com.service.UserService;


/**
 * Users of the system
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {


}
