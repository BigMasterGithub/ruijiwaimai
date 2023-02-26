package com.itheima.reggie.common;

/**
 * @author 张壮
 * @description 保存获取当前用户id
 * @since 2023/2/26 14:17
 **/
public class BaseContext {
    //每个线程保存一个副本
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }

}

