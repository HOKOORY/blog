package com.ho.blogt.controller;

import com.ho.blogt.entity.Response;
import com.ho.blogt.entity.User;
import com.ho.blogt.enums.ErrorCodeAndMsg;
import com.ho.blogt.exception.HttpException;
import com.ho.blogt.service.TokenService;
import com.ho.blogt.service.UserPhotoService;
import com.ho.blogt.utils.Constant;
import com.ho.blogt.utils.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/photo")
public class UserPhotoController {
    @Autowired
    UserPhotoService userPhotoService;
    @Autowired
    TokenService tokenService;

    /**
     * 上传自己的照片
     * @param token token header传
     * @param imageId 图片ID 访问/common/uploadImage接口获取
     * @param status 状态是否公开 1公开  2家庭成员  3自己
     * @return
     */
    @PostMapping("/uploadPhoto")
    public Response uploadPhoto(@RequestHeader(name = "token", required = false, defaultValue = "0") String token,
                                @RequestParam("imageId") long imageId, @RequestParam("status") int status) {
        if (Constant.DEFAULT_VALUE.equals(token)) {
            throw new HttpException(ErrorCodeAndMsg.USER_NOT_LOGIN);
        }
        User user = (User) tokenService.getToken(token);
        if (null == user) {
            throw new HttpException(ErrorCodeAndMsg.USER_NOT_LOGIN);
        }
        return new Response(userPhotoService.insertUserPhoto(user.getId(), imageId, status));
    }

    /**
     * 获取自己照片的列表
     * @param token token header传
     * @param startTime 筛选条件
     * @param endTime 筛选条件
     * @param pageRequest 分页 page/limit
     * @return
     */
    @GetMapping("/userPhotoList")
    public Response getUserPhotoList(@RequestHeader(name = "token", required = false, defaultValue = Constant.DEFAULT_VALUE) String token,
                                     @RequestParam(value = "startTime", required = false, defaultValue = Constant.DEFAULT_VALUE) String startTime,
                                     @RequestParam(value = "endTime", required = false, defaultValue = Constant.DEFAULT_VALUE) String endTime,
                                     PageRequest pageRequest) {
        if (Constant.DEFAULT_VALUE.equals(token)) {
            throw new HttpException(ErrorCodeAndMsg.USER_NOT_LOGIN);
        }
        User user = (User) tokenService.getToken(token);
        if (null == user) {
            throw new HttpException(ErrorCodeAndMsg.USER_NOT_LOGIN);
        }
        Date startDateTime = null;
        Date endDateTime = null;
        if (!Constant.DEFAULT_VALUE.equals(startTime) && !Constant.DEFAULT_VALUE.equals(endTime)) {
            try {
                startDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
                endDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
            } catch (ParseException e) {
                // 抛出时间格式不对的异常
                throw new HttpException(ErrorCodeAndMsg.DATEFORMAT_ERROR);
            }
        }
        return new Response(userPhotoService.getUserPhotoList(user.getId(), startDateTime, endDateTime, pageRequest));
    }

    /**
     * 删除自己的某张照片
     * @param photoId 照片ID
     * @param token token header传
     * @return
     */
    @PostMapping("/deleteUserPhoto")
    public Response deleteUserPhoto(@RequestParam("photoId") long photoId,
                                    @RequestHeader(name = "token") String token) {
        if (Constant.DEFAULT_VALUE.equals(token)) {
            throw new HttpException(ErrorCodeAndMsg.USER_NOT_LOGIN);
        }
        User user = (User) tokenService.getToken(token);
        if (null == user) {
            throw new HttpException(ErrorCodeAndMsg.USER_NOT_LOGIN);
        }
        return new Response(userPhotoService.deleteUserPhoto(photoId,user.getId()));
    }
}
