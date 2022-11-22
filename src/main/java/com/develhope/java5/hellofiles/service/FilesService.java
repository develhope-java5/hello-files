package com.develhope.java5.hellofiles.service;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesService {
    Logger logger = LogManager.getLogger(FilesService.class);

    @Value("${files.destinationFolder}")
    private String destinationFolder;

    @Autowired
    private ResourceLoader resourceLoader;

    public void save(MultipartFile multipartFile) {
        File file = new File(destinationFolder + multipartFile.getOriginalFilename());

        if(file.exists()) {
            logger.warn("File " + file.getName() + " exists");
        }

        try {
            multipartFile.transferTo(file);
        } catch (IllegalStateException | IOException e) {
            logger.error("UNABLE TO SAVE FILE");
            e.printStackTrace();
        } 
    }

    public Resource load(String fileIdentifier) {
        String filePath = String.format("file:%s/%s", destinationFolder, fileIdentifier);
        return resourceLoader.getResource(filePath);
    }
}
