package com.usst.store.service;

import com.usst.store.entity.Product;

import java.util.List;

public interface IProductService {

    /**
     * 查询热门商品
     * @return 返回热门商品集合
     */
    List<Product> findHotList();

    /**
     * 根据商品id查询商品详情
     * @param id 商品id
     * @return 匹配的商品详情，如果没有匹配的数据则返回null
     */
    Product findById(Integer id);
}
