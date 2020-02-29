package MySocket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCP_Login_MultiClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("-------client-------");
		
		new Thread(new MultiClient()).start();
	}
}

class MultiClient implements Runnable{

	@Override
	public void run() {
			//设置要连接服务器的端口号
				Socket client = null;
				try {
					client = new Socket("localhost",888);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//读取控制台的datas
				BufferedReader conReader = new BufferedReader(new InputStreamReader(System.in));
				
				System.out.println("Input user name:\t");
				String uName = null;
				try {
					uName = conReader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Input password:\t");
				String pwd = null;
				try {
					pwd = conReader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//关闭控制台输入流
				try {
					conReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				DataOutputStream dos = null;
				try {
					dos = new DataOutputStream(client.getOutputStream());
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				try {
					dos.writeUTF("uName="+uName+"@&@"+"password="+pwd);
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					dos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				DataInputStream dis = null;
				try {
					dis = new DataInputStream(client.getInputStream());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				String verification = null;
				try {
					verification = dis.readUTF();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(verification);
				
				
				try {
					dis.close();
					dos.close();
					client.close();		
				} catch (IOException e) {
					e.printStackTrace();
				}
				
	}
	
}
