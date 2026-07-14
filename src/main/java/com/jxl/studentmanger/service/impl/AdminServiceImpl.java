package com.jxl.studentmanger.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxl.studentmanger.entity.Admin;
import com.jxl.studentmanger.mapper.AdminMapper;
import com.jxl.studentmanger.service.AdminService;
import org.springframework.stereotype.Service;

//@Service这个注解可以指定这是一个服务类，默认是小写，如果是小写，就不需要指定服务类名
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
}
