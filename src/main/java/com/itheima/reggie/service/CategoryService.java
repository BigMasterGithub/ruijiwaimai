package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Employee;
import org.springframework.stereotype.Service;

/**
 * @author 张壮
 * @description TODO
 * @since 2023/2/26 14:45
 **/

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}

