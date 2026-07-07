package com.jxl.studentmanger.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("admin")
@Schema(description = "管理员实体类")
public class Admin {
    @TableId(type = IdType.AUTO)
    private Long id;

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