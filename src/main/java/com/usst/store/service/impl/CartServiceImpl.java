package com.usst.store.service.impl;

import com.usst.store.entity.Cart;
import com.usst.store.mapper.CartMapper;
import com.usst.store.mapper.ProductMapper;
import com.usst.store.service.ICartService;
import com.usst.store.service.ex.AccessDeniedException;
import com.usst.store.service.ex.CartNotFoundException;
import com.usst.store.service.ex.InsertException;
import com.usst.store.service.ex.UpdateException;
import com.usst.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    /**
     * 购物车的业务层依赖于购物车的持久层以及商品的持久层
     */
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {

        // 查询当前要添加的这个商品是否在购物车中已经存在
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        Date date = new Date();
        if (result == null) {
            // 表示商品没有添加到购物车中，insert
            // 创建一个cart对象
            Cart cart = new Cart();
            // 补全数据:参数提供的部分数据
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            // 补全价格，来自商品表查询的数据
            Long price = productMapper.findById(pid).getPrice();
            cart.setPrice(price);
            // 补全四个日志
            cart.setCreatedUser(username);
            cart.setCreatedTime(date);
            cart.setModifiedUser(username);
            cart.setModifiedTime(date);
            Integer rows = cartMapper.insert(cart);
            if (rows != 1) {
                throw new InsertException("插入时发生未知的错误");
            }
        }else {
            // 商品被添加过，update
            Integer num = result.getNum() + amount;
            Integer rows = cartMapper.updateNumByCid(result.getCid(), num, username, date);
            if (rows != 1) {
                throw new UpdateException("更新时发生未知的错误");
            }
        }
    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        return cartMapper.findVOByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if (result == null) {
            throw new CartNotFoundException("数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("数据非法访问");
        }
        Integer num = result.getNum() + 1;
        Integer rows = cartMapper.updateNumByCid(cid, num, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据出现异常");
        }
        return num;
    }

    @Override
    public List<CartVO> getVOByCids(Integer uid, Integer[] cids) {
        List<CartVO> list = cartMapper.findVOByCids(cids);
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).getUid().equals(uid)) {
                CartVO cartVO = list.remove(i);
                System.out.println(cartVO + "不属于用户");
            }
        }
        return list;
    }
}
