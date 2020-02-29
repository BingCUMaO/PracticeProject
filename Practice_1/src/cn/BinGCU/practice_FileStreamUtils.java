package cn.BinGCU;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class practice_FileStreamUtils {
	public static void main(String[] args) {
		
		String destPath = "dest.jpg";
		File src = createFile("src.jpg");
		File dest = new File(destPath);
		
		InputStream is  = null;
		OutputStream os = null;
		
		//���ĸ���
		try {
			is = new FileInputStream(src);
			os  = new FileOutputStream(dest);
			
			copyFile(is, os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//���Ĺر�
		close(is,os);
	}
	
	/**
	 * 	�������ƹ��̵ķ�װ
	 * @param is
	 * @param os
	 */
	public static void copyFile(InputStream is, OutputStream os) {
		byte[] buffer = new byte[1024];
		
		int len = 0;
		try {
			while((len = is.read(buffer))!=-1) {
				os.write(buffer, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 	���ļ����ڽ��м��
	 * 	���ļ�·�������ڣ��򴴽��ļ�
	 * @param path
	 * @return
	 */
	public static File createFile(String path) {
		File file = new File(path);
		
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return file;
	}
	public static File checkFileExist(File file) {
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return file;
	}
	
	/**
	 * 	�����رչ��̵ķ�װ
	 * @param ios
	 */
	public static void close(Closeable... ios) {
		for(Closeable io:ios) {
			if(io != null) {
				try {
					io.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
