package com.jxl.studentmanger.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
//@TableName这个注解可以指定数据库表名，默认是类名的小写，如果是类名的小写，就不需要指定表名
@TableName("admin")
@Schema(description = "管理员实体类")
public class Admin {
    //@TableId这个注解可以指定主键的类型，默认是自增
    @TableId(type = IdType.AUTO)
    private Long id;

    //Schema这个注解可以指定属性的描述，默认是属性名的小写，如果是属性名的小写，就不需要指定描述，接口文档
    @Schema(description = "用户名")
    private String username;

    private String userpwd;
    private String name;
    private String sex;
    private String tel;
    private String headurl;

    /**
     * 验证码id
     */
    @Schema(description = "验证码ID")
    @TableField(exist = false)
    private String captchaId;

    /**
     * 验证码
     */
    @Schema(description = "验证码")
    @TableField(exist = false)
    private String captchaCode;

    /**
     * 登录token
     */
    @Schema(description = "登录token")
    @TableField(exist = false)
    private String token;
}