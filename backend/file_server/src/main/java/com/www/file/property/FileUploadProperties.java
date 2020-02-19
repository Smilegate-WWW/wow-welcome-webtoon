package com.www.file.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

//@ConfigurationProperties(prefix="file")
@ConfigurationProperties(prefix="custom")

public class FileUploadProperties {
    private String uploadDir;
 
    public String getUploadDir() {
        return uploadDir;
    }
 
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
