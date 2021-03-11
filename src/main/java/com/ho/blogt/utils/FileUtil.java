package com.ho.blogt.utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public void uploadFile(byte[] file, String filePath, String fileName) throws IOException {

        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = null;
        out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    /**
     * 文件名后缀前添加一个时间戳
     */
    public String getFileName(String fileName) {
        int index = fileName.lastIndexOf(".");
        fileName = fileName.substring(0, index) + "_" + StringUtil.randomString(5) +
                System.currentTimeMillis() + fileName.substring(index);
        return fileName;
    }

    /**
     * 获取当前系统路径
     */
    public String getUploadPath() {
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!path.exists()) {
            path = new File("");
        }
        File upload = new File(path.getAbsolutePath(), "");
        if (!upload.exists()) {
            upload.mkdirs();
        }
        return upload.getAbsolutePath();
    }

    public boolean isImage(String fileName) {
        int index = fileName.lastIndexOf(".");
        List<String> imageType = new ArrayList<>();
        imageType.add(".png");
        imageType.add(".jpg");
        imageType.add(".jpeg");
        String a = fileName.substring(index);
        return imageType.contains(fileName.substring(index));
    }
}
