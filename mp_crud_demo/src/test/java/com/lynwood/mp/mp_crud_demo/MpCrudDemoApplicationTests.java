package com.lynwood.mp.mp_crud_demo;

import com.lynwood.mp.mp_crud_demo.business.entity.User;
import com.lynwood.mp.mp_crud_demo.business.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MpCrudDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void simplenessMapperCURD() {
        //增加
        User addUser = new User();
        addUser.setAge(18);
        addUser.setEmail("wunian_@hotmail.com");
        addUser.setName("Lynwood");
        userMapper.insert(addUser); // insert 之后是将id装配到实体对象里的
        System.out.println("add:\n" + addUser);
        // User(id=1082883152404103169, name=Lynwood, age=18, email=wunian_@hotmail.com)
        // id = 1082883152404103169  之所以这么长是因为  MP底层给我们自己以uuid 的形式添加了 user对象的id属性
        // 更改yaml 全局策略为AUTO 并重置自增序列 输出 User(id=1, name=Lynwood, age=18, email=wunian_@hotmail.com)

        //修改
        User updateUser = new User();
        updateUser.setId(1082883152404103169L);
        updateUser.setName("ok?");
        userMapper.updateById(updateUser);
        System.out.println("update:\n" + updateUser);
        //  User(id=1082883152404103169, name=ok?, age=null, email=null)
        // 刷新数据库 更改成功 但是没有讲其他对象进行装配

        //查询
        User selectUser = userMapper.selectById(1082883152404103169L);
        System.out.println("select:\n" + selectUser);
        //User(id=1082883152404103169, name=ok?, age=18, email=wunian_@hotmail.com)

        //删除
        int i = userMapper.deleteById(1082883152404103169L);
        if (i==1){
            System.out.println("delete:\n" + "删除成功!");
            //刷新库 删除成功
        }
    }
    @Test
    public void batchMapperCURD() {
        // 多id 查询
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        idList.add(3L);
        List<User> userList = userMapper.selectBatchIds(idList);// id的多个查询
        System.out.println("selectBatch:" );
        userList.forEach(System.out::println);
        //User(id=1, name=Lynwood, age=18, email=wunian_@hotmail.com)
        //User(id=3, name=Jack, age=20, email=test2@baomidou.com)

        // 多条件  查询
        Map<String,Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("age",18);
        stringObjectMap.put("id",2);
        List<User> selectByMap = userMapper.selectByMap(stringObjectMap);// 字段-值 键值对集合 作为 '且' 关系
        System.out.println("selectByMap:" );
        selectByMap.forEach(System.out::println);
        //User(id=2, name=Jone, age=18, email=test1@baomidou.com)

        // 多id 删除
        int deleteCount = userMapper.deleteBatchIds(idList);// id的多个删除
        System.out.println("deleteBatch:\n"+ deleteCount );
        // 2

        // 多条件 删除
        int deleteCount2 = userMapper.deleteByMap(stringObjectMap);// id的多个查询
        System.out.println("deleteByMap:\n"+ deleteCount2 );
        // 1
    }
    @Test
    public void myInjectorMapperCURD() {
        userMapper.clearTable();
    }



}

