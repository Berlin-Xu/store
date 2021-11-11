package com.usst.store.mapper;

import com.usst.store.entity.Cart;
import com.usst.store.entity.District;
import com.usst.store.vo.CartVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

// 标注当前的类是一个测试类，不会随同项目一块打包发送
@SpringBootTest
// @RunWith：表示启动这个单元测试类，需要传递一个SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class CartMapperTests {

    @Autowired
    private CartMapper cartMapper;

    @Test
    public void insert() {
        Cart cart = new Cart();
        cart.setUid(6);
        cart.setPid(10000002);
        cart.setNum(3);
        cart.setPrice(1000L);
        Integer rows = cartMapper.insert(cart);
        System.out.println("rows=" + rows);
    }

    @Test
    public void updateNumByCid() {
        Integer cid = 1;
        Integer num = 10;
        String modifiedUser = "购物车管理员";
        Date modifiedTime = new Date();
        Integer rows = cartMapper.updateNumByCid(cid, num, modifiedUser, modifiedTime);
        System.out.println("rows=" + rows);
    }

    @Test
    public void findByUidAndPid() {
        Integer uid = 6;
        Integer pid = 10000002;
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        System.out.println(result);
    }

    @Test
    public void findVOByUid() {
        List<CartVO> list = cartMapper.findVOByUid(6);
        System.out.println(list);
    }

    @Test
    public void findByCid() {
        Cart cart = cartMapper.findByCid(3);
        System.out.println(cart);
    }

}
