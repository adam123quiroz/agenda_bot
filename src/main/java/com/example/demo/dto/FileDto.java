package com.example.demo.dto;

import com.example.demo.domain.AgFile;

public class FileDto {
    private Integer idFile;
    private String fileName;
    private String mimeType;
    private String path;
    private String scoreType;

    public FileDto(AgFile agFile) {
        this.idFile = agFile.getIdFile();
        this.fileName = agFile.getFileName();
        this.mimeType = agFile.getMimeType();
        this.path = agFile.getPath();
        this.scoreType = agFile.getScoreType();
    }

    public Integer getIdFile() {
        return idFile;
    }

    public void setIdFile(Integer idFile) {
        this.idFile = idFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    @Override
    public String toString() {
        return "FileDto{" +
                "idFile=" + idFile +
                ", fileName='" + fileName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", path='" + path + '\'' +
                ", scoreType='" + scoreType + '\'' +
                '}';
    }
}
