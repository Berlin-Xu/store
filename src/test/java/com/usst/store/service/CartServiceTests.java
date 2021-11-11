package com.usst.store.service;

import com.usst.store.entity.District;
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
public class CartServiceTests {

    @Autowired
    private ICartService cartService;

    @Test
    public void addToCart() {
        cartService.addToCart(6,10000007,5,"李四");
    }

    @Test
    public void getVOByCids() {
        Integer[] cids = {2, 3, 4};
        Integer uid = 6;
        List<CartVO> list = cartService.getVOByCids(uid, cids);
        System.out.println("count=" + list.size());
        for (CartVO item : list) {
            System.out.println(item);
        }
    }
}
