package com.usst.store.controller;

import com.usst.store.controller.ex.*;
import com.usst.store.entity.User;
import com.usst.store.service.IUserService;
import com.usst.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController{

    @Autowired
    private IUserService userService;

    @RequestMapping("/reg")
    public JsonResult<Void> reg(User user) {
        // 创建响应结果对象
        userService.reg(user);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User user = userService.login(username, password);
        // 登陆成功后，将uid和username存入到HttpSession中，全局session
        session.setAttribute("uid",user.getUid());
        session.setAttribute("username",user.getUsername());
        return new JsonResult<>(OK,user);
    }

    @RequestMapping("/change_password")
    public JsonResult<Void> changePassword(String oldPassword,String newPassword,HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        User result = userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(OK,result);
    }

    @RequestMapping("/change_info")
    public JsonResult<Void> changeInfo(User user,HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid,username,user);
        return new JsonResult<>(OK);
    }

    // 设置上传文件的最大值
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    // 限制上传文件的类型
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }


    @RequestMapping("/change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                                           MultipartFile file) {
        // 判断文件是否为空
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        // 判断文件大小是否超出限制
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件大小超出限制");
        }
        // 判断文件类型是否是规定类型
        String contentType = file.getContentType();
        if (!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("文件类型不支持");
        }
        String parent = session.getServletContext().getRealPath("upload");
        // File对象指向这个路径，File是否存在
        File dir = new File(parent);
        if (!dir.exists()) {
            dir.mkdirs();  // 创建当前目录
        }
        // 获取到这个文件名称，UUID工具生成一个新的字符串作为文件名
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename=" + originalFilename);
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;
        File dest = new File(dir, filename);
        // 将参数file中的数据写入到dest中
        try {
            file.transferTo(dest);
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }
        // 返回头像路径
        String avatar = "/upload/" + filename;
        userService.changeAvatar(getUidFromSession(session),avatar,getUsernameFromSession(session));
        return new JsonResult<>(OK,avatar);
    }
}
