package com.usst.store.service;

import com.usst.store.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// 标注当前的类是一个测试类，不会随同项目一块打包发送
@SpringBootTest
// @RunWith：表示启动这个单元测试类，需要传递一个SpringRunner的实例类型
@RunWith(SpringRunner.class)
public class AddressServiceTests {

    @Autowired
    private IAddressService addressService;

    @Test
    public void addNewAddress() {
        Address address = new Address();
        address.setUid(6);
        address.setPhone("18017339999");
        address.setName("Mary");
        addressService.addNewAddress(6,"管理员",address);
    }

    @Test
    public void setDefault() {
        addressService.setDefault(6,6,"管理员");
    }

    @Test
    public void delete() {
        addressService.delete(2,6,"李四");
    }
}
