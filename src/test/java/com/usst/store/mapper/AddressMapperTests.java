package com.usst.store.mapper;

import com.usst.store.entity.Address;
import com.usst.store.entity.User;
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
public class AddressMapperTests {

    // idea有检测功能，接口不能直接创建对象
    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert() {

        Address address = new Address();
        address.setUid(6);
        address.setPhone("18017335683");
        address.setName("Lucy");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid() {
        Integer count = addressMapper.countByUid(6);
        System.out.println(count);
    }

    @Test
    public void findByUid() {
        List<Address> list = addressMapper.findByUid(6);
        for (Address address : list) {
            System.out.println(address);
        }
    }

    @Test
    public void findByAid() {
        Address address = addressMapper.findByAid(6);
        System.out.println(address);
    }

    @Test
    public void updateNonDefault() {
        addressMapper.updateNonDefault(6);
    }

    @Test
    public void updateDefaultByAid() {
        addressMapper.updateDefaultByAid(6,"张三",new Date());
    }

    @Test
    public void deleteByAid() {
        addressMapper.deleteByAid(1);
    }

    @Test
    public void findLastModified() {
        Address address = addressMapper.findLastModified(6);
        System.out.println(address);
    }
}
