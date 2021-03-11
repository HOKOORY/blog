package com.ho.blogt.mapper;

import com.ho.blogt.entity.Image;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMpper {
    /**
     * 插入image
     * @param image
     * @return INT
     */
    int insertImage(Image image);

    /**
     * 通过sha512找图片
     * @param sha512
     * @return Image
     */
    Image getImageBySha512(String sha512);
}
