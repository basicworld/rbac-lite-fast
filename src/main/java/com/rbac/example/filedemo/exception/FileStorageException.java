/**
 *
 */
package com.rbac.example.filedemo.exception;

/**
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
