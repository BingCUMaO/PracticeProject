package cn.BinGCU;

import java.io.*;

public class practice_ObjectStream {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		/**
		 * 	�����ļ�Դ
		 */
		File file = new File("objectStreamTest.txt");
		if(!file.exists()) {
			file.createNewFile();
		}
		
		
		/**
		 * 	Serialize		���л�
		 */
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		
		String string = "���л�֮��һ��";
		oos.writeObject(string);
		oos.flush();		//ˢ�»�����
		oos.close();
		
		
		/**
		 * 	Deserialize		�����л�
		 */
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		ObjectInputStream ois = new ObjectInputStream(bis);		
		//ע�ⴴ��ObjectInputStream����ʱ������ȡλ�ò���Ϊ�գ�����EOFException
		
		Object strObject = ois.readObject();
		String _string = null;
		if(strObject instanceof String) {
			_string = (String)strObject;
		}
		System.out.println(_string);
		ois.close();
		
	}
}
