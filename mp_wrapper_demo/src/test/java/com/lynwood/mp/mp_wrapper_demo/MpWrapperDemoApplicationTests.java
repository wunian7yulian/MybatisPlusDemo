package com.lynwood.mp.mp_wrapper_demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lynwood.mp.mp_wrapper_demo.business.entity.User;
import com.lynwood.mp.mp_wrapper_demo.business.mapper.UserMapper;
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
public class MpWrapperDemoApplicationTests {
    @Autowired
    private UserMapper userMapper;

    /**
     *  测试 allEq()  等同于 WHERE name = ? AND age = ?
     *  +
     *  使用 selectList() 返回多个指定类型对象 的集合
     *
     */
    @Test
    public void abstractWrapperTest_allEq() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        HashMap<String,Object> param = new HashMap<>();
        param.put("name","Lynwood");
        param.put("age","18");
        userQueryWrapper.allEq(param);
        List<User> userList = userMapper.selectList(userQueryWrapper);
        userList.forEach(System.out::println);
        /**
         * 输出:
         *    ==>  Preparing: SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
         *    ==> Parameters: Lynwood(String), 18(String)
         */
    }

    /**
     *  测试 eq()  等同于 WHERE name = ?
     *  +
     *  使用selectOne()
     *      当返回多条会报错
     */
    @Test
    public void abstractWrapperTest_eq() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("name","Lynwood");
        User user = userMapper.selectOne(userQueryWrapper);
        System.out.println(user);
        /**
         * 输出:
         *           Preparing: SELECT id,name,age,email FROM user WHERE name = ?
         *           Parameters: Lynwood(String)
         */
    }

    /**     not equals 缩写 ~~
     *
     *  测试 ne()  等同于 WHERE age <> ?
     *  +
     *  使用 selectObjs() 返回多个 非指定类型对象 的集合
     *
     */
    @Test
    public void abstractWrapperTest_ne() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.ne("age", 18);
        List<Object> objectList = userMapper.selectObjs(userQueryWrapper);
        objectList.forEach(System.out::println);
        /**
         * 输出:
         *    ==>  Preparing: SELECT id,name,age,email FROM user WHERE age <> ?
         *      ==> Parameters: 18(Integer)
         */
    }

    /***************************************************** 多接口连用 * AND 关系*******************************/
    /**     测试
     *      gt() : greater than       等同于     >
     *      ge() : greater equals     等同于     >=
     *      lt() : less than          等同于     <
     *      le() : less equals        等同于     <=
     *
     *  使用 selectMaps() 返回多个 指定到Map做封装 的集合
     *
     */
    @Test
    public void abstractWrapperTest_gt_ge_lt_le() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.ge("age", 21);
        userQueryWrapper.le("age", 24);
        List<Map<String, Object>> mapList = userMapper.selectMaps(userQueryWrapper);
        mapList.forEach(System.out::println);
        /**
         * 输出:
         *      ==>  Preparing: SELECT id,name,age,email FROM user WHERE age >= ? AND age <= ?
         *      ==> Parameters: 21(Integer), 24(Integer)
         */
    }

    /**
     *  测试
     *      between()       等同于 WHERE age between ? and ?
     *      notBetween()    等同于 WHERE age not between ? and ?
     *  +
     *  使用 selectList() 返回多个 非指定类型对象 的集合
     *
     */
    @Test
    public void abstractWrapperTest_between() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .between("age", 18,24)
                .notBetween("id",0,11);// 支持链式调用

        List<User> userList = userMapper.selectList(userQueryWrapper);
        userList.forEach(System.out::println);
        /**
         * 输出:
                 ==>  Preparing: SELECT id,name,age,email FROM user WHERE age BETWEEN ? AND ? AND id NOT BETWEEN ? AND ?
                 ==> Parameters: 18(Integer), 24(Integer), 0(Integer), 11(Integer)
         */
    }

    /**
     *  测试
     *      like()              等同于 LIKE '%?%'
     *      notLike()           等同于 NOT LIKE '%?%'
     *      likeLeft()          等同于 LIKE '%?'
     *      likeRight()         等同于 LIKE '?%'
     *  +
     *  使用 selectCount()   返回查询到的条数
     */
    @Test
    public void abstractWrapperTest_like() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .like("email", "test")
                .notLike("email","test4")
                .likeRight("name","J") // J%
                .likeLeft("name","e"); //%e
        Integer selectCount = userMapper.selectCount(userQueryWrapper);
        System.out.println(selectCount);
        /**
         * 输出:
                 ==>  Preparing: SELECT id,name,age,email FROM user WHERE email LIKE ? AND email NOT LIKE ? AND name LIKE ? AND name LIKE ?
                 ==> Parameters: %test%(String), %test4%(String), J%(String), %e(String)
         */
    }

    /**
     *  测试
     *      null()              等同于 is null
     *      isNotNull()         等同于 is not null
     *
     */
    @Test
    public void abstractWrapperTest_null() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
               .isNotNull("name");
        Integer selectCount = userMapper.selectCount(userQueryWrapper);
        System.out.println(selectCount);
        /**
         * 输出:
             ==>  Preparing: SELECT COUNT(1) FROM user WHERE name IS NOT NULL
             ==> Parameters:
             <==    Columns: COUNT(1)
         */
    }

    /**
     *  测试
     *      in()              等同于 IN (?,?)
     *      notIn()           等同于 NOT IN (?,?)
     *      inSql()           等同于 IN (sql)
     *      notInSql()        等同于 NOT IN (sql)
     */
    @Test
    public void abstractWrapperTest_in() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        List<Long> idLists = new ArrayList<>();
        idLists.add(11L);
        idLists.add(13L);
        userQueryWrapper
               // .in("id",idLists);
                .inSql("id","select id from user where id<=15");
        List<User> userList = userMapper.selectList(userQueryWrapper);
        userList.forEach(System.out::println);
        /**
         * 输出:
                 ==>  Preparing: SELECT id,name,age,email FROM user WHERE id IN (select id from user where id<=15)
                 ==> Parameters:
         */
    }

    /**
     *  测试
     *      groupBy() .. 支持多参数    等同于 GROUP BY age,name,id
     */
    @Test
    public void abstractWrapperTest_group() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .groupBy("age","name","id");
        List<User> userList = userMapper.selectList(userQueryWrapper);
        userList.forEach(System.out::println);
        /**
         * 输出:
             ==>  Preparing: SELECT id,name,age,email FROM user GROUP BY age,name,id
             ==> Parameters:
         */
    }

    /**
     *  测试
     *      orderBy()     .. 支持多参数    等同于 关于 condition
     *      orderByDesc() .. 支持多参数    等同于 ORDER BY age DESC , id DESC
     *      orderByAsc()  .. 支持多参数    等同于 ORDER BY age ASC , id ASC
     */
    @Test
    public void abstractWrapperTest_order() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .orderByAsc("age","id");
        List<User> userList = userMapper.selectList(userQueryWrapper);
        userList.forEach(System.out::println);
        /**
         * 输出:
             ==>  Preparing: SELECT id,name,age,email FROM user ORDER BY age ASC , id ASC
             ==> Parameters:
         */
    }

    /**
     *  测试
     *      having()     .. 支持多参数    等同于 HAVING sum(id)> ?
     */
    @Test
    public void abstractWrapperTest_having() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .groupBy("age")
                .having("sum(id)>{0}",20);// 替换里面的参数
        List<User> userList = userMapper.selectList(userQueryWrapper);
        userList.forEach(System.out::println);
        /**
         * 输出:
                 ==>  Preparing: SELECT id,name,age,email FROM user GROUP BY age HAVING sum(id)>?
                 ==> Parameters: 20(Integer)
         */
    }

    /**
     *  测试
     *      or()       等同于 or
     *      and()       等同于 and
     */
    @Test
    public void abstractWrapperTest_or_and() {
        // 简单 or 和and
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .eq("id",11)
                .or()  // 默认and()
//                .and() // 两个不能连用
                .eq("id",13);
        List<User> userList = userMapper.selectList(userQueryWrapper);
        userList.forEach(System.out::println);
        /**
         * 输出:
                 ==>  Preparing: SELECT id,name,age,email FROM user WHERE id = ? OR id = ?
                 ==> Parameters: 11(Integer), 13(Integer)
         */

        // 嵌套的 or 和and
        QueryWrapper<User> userQueryWrapper1 = new QueryWrapper<>();
        userQueryWrapper1
                .eq("id",14)
                .or(i -> i.eq("name", "Lynwood").ne("age", "18"));
        List<User> userList1 = userMapper.selectList(userQueryWrapper1);
        userList1.forEach(System.out::println);
        /**输出:
         *
         * ==>  Preparing: SELECT id,name,age,email FROM user WHERE id = ? OR ( name = ? AND age <> ? )
         * ==> Parameters: 14(Integer), Lynwood(String), 18(String)
         */
        /**!!!!!!!!!!!!  .or(i -> i.eq("name", "Lynwood").ne("age", "18"));  解释  见下文发现问题*/
    }

    /**
     *  测试
     *      apply()    动态传参防止 sql注入    占位符 替换 以值的形式添加
     */
    @Test
    public void abstractWrapperTest_apply() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
               .apply("id>{0}",13);
        List<User> userList = userMapper.selectList(userQueryWrapper);
        userList.forEach(System.out::println);
        /**
         * 输出:
                 ==>  Preparing: SELECT id,name,age,email FROM user WHERE id>?
                 ==> Parameters: 13(Integer)
         */
    }

    /**
     *  测试
     *      last()   在sql 最后追加
     *
     *      最常用 last("limit 1")
     */
    @Test
    public void abstractWrapperTest_last() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
               .last("limit 2");
        List<User> userList = userMapper.selectList(userQueryWrapper);
        userList.forEach(System.out::println);
        /**
         * 输出:
                 ==>  Preparing: SELECT id,name,age,email FROM user limit 2
                 ==> Parameters:
         */
    }

    /**
     *  测试
     *      exists()  拼接 EXISTS ( sql语句 )
     *      notExists()  拼接 NOT EXISTS ( sql语句 )
     */
    @Test
    public void abstractWrapperTest_exists() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .exists("SELECT id,name,age,email FROM user where id= 222");
        List<User> userList = userMapper.selectList(userQueryWrapper);
        userList.forEach(System.out::println);
        /**
         * 输出:
                 ==>  Preparing: SELECT id,name,age,email FROM user WHERE EXISTS (SELECT id,name,age,email FROM user where id= 222)
                 ==> Parameters:
                 <==      Total: 0
         */
    }

    /**
     *  测试
     *      nested()  正常嵌套  (无 or and 模式嵌套)
     */
    @Test
    public void abstractWrapperTest_nested() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .nested(i -> i.eq("name", "Lynwood").eq("age", "18"));
        List<User> userList = userMapper.selectList(userQueryWrapper);
        userList.forEach(System.out::println);
        /**
         * 输出:
                 ==>  Preparing: SELECT id,name,age,email FROM user WHERE ( name = ? AND age = ? )
                 ==> Parameters: Lynwood(String), 18(String)
         */
    }



}

