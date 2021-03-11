package com.ho.blogt.service.impl;


import com.ho.blogt.entity.Image;
import com.ho.blogt.mapper.ImageMpper;
import com.ho.blogt.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageMpper imageMpper;
    @Override
    public int insertImage(Image image) {
       return imageMpper.insertImage(image);
    }

    @Override
    public Image getImageBySha512(String sha512) {
        return imageMpper.getImageBySha512(sha512);
    }
}
