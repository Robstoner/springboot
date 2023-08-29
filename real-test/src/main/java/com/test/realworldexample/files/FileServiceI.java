package com.test.realworldexample.files;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

public interface FileServiceI {
    @PostConstruct
    public void init();

    public String save(MultipartFile file, String folder);

    public Resource load(String filename);

    public void delete(String filename);
}
