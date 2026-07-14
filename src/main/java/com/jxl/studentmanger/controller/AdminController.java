package com.jxl.studentmanger.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxl.studentmanger.entity.Admin;
import com.jxl.studentmanger.exception.BusinessException;
import com.jxl.studentmanger.response.R;
import com.jxl.studentmanger.response.ResponseCode;
import com.jxl.studentmanger.service.impl.AdminServiceImpl;
import com.jxl.studentmanger.util.CaptchaCache;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//@RestController这个注解可以指定这是一个控制器类，默认是小写，如果是小写，就不需要指定控制器类名
@RestController
//@Tag这个注解可以指定这是一个标签，默认是小写，如果是小写，就不需要指定标签名，接口文档
@Tag(name = "管理员信息管理")
public class AdminController {
    //@Resource这个注解可以指定注入一个资源，默认是小写，如果是小写，就不需要指定资源名
    @Resource
    private AdminServiceImpl adminService;
    @Resource
    private CaptchaCache captchaCache;

    @Operation(summary = "新增管理员")
    //@PostMapping这个注解可以指定这是一个POST请求，默认是小写，如果是小写，就不需要指定请求路径
    @PostMapping("/admin/add")
    @CrossOrigin
    @SaCheckLogin
    public R add(@RequestBody Admin admin){
        //MyBatis-Plus 提供的一个查询条件构造器，用于构建查询条件
        LambdaQueryWrapper<Admin> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Admin::getUsername,admin.getUsername());
        long count = adminService.count(lambdaQueryWrapper);
        if (count>0){
            throw new BusinessException(ResponseCode.USERNAME_EXIST);
        }
        // 使用MD5算法对管理员密码进行加密
        admin.setUserpwd(DigestUtils.md5DigestAsHex(admin.getUserpwd().getBytes()));
        adminService.save(admin);
        return R.success();
    }
    /**
     * 查询所有管理员信息
     * @param pagenum 当前页码
     * @param pagesize 每页条数
     */
    //@Operation这个注解可以指定这是一个操作，默认是小写，如果是小写，就不需要指定操作名，接口文档
    @Operation(summary = "查询管理员列表")
    @PostMapping("/admin/list")
    @CrossOrigin
    @SaCheckLogin
    public R<PageInfo<Admin>> list(@RequestBody Admin admin,@RequestParam Integer pagenum, @RequestParam Integer pagesize){
        LambdaQueryWrapper<Admin> adminWrapper = new LambdaQueryWrapper<>();
        if(ObjectUtils.isNotEmpty(admin.getName())){
            adminWrapper.like(Admin::getName,admin.getName());
        }
        if(ObjectUtils.isNotEmpty(admin.getTel())){
            adminWrapper.like(Admin::getTel,admin.getTel());
        }
        adminWrapper.orderByDesc(Admin::getId);
        PageHelper.startPage(pagenum,pagesize);
        List<Admin> adminList = adminService.list(adminWrapper);
        //包装查询结果为分页信息
        PageInfo<Admin> pageInfo = new PageInfo<>(adminList);
        return R.data(pageInfo);
    }
    @Operation(summary = "修改管理员")
    @PostMapping("/admin/update")
    @CrossOrigin
    @SaCheckLogin
    //@RequestBody这个注解可以指定请求体中的数据是JSON格式，默认是小写，如果是小写，就不需要指定请求体中的数据格式
    public R update(@RequestBody Admin admin){
        adminService.updateById(admin);
        return R.success();
    }
    @Operation(summary = "删除管理员")
    @PostMapping("/admin/delete")
    @CrossOrigin
    @SaCheckLogin
    public R delete(@RequestParam List<Long> ids){
        adminService.removeByIds(ids);
        return R.success();
    }

    /**
     * 检验用户名是否唯一
     */
    @GetMapping(value = "/admin/checkUsername")
    @CrossOrigin
    public R checkUsername(@RequestParam String username)  {
        LambdaQueryWrapper<Admin> adminWrapper = new LambdaQueryWrapper<>();
        adminWrapper.eq(Admin::getUsername,username);
        List<Admin> adminList = adminService.list(adminWrapper);
        if (adminList != null && adminList.size() > 0) {
            return R.fail(ResponseCode.USERNAME_EXIST);
        }
        return R.success();
    }

    /**
     * 管理员登录
     * @return
     */
    @Operation(summary = "管理员登录")
    @PostMapping("/admin/login")
    @CrossOrigin
    public R<Admin> login(@RequestBody Admin admin){
        if (StringUtils.isBlank(admin.getCaptchaId()) || StringUtils.isBlank(admin.getCaptchaCode())) {
            return R.fail(ResponseCode.CAPTCHA_ERROR);
        }
        boolean result = captchaCache.validateCaptcha(admin.getCaptchaId(), admin.getCaptchaCode());
        if (!result) {
            return R.fail(ResponseCode.CAPTCHA_ERROR);
        }
        captchaCache.removeCaptcha(admin.getCaptchaId());
        String encryptedPwd = DigestUtils.md5DigestAsHex(admin.getUserpwd().getBytes());
        LambdaQueryWrapper<Admin> adminWrapper = new LambdaQueryWrapper<>();
        adminWrapper.eq(Admin::getUsername,admin.getUsername());
        adminWrapper.eq(Admin::getUserpwd,encryptedPwd);
        admin = adminService.getOne(adminWrapper);
        //数据库没有数据，就提示用户名密码错误
        if(admin == null){
            throw new BusinessException(ResponseCode.USERNAME_USERPWD_ERROR);
        }
        //根据ID进行登录
        StpUtil.login(admin.getId());
        //获取当前登录会话的token，赋值给admin的token属性
        admin.setToken(StpUtil.getTokenValue());
        return R.data(admin);
    }

    /**
     * 管理员退出登录
     * @return
     */
    @Operation(summary = "管理员退出登录")
    @PostMapping("/admin/loginout")
    public R loginout(){
        //退出当前会话
        StpUtil.logout();
        return R.success();
    }

    /**
     * 重置管理员密码。
     * @return 返回一个表示操作结果的对象，通常表示操作成功。
     */
    @Operation(summary = "重置管理员密码")
    @PostMapping(value = "/admin/resetPwd")
    @CrossOrigin
    @SaCheckLogin
    public R resetPwd(@RequestParam Integer id)  {
        if (id == null) {
            throw new BusinessException(ResponseCode.ERROR);
        }
        Admin admin = adminService.getById(id);
        if (admin == null) {
            throw new BusinessException("管理员不存在");
        }
        admin.setUserpwd(DigestUtils.md5DigestAsHex("123456".getBytes()));
        adminService.updateById(admin);
        return R.success();
    }
}