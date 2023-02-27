package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;

/**
 * @author 张壮
 * @description 菜 服务
 * @since 2023/2/26 14:45
 **/

public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);


    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);

}

