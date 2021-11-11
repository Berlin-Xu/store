package com.usst.store.service;

import com.usst.store.entity.User;

/**
 * 用户模块业务层接口
 */
public interface IUserService {

    /**
     * 用户注册
     * @param user 用户数据
     */
    void reg(User user);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 成功登陆查询到的用户数据
     */
    User login(String username,String password);

    /**
     * 修改密码
     * @param uid 用户id
     * @param username 修改人
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void changePassword(Integer uid,String username,String oldPassword,String newPassword);

    /**
     * 根据用户id获取用户信息
     * @param uid 用户id
     * @return 用户数据
     */
    User getByUid(Integer uid);

    /**
     * 修改用户资料，id和username可以在session中获得
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @param user 用户的新的数据
     */
    void changeInfo(Integer uid, String username, User user);

    /**
     * 修改用户头像
     * @param uid 用户id
     * @param avatar 用户头像的路径
     * @param username 用户名
     */
    void changeAvatar(Integer uid,String avatar,String username);


}
