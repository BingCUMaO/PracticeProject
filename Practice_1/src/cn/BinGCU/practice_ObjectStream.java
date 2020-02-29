package cn.BinGCU;

import java.io.*;

public class practice_ObjectStream {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		/**
		 * 	创建文件源
		 */
		File file = new File("objectStreamTest.txt");
		if(!file.exists()) {
			file.createNewFile();
		}
		
		
		/**
		 * 	Serialize		序列化
		 */
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		
		String string = "序列化之第一步";
		oos.writeObject(string);
		oos.flush();		//刷新缓冲区
		oos.close();
		
		
		/**
		 * 	Deserialize		反序列化
		 */
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		ObjectInputStream ois = new ObjectInputStream(bis);		
		//注意创建ObjectInputStream对象时，所读取位置不能为空，否则EOFException
		
		Object strObject = ois.readObject();
		String _string = null;
		if(strObject instanceof String) {
			_string = (String)strObject;
		}
		System.out.println(_string);
		ois.close();
		
	}
}
