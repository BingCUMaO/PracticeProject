package MySocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 	���߳�ʵ����Ϣ�Ի�
 * 
 * 	server����Ϣ������
 */
public class UDP__MultiServer implements Runnable{
	
	private DatagramSocket datagramSocket;
	
	private DatagramPacket packet ;
	
	
	public UDP__MultiServer(int port) {
		try {
			//1�������׽���
			this.datagramSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	private void receiveMsg() {
		//2��׼�����յİ���
		byte[] buffer = new byte[1024*1024];
		packet= new DatagramPacket(buffer, 0, buffer.length);
		while(true) {
			//3������
			try {
				datagramSocket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//4����������
			byte[] datas = packet.getData();
			System.out.println("�Է���"+new String(datas));
			
			if(new String(datas).equals("Exit")) {
				break;
			}
		}
		//5���ͷ���Դ
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
