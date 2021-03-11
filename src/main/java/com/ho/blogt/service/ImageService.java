package com.ho.blogt.service;


import com.ho.blogt.entity.Image;

public interface ImageService {
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
