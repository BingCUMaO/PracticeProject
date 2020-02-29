package MySocket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 	登陆模拟
 * @author BinGCU
 *
 */
public class TCP_Login__Client {
	public static void main(String[] args) throws IOException {
		Socket client = new Socket("localhost", 888);
		System.out.println("--------Client-------");
		
		//发送客户端套接字的输出流
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		//获取控制台的输入流
		BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
		
		//输入用户名与密码
		System.out.print("请输入用户名：\t");
		String uName = consoleInput.readLine();
		System.out.print("请输入密码：\t");
		String uPwd = consoleInput.readLine();
		
		//关闭流
		consoleInput.close();
		
		//将用户名信息与密码信息写入客户端套接字
		dos.writeUTF("userName="+uName+'&'+"password="+uPwd);
		dos.flush();
		
		//获取客户端套接字的输入流，用来接收请求登陆后服务器发送给客户端的信息
		DataInputStream dis = new DataInputStream(client.getInputStream());

		System.out.println(dis.readUTF());
		dos.close();
		client.close();
	}
}
