package org.king.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.struts.upload.FormFile;

public class FileUtil {
	public synchronized static String saveUploadFile(FormFile formFile,String filePath)
	{
		String fileName = "";
		try {
			fileName = formFile.getFileName();
			if(formFile.getInputStream() != null && !fileName.isEmpty())
			{				
				File file = new File(filePath);
				if(!file.exists())
				{
					if(!file.mkdirs())
					{
						System.err.println("创建文件夹失败！");
					}
				}
				int index = fileName.indexOf(".");
				fileName = System.currentTimeMillis() + fileName.substring(index);
				saveInputStreamToFile(formFile.getInputStream(), filePath + "\\" + fileName);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fileName;
	}
	
	public static void saveInputStreamToFile(InputStream from,String fileUrl)
	{	
		FileOutputStream to = null;
		try {
			to = new FileOutputStream(fileUrl);
			byte[] buffer = new byte[4096];
			int bytes_read;
			while ((bytes_read = from.read(buffer)) != -1)
				to.write(buffer, 0, bytes_read); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (from != null)
				try {
					from.close();
				} catch (IOException e) {
					;
				}
			if (to != null)
				try {
					to.close();
				} catch (IOException e) {
					;
				}
		}
	}
	
	public static String getContentType(String fileName) {
        String fileNameTmp = fileName.toLowerCase();
        String ret = "";
        if (fileNameTmp.endsWith("txt")) {
            ret = "text/plain";
        }
        if (fileNameTmp.endsWith("gif")) {
            ret = "image/gif";
        }
        if (fileNameTmp.endsWith("jpg")) {
            ret = "image/jpeg";
        }
        if (fileNameTmp.endsWith("jpeg")) {
            ret = "image/jpeg";
        }
        if (fileNameTmp.endsWith("jpe")) {
            ret = "image/jpeg";
        }
        if (fileNameTmp.endsWith("zip")) {
            ret = "application/zip";
        }
        if (fileNameTmp.endsWith("rar")) {
            ret = "application/rar";
        }
        if (fileNameTmp.endsWith("doc")) {
            ret = "application/msword";
        }
        if (fileNameTmp.endsWith("ppt")) {
            ret = "application/vnd.ms-powerpoint";
        }
        if (fileNameTmp.endsWith("xls")) {
            ret = "application/vnd.ms-excel";
        }
        if (fileNameTmp.endsWith("html")) {
            ret = "text/html";
        }
        if (fileNameTmp.endsWith("htm")) {
            ret = "text/html";
        }
        if (fileNameTmp.endsWith("tif")) {
            ret = "image/tiff";
        }
        if (fileNameTmp.endsWith("tiff")) {
            ret = "image/tiff";
        }
        if (fileNameTmp.endsWith("pdf")) {
            ret = "application/pdf";
        }
        return ret;
    }

}
