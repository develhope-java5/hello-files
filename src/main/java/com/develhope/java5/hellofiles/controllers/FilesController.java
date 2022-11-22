package com.develhope.java5.hellofiles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.develhope.java5.hellofiles.service.FilesService;

@Controller
@RequestMapping("/files")
public class FilesController {
    @Autowired
    private FilesService filesService;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        filesService.save(file);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Resource> download(@RequestParam String fileIdentifier) {
        Resource resource = filesService.load(fileIdentifier);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }
}
