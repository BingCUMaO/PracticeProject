package MySocket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 	��½ģ��
 * @author BinGCU
 *
 */
public class TCP_Login__Client {
	public static void main(String[] args) throws IOException {
		Socket client = new Socket("localhost", 888);
		System.out.println("--------Client-------");
		
		//���Ϳͻ����׽��ֵ������
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		//��ȡ����̨��������
		BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
		
		//�����û���������
		System.out.print("�������û�����\t");
		String uName = consoleInput.readLine();
		System.out.print("���������룺\t");
		String uPwd = consoleInput.readLine();
		
		//�ر���
		consoleInput.close();
		
		//���û�����Ϣ��������Ϣд��ͻ����׽���
		dos.writeUTF("userName="+uName+'&'+"password="+uPwd);
		dos.flush();
		
		//��ȡ�ͻ����׽��ֵ����������������������½����������͸��ͻ��˵���Ϣ
		DataInputStream dis = new DataInputStream(client.getInputStream());

		System.out.println(dis.readUTF());
		dos.close();
		client.close();
	}
}
