package MySocket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import MySocket.UDP_server;

public class UDP_client {
	public static final int clientPort = 666;
	public static void main(String[] args) throws Exception {
		System.out.println("客户端，发送数据中...");

		//1、使用DatagramSocket 指定端口，创建发送端
		DatagramSocket client = new DatagramSocket(clientPort);
		
		//2、准备数据，需要转化为byte[]类型
		byte[] datas = "<数据包内容>".getBytes();
		
		//3、封装成DatagramPacket包裹，并且需要指定目的地端口
		DatagramPacket dPacket = new DatagramPacket(datas, datas.length,
				new InetSocketAddress("localhost",UDP_server.serverPort));
		
		//4、发送包裹send(DatagramPacket dp);
		client.send(dPacket);
		
		//5、释放资源
		client.close();
		
		System.out.println("已发送");
	}
}
