package MySocket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDP_server {
	public static final int serverPort = 999;
	public static void main(String[] args) throws Exception {
		System.out.println("服务器，接收数据包中...");
		
		//1、使用DatagramSocket 指定端口 创建接收端
		DatagramSocket server = new DatagramSocket(serverPort);
		
		//2、准备容器，封装成DatagramPacket包裹
		byte[] container = new byte[1024*1024];
		DatagramPacket dPacket = new DatagramPacket(container, 0, container.length);
		
		//3、阻塞式接收包裹：receive(DatagramPacket dp);
		server.receive(dPacket);
		
		//4、分析数据
		byte[] datas = dPacket.getData();
		System.out.println(new String(datas,0,datas.length));
		
		//5、释放资源
		server.close();
		
		System.out.println("已获取");
	}
	
}
