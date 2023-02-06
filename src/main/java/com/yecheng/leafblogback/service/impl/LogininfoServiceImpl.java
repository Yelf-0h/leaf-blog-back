package com.yecheng.leafblogback.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yecheng.leafblogback.mapper.LogininfoMapper;
import com.yecheng.leafblogback.entity.Logininfo;
import com.yecheng.leafblogback.service.LogininfoService;

/**
 * (Logininfo)表服务实现类
 *
 * @author makejava
 * @since 2023-02-02 02:15:23
 */
@Service("logininfoService")
public class LogininfoServiceImpl extends ServiceImpl<LogininfoMapper, Logininfo> implements LogininfoService {

}
