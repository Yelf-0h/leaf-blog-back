package com.yecheng.leafblogback.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.leafblogback.mapper.UserMapper;
import com.yecheng.leafblogback.entity.User;
import com.yecheng.leafblogback.service.UserService;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2023-02-02 02:15:23
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
