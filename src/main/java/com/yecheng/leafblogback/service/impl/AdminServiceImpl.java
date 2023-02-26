package com.yecheng.leafblogback.service.impl;

import com.yecheng.leafblogback.bean.entity.Admin;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.leafblogback.mapper.AdminMapper;
import com.yecheng.leafblogback.service.AdminService;

/**
 * (Admin)表服务实现类
 *
 * @author makejava
 * @since 2023-02-02 02:07:46
 */
@Service("adminService")
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
