package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")

public class UserController {
    @Resource
    UserMapper userMapper;

    @PostMapping
    public Result<?> save(@RequestBody User user){
        if(user.getPassword() == null){
            user.setPassword("123456");
        }
        userMapper.insert(user);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody User user){
        userMapper.updateById(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id){
        userMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<User> wapper = Wrappers.<User>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wapper.like(User::getNickName,search);
        }
        return Result.success(  userMapper.selectPage(new Page<>(pageNum, pageSize),wapper));
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, user.getUsername())
                .eq(User::getPassword,user.getPassword()));

        if(res == null){
            return Result.error("-1","Invalid Username or Password");
        }
        if(user.getPassword() == null){
            user.setPassword("123456");
        }
        userMapper.insert(user);
        return Result.success();
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user){
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, user.getUsername()));

        if(res != null){
            return Result.error("-1","Username already exist");
        }

        return Result.success();
    }
}
