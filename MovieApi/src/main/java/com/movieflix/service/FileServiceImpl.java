package com.movieflix.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService{

    Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String pathStorage = path.concat(File.separator).concat(fileName);

        // This line does not create the file or directory physically on the disk;
        // it merely creates a Java object representing the file or directory specified by the path.
        File f = new File(pathStorage);
        if(!f.exists())
        {
            f.mkdirs();
        }
        try {
            Files.copy(file.getInputStream(), Paths.get(pathStorage),StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
          throw new IOException("Couldn't to copy the contents of a source file to a target file");
        }
        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
        String filePath = path.concat(File.separator).concat(fileName);
       return new FileInputStream(filePath);
    }


//
//    Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
//    @Override
//    public String uploadFile(String path, MultipartFile file) throws IOException{
//        //get file name
//        String fileName = file.getOriginalFilename();
//        //get file path
//        String filePath = path + File.separator + fileName;
//        //create file object
//        File f = new File(filePath);
//        if(!f.exists()){
//            f.mkdir();
//        }
//        //copy the file or upload file  to the path
//        try {
//            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
//        }catch (IOException e){
//            log.info("error in files copy");
//        }
//
//        return fileName;
//    }
//    @Override
//    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException{
//
//        String filePath = path + File.separator + fileName;
//        return new FileInputStream(filePath);
//    }
}
