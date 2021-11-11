package com.usst.store.service.impl;

import com.usst.store.entity.User;
import com.usst.store.mapper.UserMapper;
import com.usst.store.service.IUserService;
import com.usst.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import sun.security.x509.AVA;

import java.util.Date;
import java.util.UUID;

/**
 * 用户模块业务层的实现类
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        // 通过user参数来获取传递过来的username
        String username = user.getUsername();
        // 调用findByUserName判断用户是否被注册过
        User res = userMapper.findByUserName(username);
        // 判断结果集是否为null
        if (res != null) {
            throw new UserNameDuplicatedException("用户名被占用");
        }

        // 密码加密处理：md5
        String oldPassword = user.getPassword();
        // 获取盐值(随机)
        String salt = UUID.randomUUID().toString().toUpperCase();
        // 用于下一次比较的时候盐值相同
        user.setSalt(salt);
        // 将密码和颜值作为一个整体进行加密处理
        String md5Password = getMD5Password(oldPassword, salt);
        // 将加密后的代码重新补全设置到user对象
        user.setPassword(md5Password);

        // 一系列数据的补全操作
        user.setIsDelete(0);
        // 4个日志字段
        Date date = new Date();
        user.setCreatedUser(username);
        user.setCreatedTime(date);
        user.setModifiedUser(username);
        user.setModifiedTime(date);

        // 执行注册业务逻辑功能
        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new InsertException("在用户注册过程中产生了未知异常");
        }
    }

    @Override
    public User login(String username, String password) {

        // 根据用户名称查询用户数据是否存在，如果不在抛出异常
        User result = userMapper.findByUserName(username);
        if (result == null) {
            throw new UserNotFoundException("用户数据不存在");
        }
        // 检测用户的密码是否匹配
        // 先获取数据库中加密的密码
        String oldMd5Password = result.getPassword();
        // 和用户按照相同加密算法的密码
        String salt = result.getSalt();
        String newMd5Password = getMD5Password(password,salt);
        // 进行比较
        if (!oldMd5Password.equals(newMd5Password)) {
            throw new PasswordNotMatchException("用户密码错误");
        }
        // 判断is_delete字段的值是否为1,1表示被删除
        if (result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        // 封装查询的用户的数据，提升性能--数据压缩
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        // 将当前的用户数据返回，返回的数据用于辅助其他页面做数据展示
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {

        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        // 原始密码和数据库中密码进行比较
        String oldMd5Password = getMD5Password(oldPassword,result.getSalt());
        if (!result.getPassword().equals(oldMd5Password)) {
            throw new PasswordNotMatchException("用户密码错误");
        }
        // 将新的密码设置到数据库中，将新的密码进行加密再去更新
        String newMd5Password = getMD5Password(newPassword,result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid,newMd5Password,username,new Date());
        if (rows != 1) {
            throw new UpdateException("在用户更新密码过程中产生了未知异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知异常");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新头像时产生未知异常");
        }
    }

    /**
     * 定义一个MD5算法加密
     * @param password 旧密码
     * @param salt 颜值
     * @return 加密后的密码
     */
    private String getMD5Password(String password, String salt) {

        /**
         * 加密规则：
         * 1、无视原始密码的强度
         * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
         * 3、循环加密3次
         */
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
