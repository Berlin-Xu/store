package com.usst.store.mapper;

import com.usst.store.entity.Address;

import java.util.Date;
import java.util.List;

public interface AddressMapper {

    /**
     * 插入用户的收货地址数据
     * @param address 收货地址数据
     * @return 受影响的行数
     */
    Integer insert(Address address);

    /**
     * 根据用户的id查询用户收货地址总数
     * @param uid 用户id
     * @return 当前用户ed收货地址总数
     */
    Integer countByUid(Integer uid);

    /**
     * 根据用户的id查询用户的收货地址数据
     * @param uid 用户id
     * @return 收货地址数据
     */
    List<Address> findByUid(Integer uid);

    /**
     * 根据aid查询收货地址
     * @param id 收货地址id
     * @return 收货地址数据
     */
    Address findByAid(Integer id);

    /**
     * 根据用户id，将所有收货地址设为非默认
     * @param uid 用户id
     * @return 受影响行数
     */
    Integer updateNonDefault(Integer uid);

    /**
     * 根据aid将地址设为默认
     * @param aid 地址id
     * @param modifiedUser 修改者
     * @param modifiedTime 修改时间
     * @return 受影响行数
     */
    Integer updateDefaultByAid(Integer aid, String modifiedUser, Date modifiedTime);

    /**
     * 根据收货地址的id删除收货地址数据
     * @param aid 收货地址的id
     * @return 受影响的行数
     */
    Integer deleteByAid(Integer aid);

    /**
     * 根据用户的id查询最后一次被修改的收货地址数据
     * @param uid 用户id
     * @return 最后一次被修改的收货地址数据
     */
    Address findLastModified(Integer uid);
}
