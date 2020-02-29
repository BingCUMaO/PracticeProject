package MySocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class TCP_Login_MultiServer {
	@SuppressWarnings("serial")
	private static HashMap<String, String> userList = new HashMap<String, String>() {
		{
			put("BinGCU", "brilliant");
			put("_A", "no");
		}
	};
	
	public static void main(String[] args) throws IOException {
		System.out.println("-------server-------");

		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(888);
		
		Socket client = null;
		DataInputStream dis = null;
		DataOutputStream dos = null;
		while(true) {
			client = server.accept();
			System.out.println("Client linked");
			
			//��Socket�н��յ���client���͵�datas
			dis = new DataInputStream(client.getInputStream());
			String data = dis.readUTF();
			System.out.println("data:"+data);
			
			boolean arePass = verify(data);
			//��Socket�з�����֤��Ϣ��client
			dos = new DataOutputStream(client.getOutputStream());
			
			if(arePass) {
				System.out.println("�ɹ���½");
				dos.writeUTF("Pass");
				dos.flush();
			}else {
				System.out.println("��½ʧ��");
				dos.writeUTF("Can not to pass");
				dos.flush();
			}
		}
	}
	
	private static boolean verify(String datas) {
		String[] msgs = datas.split("@&@");
		String uName = null;
		String pwd = null;
		for(String msg:msgs) {
			String[] accoutMsg = msg.split("=");
			if(accoutMsg[0].equals("uName")) {
				uName = accoutMsg[1];
			}
			else if(accoutMsg[0].equals("password")) {
				pwd = accoutMsg[1];
			}
			else {
				System.out.println("Error to pass");
				return false;
			}
		}
		
		//��֤�û��������������userList��
		if(userList.containsKey(uName)) 
			if(userList.get(uName).equals(pwd))
				return true;
		
		return false;
	}
	
	
}
