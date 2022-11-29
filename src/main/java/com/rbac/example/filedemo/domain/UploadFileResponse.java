/**
 *
 */
package com.rbac.example.filedemo.domain;

/**
 * 上传文件的响应信息
 *
 * @author wlfei
 * @date 2022-02-09
 */
public class UploadFileResponse {
    /** 文件名 */
    private String fileName;
    /** 文件下载url */
    private String fileDownloadUri;
    /** 文件类型 */
    private String fileType;
    /** 文件大小 */
    private long size;

    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        super();
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}
