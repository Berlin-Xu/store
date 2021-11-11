package com.usst.store.service;

import com.usst.store.entity.User;
import com.usst.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// 标注当前的类是一个测试类，不会随同项目一块打包发送
@SpringBootTest
// @RunWith：表示启动这个单元测试类，需要传递一个SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class UserServiceTests {

    @Autowired
    private IUserService userService;

    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("zhangsan");
            user.setPassword("123");
            userService.reg(user);
        } catch (ServiceException e) {
            System.out.println(e.getMessage() + ":" + e.getClass().getSimpleName());
        }
    }

    @Test
    public void login() {
        User user = userService.login("tom", "123");
        System.out.println(user);
    }

    @Test
    public void changePassword() {
        userService.changePassword(6,"管理员","123","111");
    }

    @Test
    public void getByUid() {
        User user = userService.getByUid(5);
        System.out.println(user);
    }

    @Test
    public void changeInfo() {
        User user = new User();
        user.setPhone("1234567890");
        user.setEmail("123@qq.com");
        user.setGender(0);
        userService.changeInfo(5,"管理员",user);
    }

    @Test
    public void changeAvatar() {
        userService.changeAvatar(6,"/upload/test.png","李四");
    }
}
