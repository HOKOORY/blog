package com.ho.blogt.mapper;

import com.ho.blogt.entity.UserPhoto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserPhotoMapper {
    int insertUserPhoto(@Param("userPhoto") UserPhoto userPhoto);

    List<UserPhoto> getUserPhotoList(@Param("userId") long userId,
                                     @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    int deleteUserPhoto(@Param("photoId") long photoId);

    UserPhoto getUserPhoto(@Param("photoId") long photoId);
}
