package MySocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;


/**
 * 	���߳�ʵ����Ϣ�Ի�
 * 
 * 	client����Ϣ������
 */
public class UDP__MultiClient implements Runnable{
	
	private DatagramSocket datagramSocket;
	private DatagramPacket packet;
	private BufferedReader br;
	private String toIP;
	private int toPort;
	
	
	public UDP__MultiClient(int port, String toIP, int toPort) {
		try {
			//1������Socket
			this.datagramSocket = new DatagramSocket(port);
			//�ӿ���̨��ȡ
			this.br = new BufferedReader(new InputStreamReader(System.in));
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		this.toIP = toIP;
		this.toPort = toPort;
	}

	private void sendMsg() {
		
		while(true) {
			String line = null;
			byte[] datas = null;
			//2��׼������Դ��װ��byte[]����		
			try {
				line= br.readLine();
				datas = line.getBytes();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			//3����װ��DatagramPacket��
			packet = new DatagramPacket(datas, datas.length, new InetSocketAddress(toIP, toPort));
			//4��ʹ��Socket�ķ��ͷ���
			try {
				datagramSocket.send(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(line.equals("Exit")) {
				break;
			}
		}
		
		//5���ͷ���Դ
		datagramSocket.close();
	}
	
	@Override
	public void run() {
		sendMsg();
	}
	
	
	
	public static void main(String[] args) {
		new Thread(new UDP__MultiServer(2000)).start();
		new Thread(new UDP__MultiClient(666, "localhost", 1000)).start();
	}
}
