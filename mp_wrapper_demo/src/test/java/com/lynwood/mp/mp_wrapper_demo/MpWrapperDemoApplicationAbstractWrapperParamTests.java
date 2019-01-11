package com.lynwood.mp.mp_wrapper_demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lynwood.mp.mp_wrapper_demo.business.entity.User;
import com.lynwood.mp.mp_wrapper_demo.business.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

/**
 *  `boolean condition` 、` R column`和 `Function<This, This> func` 三个参数相关测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MpWrapperDemoApplicationAbstractWrapperParamTests {

    @Autowired
    private UserMapper userMapper;

    /**
     *  测试 boolean condition
     *
     */
    @Test
    public void abstractWrapperTest_Condition() {
        Integer age = new Random().nextInt(25);
        System.out.println("年龄: " + age);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        boolean condition = age<=18;
        userQueryWrapper.ne(condition,"name","Lynwood");
        List<User> userList = userMapper.selectList(userQueryWrapper);
        userList.forEach(System.out::println);
        /**
         * 输出:
         年龄: 12
         ==>  Preparing: SELECT id,name,age,email FROM user WHERE name <> ?
         ==> Parameters: Lynwood(String)

         年龄: 21
         ==>  Preparing: SELECT id,name,age,email FROM user
         ==> Parameters:
         */
    }

    /**
     *  测试 R column
     *
     */
    @Test
    public void abstractWrapperTest_Column() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
//        userQueryWrapper.ne("name","Lynwood");
        userQueryWrapper.lambda().eq(User::getName,"Lynwood");

        List<User> userList = userMapper.selectList(userQueryWrapper);
        userList.forEach(System.out::println);
        /**
         * 输出:
         年龄: 12
         ==>  Preparing: SELECT id,name,age,email FROM user WHERE name <> ?
         ==> Parameters: Lynwood(String)

         年龄: 21
         ==>  Preparing: SELECT id,name,age,email FROM user
         ==> Parameters:
         */
    }

    /**
     *  测试 R func
     *
     */
    @Test
    public void abstractWrapperTest_Func() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.ne("name","Lynwood");
        userQueryWrapper.or(new Function<QueryWrapper<User>, QueryWrapper<User>>() {
            @Override
            public QueryWrapper<User> apply(QueryWrapper<User> userQueryWrapper) {
                return userQueryWrapper.eq("age", "18");
            }
        });
        // 等同与
        // userQueryWrapper.ne("name","Lynwood")
        //                        .or(i -> i.eq("age", "18"));
        List<User> userList = userMapper.selectList(userQueryWrapper);
        userList.forEach(System.out::println);
    }


}
