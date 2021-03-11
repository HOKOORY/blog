package com.ho.blogt.service;

import com.ho.blogt.utils.PageRequest;
import com.ho.blogt.utils.PageResult;

import java.util.Date;

public interface UserPhotoService {
    int insertUserPhoto(long userId, long imageId,int status);

    PageResult getUserPhotoList(long userId, Date startTime, Date endTime, PageRequest pageRequest);

    int deleteUserPhoto(long photoId,long userId);
}
