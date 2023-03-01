package com.itheima.reggie.common;

import io.swagger.annotations.*;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果，服务端响应的数据最终都会封装成此对象
 *
 * @param <T>
 */
@Data
@ApiModel(value = "返回结果")
public class Result<T> {
    @ApiModelProperty(value = "编码：1成功，0和其它数字为失败")
    private Integer code; //编码：1成功，0和其它数字为失败
    @ApiModelProperty(value = "错误信息")
    private String msg; //错误信息
    @ApiModelProperty(value = "数据")
    private T data; //数据
    @ApiModelProperty(value = "动态数据")
    private Map map = new HashMap(); //动态数据

    @ApiOperation(value = "成功返回给前端数据接口")

    @ApiImplicitParams({
            @ApiImplicitParam(name = "object", value = "返回前端的数据", required = true)

    })
    public static <T> Result<T> success(T object) {
        Result<T> r = new Result<T>();
        r.data = object;
        r.code = 1;
        return r;
    }


    @ApiOperation(value = "未成功返回给前端数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msg", value = "错误原因", required = true)

    })
    public static <T> Result<T> error(String msg) {
        Result r = new Result();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    @ApiOperation(value = "添加 动态数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "key", required = true),
            @ApiImplicitParam(name = "value", value = "value", required = true)
    })
    public Result<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
