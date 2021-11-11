package com.usst.store.service;

import com.usst.store.entity.Order;
import com.usst.store.mapper.OrderMapper;
import com.usst.store.vo.CartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

// 标注当前的类是一个测试类，不会随同项目一块打包发送
@SpringBootTest
// @RunWith：表示启动这个单元测试类，需要传递一个SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class OrderServiceTests {

    @Autowired
    private IOrderService orderService;

    @Test
    public void create() {
        Integer[] cids = {2,3,4};
        Order order = orderService.create(13, cids, 6, "王五2");
        System.out.println(order);
    }

}
