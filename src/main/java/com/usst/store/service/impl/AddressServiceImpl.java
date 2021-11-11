package com.usst.store.service.impl;

import com.usst.store.entity.Address;
import com.usst.store.mapper.AddressMapper;
import com.usst.store.service.IAddressService;
import com.usst.store.service.IDistrictService;
import com.usst.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户新增收货地址业务层实现类
 */
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    // 在添加用户的收货地址的业务层依赖于IDistrictService
    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {

        // 调用收货地址统计的方法
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount) {
            throw new AddressCountLimitException("用户收货地址超出上限");
        }

        // 对address对象中的数据补全：省市区
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        // 补全数据
        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);
        address.setCreatedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());

        // 插入收货地址的方法
        Integer rows = addressMapper.insert(address);
        if (rows != 1) {
            throw new InsertException("插入用户的收货地址产生未知异常");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        for (Address address : list) {
            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setTel(null);
            address.setIsDefault(null);
            address.setCreatedTime(null);
            address.setCreatedUser(null);
            address.setModifiedTime(null);
            address.setModifiedUser(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("收货地址不存在");
        }
        // 检测当前获取到的收货地址数据的归属
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }
        // 先将所有的收货地址设置为非默认
        Integer rows = addressMapper.updateNonDefault(uid);
        if (rows < 1) {
            throw new UpdateException("更新数据时产生未知异常");
        }
        // 将用户选中的某条地址设为默认地址
        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知异常");
        }
    }

    @Override
    public void delete(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("收货地址数据不存在");
        }
        // 检测当前获取到的收货地址数据的归属
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }
        Integer rows = addressMapper.deleteByAid(aid);
        if (rows != 1) {
            throw new DeleteException("删除数据产生未知的异常");
        }
        Integer count = addressMapper.countByUid(uid);
        if (count == 0) {
            return;
        }
        // 删除了默认的地址，就要重新设置一个地址作为默认地址
        if (result.getIsDefault() == 1) {
            // 将这条数据中的is_default设置为1
            Address address = addressMapper.findLastModified(uid);
            rows = addressMapper.updateDefaultByAid(address.getAid(), username, new Date());
            if (rows != 1) {
                throw new UpdateException("更新数据时产生未知异常");
            }
        }
    }

    @Override
    public Address getByAid(Integer aid, Integer uid) {
        Address address = addressMapper.findByAid(aid);
        if (address == null) {
            throw new AddressNotFoundException("收货地址不存在");
        }
        // 检测当前获取到的收货地址数据的归属
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedTime(null);
        address.setCreatedUser(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);
        return address;
    }
}
