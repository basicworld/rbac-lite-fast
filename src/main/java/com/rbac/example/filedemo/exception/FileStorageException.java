/**
 *
 */
package com.rbac.example.filedemo.exception;

/**
 * 存储文件报错
 * 
 * @author wlfei
 * @date 2022-02-09
 */
public class FileStorageException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
