package com.ho.blogt.controller;


import com.ho.blogt.entity.Image;
import com.ho.blogt.entity.Response;
import com.ho.blogt.enums.ErrorCodeAndMsg;
import com.ho.blogt.exception.HttpException;
import com.ho.blogt.service.ImageService;
import com.ho.blogt.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Date;

/**
 * 公共方法
 *
 * @author hh
 */
@RestController
@RequestMapping(value = "/common")
public class Common {
    @Autowired
    ImageService imageService;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public Response uploadImage(@RequestParam("file") MultipartFile file) {
        FileUtil fileUtil = new FileUtil();
        String fileName = file.getOriginalFilename();
        if (!fileUtil.isImage(fileName)) {
            throw new HttpException(ErrorCodeAndMsg.NOT_IMAGE);
        }
        fileName = fileUtil.getFileName(fileName);
        String path = "static/upload/image/";
        String filePath = fileUtil.getUploadPath() + "/" + path;
        //设置文件上传路径
        if (file.isEmpty()) {
            throw new HttpException(ErrorCodeAndMsg.IMAGE_NULL);
        }
        try {
            byte[] fileByte = file.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(fileByte);
            byte[] byteBuffer = messageDigest.digest();

            // 將 byte 轉換爲 string
            StringBuffer strHexString = new StringBuffer();
            // 遍歷 byte buffer
            for (int i = 0; i < byteBuffer.length; i++) {
                String hex = Integer.toHexString(0xff & byteBuffer[i]);
                if (hex.length() == 1) {
                    strHexString.append('0');
                }
                strHexString.append(hex);
            }
            String sha512 = strHexString.toString();
            Image image = imageService.getImageBySha512(sha512);
            if (null == image) {
                fileUtil.uploadFile(fileByte, filePath, fileName);
                image = new Image();
                image.setSha512(sha512);
                image.setFileName(path + fileName);
                image.setCreateTime(new Date());
                imageService.insertImage(image);
            }
            return new Response<>(image);
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpException(ErrorCodeAndMsg.UPLOAD_IMAGE_FAIL);
        }
    }

    @RequestMapping(value = "/imgShow", method = RequestMethod.GET)
    public void imgShow(String imgSha512, HttpServletResponse response) {
        Image image = imageService.getImageBySha512(imgSha512);
        String imgPath = image.getFileName();
        File file = new File(imgPath);
        String suffixName = imgPath.substring(imgPath.lastIndexOf("."));
        if (!(file.exists() && file.canRead())) {
            // 文件不存在或者无法读取
        }
        FileInputStream inputStream = null;
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            inputStream = new FileInputStream(file);
            response.setContentType("it=utf-mage/png;charset=utf-8");
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();

        } catch (Exception e) {
            logger.error("获取图片异常！{}", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
