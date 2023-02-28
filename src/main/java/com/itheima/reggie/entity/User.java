package com.itheima.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 用户信息
 */
@Data
@ApiModel(value = "用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "主键")
    private Long id;


   @ApiModelProperty(value = " 姓名")
    private String name;


     
    @ApiModelProperty(value = " 手机号")
    private String phone;


     
    @ApiModelProperty(value = " 性别 0 女 1 男")
    private String sex;


     
    @ApiModelProperty(value = " 身份证号")
    private String idNumber;


     
    @ApiModelProperty(value = "头像")
    private String avatar;


     
    @ApiModelProperty(value = " 状态 0:禁用，1:正常")
    private Integer status;

}
