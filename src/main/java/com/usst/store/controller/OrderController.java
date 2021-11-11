package com.usst.store.controller;


import com.usst.store.entity.Order;
import com.usst.store.service.ICartService;
import com.usst.store.service.IOrderService;
import com.usst.store.util.JsonResult;
import com.usst.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController extends BaseController{

    @Autowired
    private IOrderService orderService;

    @RequestMapping("/create")
    public JsonResult<Order> create(Integer aid,Integer[] cids,HttpSession session) {
        Order order = orderService.create(aid, cids, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<>(OK,order);
    }
}
