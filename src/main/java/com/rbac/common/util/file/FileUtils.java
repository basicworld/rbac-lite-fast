package com.rbac.common.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件处理工具
 * 
 * @author wlfei
 *
 */
public class FileUtils {
	/**
	 * 以流形式下载文件 <br>
	 * 不做路径等合法校验
	 * 
	 * @param response
	 * @param saveFileName       客户端保存的文件名
	 * @param fullFilePathOnDisk 文件在服务器硬盘的全路径存储位置
	 */
	public static void streamDownload(HttpServletResponse response, String saveFileName, String fullFilePathOnDisk) {
		// config
		final String CONTENT_TYPE = "application/x-msdownload";
		final String ENCODING = "UTF-8";
		final int BLOCK_SIZE = 65000;

		response.setCharacterEncoding(ENCODING);

		File file = new File(fullFilePathOnDisk);

		FileInputStream fileIn = null;
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			fileIn = new FileInputStream(file);
			long fileLen = file.length();
			int readBytes = 0;
			int totalRead = 0;
			byte[] b = new byte[BLOCK_SIZE];
			response.setContentType(CONTENT_TYPE);
			response.setContentLength((int) fileLen);

			if (fullFilePathOnDisk.length() == 0) {
				// 空文件名处理
				response.setHeader("Content-Disposition", "attachment;");
			} else {
				// 正常文件处理
				String downFileName = URLEncoder.encode(saveFileName, ENCODING);
				response.setHeader("Content-Disposition", String.valueOf(
						new StringBuffer(String.valueOf("attachment;")).append(" filename=").append(downFileName)));
			}
			// 循环流式输出
			while (totalRead < fileLen) {
				readBytes = fileIn.read(b, 0, BLOCK_SIZE);
				totalRead += readBytes;
				out.write(b, 0, readBytes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileIn != null) {
				try {
					fileIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

}
