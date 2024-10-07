package org.boot.post_springboot.demo.service.Implements;

import lombok.Setter;
import org.boot.post_springboot.demo.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;


@Setter
public class FileServiceImplements implements FileService {

//    @Value("%{file.path}")
//    private String filePath;
//    @Value("%{file.url}")
//    private String fileUrl;
//
//    @Override
//    public String upload(MultipartFile file) {
//        if (file.isEmpty()) return null;
//        String originalFilename = file.getOriginalFilename();
//        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")); //마지막 .뒤에 오는 것이 확장자이기 때문에
//        String uuid = UUID.randomUUID().toString();
//        String saveFileName = uuid + extension;
//        String savePath = filePath + saveFileName;
//
//        try {
//            file.transferTo(new File(savePath));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        String url = fileUrl + saveFileName;
//        return url;
//    }
//
//    @Override
//    public Resource getImage(String fileName) {
//
//        Resource resource = null;
//
//        try {
//            resource = new UrlResource("file" + filePath + fileName);
//
//        }catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        return resource;


//    }
}
