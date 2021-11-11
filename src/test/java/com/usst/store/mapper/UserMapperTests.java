package com.usst.store.mapper;

import com.usst.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

// 标注当前的类是一个测试类，不会随同项目一块打包发送
@SpringBootTest
// @RunWith：表示启动这个单元测试类，需要传递一个SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class UserMapperTests {

    // idea有检测功能，接口不能直接创建对象
    @Autowired
    private UserMapper userMapper;

    /**
     * 特点：@Test修饰，返回值void，参数列表不指定，访问修饰符public
     */
    @Test
    public void insert() {
        User user = new User();
        user.setUsername("lili");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByUserName() {
        User user = userMapper.findByUserName("lili");
        System.out.println(user);
    }

    @Test
    public void updatePasswordByUid() {
        userMapper.updatePasswordByUid(5,"111","管理员",new Date());
    }

    @Test
    public void findByUid() {
        System.out.println(userMapper.findByUid(5));
    }

    @Test
    public void updateInfoByUid() {
        User user = new User();
        user.setUid(5);
        user.setPhone("17858802222");
        user.setEmail("admin@cy.com");
        user.setGender(1);
        user.setModifiedUser("系统管理员");
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        System.out.println("rows=" + rows);
    }

    @Test
    public void updateAvatarByUid() {
        userMapper.updateAvatarByUid(6,"/upload/avatar.png","系统管理员",new Date());
    }
}
