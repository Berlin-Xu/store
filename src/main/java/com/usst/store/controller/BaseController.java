package com.usst.store.controller;

import com.usst.store.controller.ex.*;
import com.usst.store.service.ex.*;
import com.usst.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * 控制层类的基类：负责响应成功的状态码及统一处理异常
 */
public class BaseController {

    // 操作成功状态码
    public static final int OK = 200;

    // 请求处理方法，这个方法的返回值就是需要传递给前端的数据
    // 自动将异常对象传递给此方法的参数列表上
    // 当项目中产生了异常，会统一拦截到此方法中，
    // 这个方法此时就充当请求处理方法，方法的返回值直接到达前端
    @ExceptionHandler({ServiceException.class,FileUploadException.class})  // 用于统一处理抛出的异常
    public JsonResult<Void> handlerException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UserNameDuplicatedException) {
            result.setState(4000);
            result.setMessage("用户名已经被占用的异常");
        }else if (e instanceof UserNotFoundException) {
            result.setState(4001);
            result.setMessage("用户数据不存在的异常");
        }else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
            result.setMessage("用户名密码输入错误的异常");
        }else if (e instanceof AddressCountLimitException) {
            result.setState(4003);
            result.setMessage("用户的收货地址超出上限的异常");
        } else if (e instanceof AddressNotFoundException) {
            result.setState(4004);
            result.setMessage("收货地址数据不存在的异常");
        } else if (e instanceof AccessDeniedException) {
            result.setState(4005);
            result.setMessage("数据非法访问的异常");
        } else if (e instanceof ProductNotFoundException) {
            result.setState(4006);
            result.setMessage("商品数据不存在的异常");
        }else if (e instanceof CartNotFoundException) {
            result.setState(4007);
            result.setMessage("购物车数据不存在的异常");
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("注册时产生未知异常");
        } else if (e instanceof UpdateException) {
            result.setState(5001);
            result.setMessage("更新时产生未知异常");
        } else if (e instanceof DeleteException) {
            result.setState(5002);
            result.setMessage("删除时产生未知异常");
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
            result.setMessage("上传文件为空的异常");
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
            result.setMessage("上传文件超出最大限制异常");
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
            result.setMessage("上传文件类型不匹配异常");
        } else if (e instanceof FileStateException) {
            result.setState(6003);
            result.setMessage("上传文件状态异常");
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
            result.setMessage("文件上传IO异常");
        }
        return result;
    }

    /**
     * 获取session对象中的uid
     * @param session session对象
     * @return 当前登录用户的uid的值
     */
    protected final Integer getUidFromSession(HttpSession session) {
        return Integer.parseInt(session.getAttribute("uid").toString());
    }

    /**
     * 获取session对象中的username
     * @param session session对象
     * @return 当前登录用户的username的值
     */
    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}
