package MySocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 	ģ���½������
 * 
 * @author BinGCU
 *
 */
public class TCP_Login__Server {
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(888);
		System.out.println("--------Server-------");
		Socket client = server.accept();
		System.out.println("һ��Client������");
		
		DataInputStream dis = new DataInputStream(client.getInputStream());
		String data  = dis.readUTF();
//		System.out.println(data);
		
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		
		String[] msgs = data.split("&");
		String uName = null;
		String uPwd = null;
		for (String msg : msgs) {
			if(msg.split("=")[0].equals("userName")) {
				uName = msg.split("=")[1];
			}else if (msg.split("=")[0].equals("password")) {
				uPwd = msg.split("=")[1];
			}
		}
		
		//��ͻ��˷�������ķ���
		if(uName!=null&uPwd!=null) {
			dos.writeUTF("��½�ɹ�");
			dos.flush();
			System.out.println("��½�ɹ�");
		}else {
			dos.writeUTF("�û������������");
			dos.flush();
			System.out.println("�û������������");
		}
		
		
		dis.close();
		dos.close();
	}
}
