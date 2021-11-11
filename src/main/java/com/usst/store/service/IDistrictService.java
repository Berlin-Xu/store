package com.usst.store.service;

import com.usst.store.entity.District;

import java.util.List;

/**
 * 省市区业务层接口
 */
public interface IDistrictService {

    /**
     * 根据父代号来查询区域的信息
     * @param parent 父代号
     * @return 多个区域的信息
     */
    List<District> getByParent(String parent);

    /**
     * 根据代号获取名字
     * @param code 代号
     * @return 名字
     */
    String getNameByCode(String code);
}
