package MySocket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import MySocket.UDP_server;

public class UDP_client {
	public static final int clientPort = 666;
	public static void main(String[] args) throws Exception {
		System.out.println("�ͻ��ˣ�����������...");

		//1��ʹ��DatagramSocket ָ���˿ڣ��������Ͷ�
		DatagramSocket client = new DatagramSocket(clientPort);
		
		//2��׼�����ݣ���Ҫת��Ϊbyte[]����
		byte[] datas = "<���ݰ�����>".getBytes();
		
		//3����װ��DatagramPacket������������Ҫָ��Ŀ�ĵض˿�
		DatagramPacket dPacket = new DatagramPacket(datas, datas.length,
				new InetSocketAddress("localhost",UDP_server.serverPort));
		
		//4�����Ͱ���send(DatagramPacket dp);
		client.send(dPacket);
		
		//5���ͷ���Դ
		client.close();
		
		System.out.println("�ѷ���");
	}
}
