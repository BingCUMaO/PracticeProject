package MySocket;

import java.io.IOException;
import java.net.ServerSocket;

public class TCP_Chat_server {
	private static boolean areRunning = true;
	private static ServerSocket server = null;
	
	public static void main(String[] args) {
		System.out.println("-------server-------");
		
		try {
			server = new ServerSocket(888);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(areRunning) {
			new Thread(new TCP_Chat_Channal(server,"terminal")).start();
		}
	}
}

