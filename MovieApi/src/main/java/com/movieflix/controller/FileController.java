package com.movieflix.controller;

import com.movieflix.service.FileService;
import com.movieflix.service.FileServiceImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/file/")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    @Value("${project.poster}")
    private String path;

    @PostMapping("upload/")
    public ResponseEntity<String> fileUpload(@RequestPart("file") MultipartFile file) throws IOException {
        String fileName =   fileService.uploadFile(path,file    );
        return ResponseEntity.status(HttpStatus.CREATED).body(String.format("Successfully uploaded %s file",fileName));
    }
    @GetMapping("getFile/{fileName}")
    public void getFile(@PathVariable String fileName, HttpServletResponse response) throws IOException {
     //InputStream object representing the content of
        // the file obtained from the fileService.getResourceFile()
        InputStream in = fileService.getResourceFile(path,fileName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(in,response.getOutputStream());
    }

//    private final FileServiceImpl fileService;
//
//    @Value("${project.poster}")
//    private String path;
//
//
//    public FileController(FileServiceImpl fileService) {
//        this.fileService = fileService;
//    }
//
//    @PostMapping("upload/")
//    public ResponseEntity<String> uploadFile(@RequestPart MultipartFile file) throws IOException {
//       String uploadFileName =  fileService.uploadFile(path,file);
//       return ResponseEntity.status(HttpStatus.CREATED).body("Successfully uploaded "+uploadFileName);
//    }
//    @GetMapping("/{fileName}")
//    public void getFileByFileName(@PathVariable String fileName, HttpServletResponse response) throws IOException {
//        InputStream resourceFile = fileService.getResourceFile(path,fileName);
//        response.setContentType(MediaType.IMAGE_PNG_VALUE);
//        StreamUtils.copy(resourceFile,response.getOutputStream());
//    }
}
