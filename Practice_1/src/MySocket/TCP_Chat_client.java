package MySocket;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class TCP_Chat_client {
	public static void main(String[] args) {
		System.out.println("-------client-------");
		
		try {
			new Thread(new TCP_Chat_Channal(new Socket("localhost",888),String.valueOf(new Random().nextInt()))).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

