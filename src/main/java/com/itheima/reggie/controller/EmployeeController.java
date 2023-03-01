package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.Result;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import com.itheima.reggie.utils.SystemConstans;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 根据id查询 employee
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id) {
        Employee emplo = employeeService.getById(id);
        return Result.success(emplo);
    }

    /**
     * 员工登录
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        //1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2、根据页面提交的用户名username查询数据库
     /*   HashMap<String,String> map = new HashMap<>();
        map.put("username",employee.getUsername());
        employeeService.query().allEq(map).one();*/

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());


        Employee emp = employeeService.getOne(queryWrapper);

        //3、如果没有查询到则返回登录失败结果
        if (emp == null) {
            return Result.error("登录失败");
        }

        //4、密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)) {
            return Result.error("登录失败");
        }

        //5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return Result.error("账号已禁用");
        }

        //6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return Result.success(emp);
    }

    /**
     * 员工退出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        //清理Session中保存的当前登录员工的id
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }

    /**
     * 添加员工
     *
     * @Param request
     * @Param employee
     * @Return: com.itheima.reggie.common.Result<java.lang.String>
     **/
    @PostMapping
    public Result<String> addEmployee(HttpServletRequest request, @RequestBody Employee employee) {
        // 设置默认密码 :  123456
        employee.setPassword(DigestUtils.md5DigestAsHex(SystemConstans.DEFAULT_PASS.getBytes()));

        employeeService.save(employee);

        return Result.success("成功!");
    }


    /**
     * 分页查询
     * Page类是Mybatis提供的
     *
     * @Return: com.itheima.reggie.common.Result<com.baomidou.mybatisplus.extension.plugins.pagination.Page < com.itheima.reggie.entity.Employee>>
     **/
    @GetMapping("/page")
    public Result<Page> page(@RequestParam(value = "page") int page,
                             @RequestParam(value = "pageSize") int size,
                             String name) {

        Page pageInfo = new Page(page, size);

        //条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //like
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //按照 更新排序
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行
        employeeService.page(pageInfo, queryWrapper);

        return Result.success(pageInfo);


    }

    /**
     * 根据 id 修改员工信息
     *
     * @param request
     * @param emplyee
     * @return
     */
    @PutMapping
    public Result<String> update(HttpServletRequest request, @RequestBody Employee emplyee) {


        employeeService.updateById(emplyee);
        return Result.success("更改成功");
    }
}
