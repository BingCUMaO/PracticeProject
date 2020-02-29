package MySocket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP_Chat_Channal implements Runnable{
	private Socket client = null;
	private ServerSocket server = null;
	private String channalName = null;

	public TCP_Chat_Channal( Socket client,String channalName) {
		this.client = client;
		this.channalName = channalName;
	}
	
	public TCP_Chat_Channal( ServerSocket server,String channalName) {
		this.server = server;
		this.channalName = channalName;
		try {
			this.client = this.server.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		send();
		receive();
	}
	
	private void send() {
		new Thread(new Sender(client,channalName)).start();
	}
	
	private void receive() {
		new Thread(new Receiver(client)).start();
	}
}

class Sender implements Runnable{
	private BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
	private boolean areRunning = true;
	private Socket client = null;
	private DataOutputStream dos = null;
	private String channalName = null;
	
	public Sender(Socket target,String channalName) {
		this.client = target;
		this.channalName = channalName;
		try {
			dos = new DataOutputStream(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(areRunning) {
			try {
				dos.writeUTF(channalName+":\t"+consoleInput.readLine());
				dos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

class Receiver implements Runnable{
	private Socket client = null;
	private DataInputStream dis = null;
	private boolean areRunning = true;
	
	
	public Receiver(Socket target) {
		this.client = target;
		
		try {
			dis = new DataInputStream(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(areRunning) {
			try {
				System.out.println(dis.readUTF());
			} catch (IOException e) {
			}
		}
	}
}
