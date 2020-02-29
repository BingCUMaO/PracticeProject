package cn.BinGCU;

import java.io.*;

public class practice_PrintStream {
	public static void main(String[] args) throws IOException {
		/**
		 * 	方法一：直接使用PrintStream对象
		 */
		OutputStream os = System.out;
		PrintStream ps = new PrintStream(os);
		
		try {
			ps.write("PrintStream第一步---控制台输出".getBytes());
			ps.write("\n".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
//		ps.close();		
//		这里若关掉PrintStream流，会将System.out流也关闭。因为装饰流（处理流）依赖节点流
		
		/**
		 * 	重定向输出流
		 */
		File redirectFile  = new File("redirectTest.txt");
		if(!redirectFile.exists()) {
			redirectFile.createNewFile();
		}
		ps = new PrintStream(redirectFile);		//重定向打印输出流
		
		ps.append("PrintStream打印流之第二步---重定向输出流至指定文件。。。");
		ps.flush();
		ps.close();
		
		/**
		 * 	重定向输出流
		 * 	回控制台
		 */
		ps = new PrintStream(System.out);
		ps.write("PrintStream之第三步---回至控制台输出".getBytes());
		ps.write("\n".getBytes());
		ps.flush();
//		ps.close();		//这里关掉流后，System.out流也将关闭，即无法继续使用控制台
		
		
		
		
		
		/********************************************************************************/
		/**
		 * 	方法二：包装PrintStream类
		 */
		
		File redirectFile2  = new File("redirectTest.txt");
		if(!redirectFile2.exists()) {
			redirectFile2.createNewFile();
		}
		PrintStreamUtils.writeString("PrintStream第一步---控制台输出");
		PrintStreamUtils.writeString("\n");
		
		PrintStreamUtils.setOutStreamDestination(new BufferedOutputStream(new FileOutputStream(redirectFile2)));
		PrintStreamUtils.writeString("PrintStream打印流之第二步---重定向输出流至指定文件。。。");
		PrintStreamUtils.closeStream();
		
		PrintStreamUtils.setOutStreamDestination(System.out);
		PrintStreamUtils.writeString("PrintStream之第三步---回至控制台输出");
	}
}

class PrintStreamUtils{
	static private PrintStream destStream = new PrintStream(System.out);
	
	public static void writeString(String string) throws IOException {
		destStream.print(string);
		destStream.flush();
	}
	
	public static void setOutStreamDestination(OutputStream os) {
		destStream = new PrintStream(os);
	}
	
	public static void closeStream() throws IOException {
		destStream.close();
	}
}