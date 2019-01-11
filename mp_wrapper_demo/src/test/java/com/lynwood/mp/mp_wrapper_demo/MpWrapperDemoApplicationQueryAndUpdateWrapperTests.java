package com.lynwood.mp.mp_wrapper_demo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lynwood.mp.mp_wrapper_demo.business.entity.User;
import com.lynwood.mp.mp_wrapper_demo.business.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * UpdateWrapper和QueryWrapper 接口测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MpWrapperDemoApplicationQueryAndUpdateWrapperTests {

    @Autowired
    private UserMapper userMapper;

    /**
     *  测试 QueryWrapper.select()
     *
     */
    @Test
    public void abstractWrapperTest_Select() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
//        userQueryWrapper.eq("age",18)
//                .select("id","name");
        userQueryWrapper.eq("age",18)
                .select(User.class,i -> false);//只返回id
        List<User> userList = userMapper.selectList(userQueryWrapper);
            // SELECT id FROM user WHERE age = ?
        userList.forEach(System.out::println);

        //前方有坑 注意: 注意!!!!!!!!!!!

        //关于LambdaQueryWrapper的使用这样用是没问题的
        LambdaQueryWrapper<User> lambdaQueryWrapper = new QueryWrapper<User>().lambda();
        lambdaQueryWrapper.select(User::getId,User::getAge);
        List<User> userList1 = userMapper.selectList(lambdaQueryWrapper);
            //SELECT id,age FROM user
        userList1.forEach(System.out::println);

        // 这样设置是无效的!!!!
        QueryWrapper<User> userQueryWrapper2 = new QueryWrapper<>();
        userQueryWrapper2.lambda().select(User::getId,User::getAge);
        List<User> userList2 = userMapper.selectList(userQueryWrapper2);
            // SELECT id,name,age,email FROM user
        userList2.forEach(System.out::println);
    }

    /**
     *  测试 UpdateWrapper.set()
     *  测试 UpdateWrapper.setSql()
     */
    @Test
    public void abstractWrapperTest_Set() {
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        // 更新id==11 的age 为19
        userUpdateWrapper.set("age",19).eq("id",11);
        // 附加更新内容 name 设置
        User user = new User();
        user.setName("Lynwood1");
        userMapper.update(user,userUpdateWrapper);
        /**
         *          ==>  Preparing: UPDATE user SET name=?, age=? WHERE id = ?
         *          ==> Parameters: Lynwood1(String), 19(Integer), 11(Integer)
         *          <==    Updates: 1
         */

        UpdateWrapper<User> userUpdateWrapper1 = new UpdateWrapper<>();
        userUpdateWrapper1.setSql("name='wunian7yulian'").eq("id",11);
        // 附加更新内容为空时  不能传入null  需要传入空实体对象
        userMapper.update(new User(),userUpdateWrapper1);
        /**
         *      ==>  Preparing: UPDATE user SET name='wunian7yulian' WHERE id = ?
         *      ==> Parameters: 11(Integer)
         *      <==    Updates: 1
         */
    }

}
