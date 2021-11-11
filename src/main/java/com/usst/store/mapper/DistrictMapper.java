package com.usst.store.mapper;

import com.usst.store.entity.District;

import java.util.List;

public interface DistrictMapper {

    /**
     * 根据用户的父代号查询区域信息
     * @param parent 父代号
     * @return 父区域下的所有区域列表
     */
    List<District> findByParent(String parent);

    /**
     * 根据code获取名字
     * @param code 代号
     * @return 名字
     */
    String findNameByCode(String code);
}
