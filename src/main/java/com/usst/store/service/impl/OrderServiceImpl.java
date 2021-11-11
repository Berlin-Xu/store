package com.usst.store.service.impl;

import com.usst.store.entity.Address;
import com.usst.store.entity.Order;
import com.usst.store.entity.OrderItem;
import com.usst.store.mapper.OrderMapper;
import com.usst.store.service.IAddressService;
import com.usst.store.service.ICartService;
import com.usst.store.service.IOrderService;
import com.usst.store.service.ex.InsertException;
import com.usst.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private ICartService cartService;

    @Override
    public Order create(Integer aid, Integer[] cids, Integer uid, String username) {
        // 即将下单的列表
        List<CartVO> list = cartService.getVOByCids(uid, cids);
        // 计算产品的总价
        Long totalPrice = 0L;
        for (CartVO c : list) {
            totalPrice += c.getRealPrice() * c.getNum();
        }
        Address address = addressService.getByAid(aid, uid);
        Order order = new Order();
        order.setUid(uid);
        // 补全收货地址的信息
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        // 补全购物车信息
        order.setTotalPrice(totalPrice);
        order.setStatus(0);
        order.setOrderTime(new Date());
        // 日志
        order.setCreatedTime(new Date());
        order.setCreatedUser(username);
        order.setModifiedTime(new Date());
        order.setModifiedUser(username);
        // 插入数据
        Integer rows = orderMapper.insertOrder(order);
        if (rows != 1) {
            throw new InsertException("插入时发生未知异常");
        }

        // 创建订单详细项的数据
        for (CartVO cartVO : list) {
            // 创建一个订单项数据对象
            OrderItem orderItem = new OrderItem();
            orderItem.setOid(order.getOid());
            orderItem.setPid(cartVO.getPid());
            orderItem.setTitle(cartVO.getTitle());
            orderItem.setImage(cartVO.getImage());
            orderItem.setNum(cartVO.getNum());
            orderItem.setPrice(cartVO.getPrice());
            // 日志
            orderItem.setCreatedTime(new Date());
            orderItem.setCreatedUser(username);
            orderItem.setModifiedTime(new Date());
            orderItem.setModifiedUser(username);
            Integer rows2 = orderMapper.insertOrderItem(orderItem);
            if (rows2 != 1) {
                throw new InsertException("插入订单商品数据时出现未知错误，请联系系统管理员");
            }
        }
        return order;
    }
}
