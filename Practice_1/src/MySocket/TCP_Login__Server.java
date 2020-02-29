package MySocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 	模拟登陆服务器
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
		System.out.println("一个Client已连接");
		
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
		
		//向客户端发送请求的反馈
		if(uName!=null&uPwd!=null) {
			dos.writeUTF("登陆成功");
			dos.flush();
			System.out.println("登陆成功");
		}else {
			dos.writeUTF("用户名或密码错误");
			dos.flush();
			System.out.println("用户名或密码错误");
		}
		
		
		dis.close();
		dos.close();
	}
}
