package com.jxl.studentmanger.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxl.studentmanger.entity.Admin;
import com.jxl.studentmanger.mapper.AdminMapper;
import com.jxl.studentmanger.service.AdminService;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
}
