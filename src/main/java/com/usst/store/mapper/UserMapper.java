package com.usst.store.mapper;

import com.usst.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 用户模块的持久层接口
 */
public interface UserMapper {

    /**
     * 插入用户数据
     * @param user 用户数据
     * @return 受影响的行数
     */
    Integer insert(User user);

    /**
     * 根据用户名查询用户数据
     * @param username 用户名
     * @return 返回查询的信息，用户数据或null
     */
    User findByUserName(String username);

    /**
     * 根据用户的uid来修改用户的密码
     * @param uid 用户id
     * @param password 用户输入的新密码
     * @param modifiedUser 修改的执行者
     * @param modifiedTime 修改的时间
     * @return 受影响行数
     */
    Integer updatePasswordByUid(Integer uid, String password,
                                String modifiedUser, Date modifiedTime);

    /**
     * 根据uid查询用户数据
     * @param uid 用户id
     * @return 查询到返回用户对象，否则返回null值
     */
    User findByUid(Integer uid);

    /**
     * 更新用户的数据信息
     * @param user 用户数据
     * @return 受影响行数
     */
    Integer updateInfoByUid(User user);

    /**
     * 根据uid更新用户的头像
     * @param uid 用户的id
     * @param avatar 新头像的路径
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") Date modifiedTime);
}
