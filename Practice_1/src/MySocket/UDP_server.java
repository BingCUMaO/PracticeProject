package MySocket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDP_server {
	public static final int serverPort = 999;
	public static void main(String[] args) throws Exception {
		System.out.println("���������������ݰ���...");
		
		//1��ʹ��DatagramSocket ָ���˿� �������ն�
		DatagramSocket server = new DatagramSocket(serverPort);
		
		//2��׼����������װ��DatagramPacket����
		byte[] container = new byte[1024*1024];
		DatagramPacket dPacket = new DatagramPacket(container, 0, container.length);
		
		//3������ʽ���հ�����receive(DatagramPacket dp);
		server.receive(dPacket);
		
		//4����������
		byte[] datas = dPacket.getData();
		System.out.println(new String(datas,0,datas.length));
		
		//5���ͷ���Դ
		server.close();
		
		System.out.println("�ѻ�ȡ");
	}
	
}
