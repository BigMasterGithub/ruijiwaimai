package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;
import com.itheima.reggie.utils.AAAConstans;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 张壮
 * @description TODO
 * @since 2023/2/26 14:44
 **/
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    public CategoryService categoryService;
    @Autowired
    public DishService dishService;
    @Autowired
    public SetmealService setmealService;



    @PutMapping
    public Result<String> update(@RequestBody Category category){
        log.info("修改分类信息 : {}",category);
        categoryService.updateById(category);
        return Result.success("修改成功!");
    }


    /**
     * 根据 id 删除,注意当前id下有商品时,不能删除
     * @param id
     * @return
     */
    @DeleteMapping
    public Result<String> delete(Long id) {
        Category byId = categoryService.getById(id);

        categoryService.remove(id);

        return Result.success("删除成功!");

    }

    /**
     * 增加
     * @param category
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody Category category) {

        log.info("category:{}", category);
        categoryService.save(category);

        return Result.success("新增分类成功!");
    }
    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(@RequestParam(value = "page") int page,
                             @RequestParam(value = "pageSize") int size) {

        Page pageInfo = new Page(page, size);

        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();

        //按照 更新排序
        queryWrapper.orderByDesc(Category::getSort);

        //执行
        categoryService.page(pageInfo, queryWrapper);

        return Result.success(pageInfo);
    }
}

