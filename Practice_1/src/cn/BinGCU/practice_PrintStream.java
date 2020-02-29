package cn.BinGCU;

import java.io.*;

public class practice_PrintStream {
	public static void main(String[] args) throws IOException {
		/**
		 * 	����һ��ֱ��ʹ��PrintStream����
		 */
		OutputStream os = System.out;
		PrintStream ps = new PrintStream(os);
		
		try {
			ps.write("PrintStream��һ��---����̨���".getBytes());
			ps.write("\n".getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
//		ps.close();		
//		�������ص�PrintStream�����ὫSystem.out��Ҳ�رա���Ϊװ�������������������ڵ���
		
		/**
		 * 	�ض��������
		 */
		File redirectFile  = new File("redirectTest.txt");
		if(!redirectFile.exists()) {
			redirectFile.createNewFile();
		}
		ps = new PrintStream(redirectFile);		//�ض����ӡ�����
		
		ps.append("PrintStream��ӡ��֮�ڶ���---�ض����������ָ���ļ�������");
		ps.flush();
		ps.close();
		
		/**
		 * 	�ض��������
		 * 	�ؿ���̨
		 */
		ps = new PrintStream(System.out);
		ps.write("PrintStream֮������---��������̨���".getBytes());
		ps.write("\n".getBytes());
		ps.flush();
//		ps.close();		//����ص�����System.out��Ҳ���رգ����޷�����ʹ�ÿ���̨
		
		
		
		
		
		/********************************************************************************/
		/**
		 * 	����������װPrintStream��
		 */
		
		File redirectFile2  = new File("redirectTest.txt");
		if(!redirectFile2.exists()) {
			redirectFile2.createNewFile();
		}
		PrintStreamUtils.writeString("PrintStream��һ��---����̨���");
		PrintStreamUtils.writeString("\n");
		
		PrintStreamUtils.setOutStreamDestination(new BufferedOutputStream(new FileOutputStream(redirectFile2)));
		PrintStreamUtils.writeString("PrintStream��ӡ��֮�ڶ���---�ض����������ָ���ļ�������");
		PrintStreamUtils.closeStream();
		
		PrintStreamUtils.setOutStreamDestination(System.out);
		PrintStreamUtils.writeString("PrintStream֮������---��������̨���");
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