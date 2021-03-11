package com.ho.blogt.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ho.blogt.entity.UserPhoto;
import com.ho.blogt.enums.ErrorCodeAndMsg;
import com.ho.blogt.exception.HttpException;
import com.ho.blogt.mapper.UserPhotoMapper;
import com.ho.blogt.service.UserPhotoService;
import com.ho.blogt.utils.PageRequest;
import com.ho.blogt.utils.PageResult;
import com.ho.blogt.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserPhotoServiceImpl implements UserPhotoService {
    @Autowired
    UserPhotoMapper userPhotoMapper;

    @Override
    public int insertUserPhoto(long userId, long imageId, int status) {
        UserPhoto userPhoto = new UserPhoto();
        userPhoto.setCreateTime(new Date());
        userPhoto.setUserId(userId);
        userPhoto.setStatus(status);
        userPhoto.setImageId(imageId);
        userPhoto.setUpdateTime(new Date());
        return userPhotoMapper.insertUserPhoto(userPhoto);
    }

    @Override
    public PageResult getUserPhotoList(long userId, Date startTime, Date endTime, PageRequest pageRequest) {
        int page = pageRequest.getPage();
        int limit = pageRequest.getLimit();
        PageHelper.startPage(page, limit);
        List<UserPhoto> list = userPhotoMapper.getUserPhotoList(userId, startTime, endTime);
        return PageUtils.getPageResult(new PageInfo<UserPhoto>(list));
    }

    @Override
    public int deleteUserPhoto(long photoId, long userId) {
        UserPhoto userPhoto = userPhotoMapper.getUserPhoto(photoId);
        if (userPhoto.getUserId() != userId) {
            // 用户不是图片的主人，抛出错误
            throw new HttpException(ErrorCodeAndMsg.PHOTO_IS_NOT_YOUR);
        }
        return userPhotoMapper.deleteUserPhoto(photoId);
    }
}
