package com.lynwood.mp.mp_plugin_demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lynwood.mp.mp_plugin_demo.business.entity.User;
import com.lynwood.mp.mp_plugin_demo.business.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MpPluginDemoApplicationTests {
    @Autowired
    private UserMapper userMapper;
    /**
     * 测试分页插件
     */
    @Test
    public void test_page() {
        IPage<User> userIPage = userMapper.selectPage(
                new Page<User>()
                        .setCurrent(1) //设置当前查询页
                        .setSize(3) // 设置每页条数
                        .setDesc("age"),//使用page 进行排序
                new QueryWrapper<User>()
                        .lambda()
                        .likeRight(User::getEmail, "test")
                        .select(User::getId, User::getName, User::getAge)
        );

        List<User> records = userIPage.getRecords();
        records.forEach(System.out::println);
        /**
         * ==>  Preparing: SELECT id,name,age FROM user WHERE email LIKE ? ORDER BY age DESC LIMIT ?,?
         * ==> Parameters: test%(String), 0(Long), 3(Long)
         */
    }
    /**
     * 测试逻辑删除插件
     */
    @Test
    public void test_logic_delete() {
        userMapper.delete(new QueryWrapper<User>()
                .lambda()
                .notLike(User::getEmail, "test")
                .select(User::getId, User::getName, User::getAge));
        /**
         * ==>  Preparing: UPDATE user SET del=1 WHERE del=0 AND email NOT LIKE ?
         * ==> Parameters: %test%(String)
         */
        //那么查询呢?
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
        //发现查询也帮我们设置了过滤字段  del = 0
        /**
         * ==>  Preparing: SELECT id,name,age,email,del,version FROM user WHERE del=0
         * ==> Parameters:
         */

        //那么我想查出这个删除的用户 更改为 正常呢?
        List<Map<String, Object>> mapList = userMapper.selectMaps(
                new QueryWrapper<User>()
                        .lambda()
                        .eq(User::getDel, 1)
                        .select(User.class,i -> false)
        );
        Assert.assertNotNull(mapList);
        Assert.assertEquals(mapList.size(),1);
        /**
         * java.lang.AssertionError:
         * Expected :0
         * Actual   :1
         */
        // 失败了
        // 询问MP开发者后 开发者说明 因为大部分情况在删除之后不会恢复 所以没有设定相关恢复或者跳过 当前 筛选的 接口

    }

    /**
     * 测试乐观锁插件
     */
    @Test
    public void test_Optimistic_Locking() throws InterruptedException {
        User oldUser = userMapper.selectById(1);
        oldUser.setAge(19);
        System.out.println("模拟中途有人更改"+ "begin....");
        Thread.sleep(10000);
        System.out.println("模拟中途有人更改"+ "end....");
        if(userMapper.updateById(oldUser)==1){
            System.out.println("success \n"+ oldUser);
            //success
            //User(id=1, name=Jone, age=19, email=test1@test.com, del=0, version=2)
        }else {
            System.out.println("fail \n");
            //==>  Preparing: UPDATE user SET name=?, age=?, email=?, version=? WHERE id=? AND version=? AND del=0
            //==> Parameters: Jone(String), 19(Integer), test1@test.com(String), 3(Integer), 1(Long), 2(Integer)
            //<==    Updates: 0
            //fail
        }
    }



}
