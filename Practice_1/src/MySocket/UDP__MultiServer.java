package MySocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 	多线程实现消息对话
 * 
 * 	server：消息接收者
 */
public class UDP__MultiServer implements Runnable{
	
	private DatagramSocket datagramSocket;
	
	private DatagramPacket packet ;
	
	
	public UDP__MultiServer(int port) {
		try {
			//1、创建套接字
			this.datagramSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	private void receiveMsg() {
		//2、准备接收的包裹
		byte[] buffer = new byte[1024*1024];
		packet= new DatagramPacket(buffer, 0, buffer.length);
		while(true) {
			//3、接收
			try {
				datagramSocket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//4、解析数据
			byte[] datas = packet.getData();
			System.out.println("对方："+new String(datas));
			
			if(new String(datas).equals("Exit")) {
				break;
			}
		}
		//5、释放资源
		datagramSocket.close();
	}
	
	@Override
	public void run() {
		receiveMsg();
	}
	
	
	public static void main(String[] args) {
		new Thread(new UDP__MultiServer(1000)).start();
		new Thread(new UDP__MultiClient(555, "localhost", 2000)).start();

	}
}
