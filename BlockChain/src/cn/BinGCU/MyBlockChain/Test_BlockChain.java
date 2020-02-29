package cn.BinGCU.MyBlockChain;

import java.security.MessageDigest;
import java.util.Date;

public class Test_BlockChain {
	public static void main(String[] args) {
		MyBlockChain myBC = new MyBlockChain("car-SA311");		//new һ���Զ������������󣬲����ô���Ԫ����
		myBC.append("car-SA312");		//��������β�����������Ϊcar-SA312������ڵ�
		myBC.append("car-SA313");
		myBC.append("car-SA314");
		myBC.append("car-SA315");
		myBC.append("car-SA316");
		myBC.append("car-SA317");
		myBC.append("car-SA318");
			
		System.out.println("����ڵ�����"+MyBlockChain.getNumberOfBlockChains());
		System.out.println("��������ڵ��Hash Code��");
		System.out.println(myBC.getAllHashCode());
	}
}

class MyBlockChain{
	public MyBlockChain nextBlockChain = null;
	public MyBlockChain firstBlockChain = this;
	
	public void setNextBlockChain(MyBlockChain newAppend) {	
		this.nextBlockChain = newAppend;
	}
	public MyBlockChain getNextBlockChain() {		
		return this.nextBlockChain;
	}
	
	public String hash;					//��ǰ�ڵ��HashCode
	public String previousHash;		//����ǰһ���ڵ��HashCode
	private String data;					//��ǰ�ڵ����������
	private long timeStamp;			//ʱ�������֤HashCode��������
	
	private static long numberOfBlockchains = 1;
	public static long getNumberOfBlockChains(){
		return numberOfBlockchains;
	}
	
	public MyBlockChain(String data) {			//������
		this.data = data;
		this.previousHash = "0";
		this.timeStamp = new Date().getTime();
		
		this.hash = getHashCode();
	}
	
	private MyBlockChain(String data, String previousHash) {		//���������أ����ʼ���Ϊprivate�������ڲ����ã�Ϊclient�����ṩ��װ
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		
		this.hash = getHashCode();
	}
	
	public String getHashCode() {		//��װ���ʼ���Ϊprivate��getSHA256Code������Ϊ�ⲿ�����ṩ����
		String getHash = getSHA256Code(previousHash+data+Long.toString(timeStamp));
		return getHash;
	}
	
	public String append(String data) {				//append����������������β������½ڵ�
		MyBlockChain newAppend = new MyBlockChain(data, this.data);
		MyBlockChain temporaryBC = firstBlockChain;
		
		for(int i = 1;i<numberOfBlockchains;i++) {
			temporaryBC = temporaryBC.getNextBlockChain();
		}
		
		temporaryBC.setNextBlockChain(newAppend);
		numberOfBlockchains++;
		
		return newAppend.hash;
	}
	
	public String getAllHashCode() {	//getAllHashCode���������ص�ǰ������������hash code������console��String������ʾ
		String allHash = "";
		MyBlockChain temporaryBC;
		temporaryBC = firstBlockChain;
		
		for(int i = 0;i<numberOfBlockchains;i++) {
			allHash += temporaryBC.hash;
			allHash += '\n';
			
			temporaryBC = temporaryBC.nextBlockChain;
		}
		
		return allHash;
	}
	
	private String getSHA256Code(String string) {		//getSHA256Code������ͨ��SHA256��ȫ�㷨��ȡHash Code�����ʼ���Ϊprivate
		MessageDigest messageDigest;
		String stringAfterEncoding = "";
		
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");	//����SHA256�����㷨
			messageDigest.update(string.getBytes("UTF-8"));
			
			stringAfterEncoding = byteToHex(messageDigest.digest());
		} catch (Exception e) {
			System.out.println("getSHA256Code is Error:"+e.getMessage());
		}
		
		return stringAfterEncoding;
	}
	
	private String byteToHex(byte[] byteArray) {		//byteToHex��������SHA-256�㷨���������Hash code���ֽ�����ת��Ϊ16���Ƶ�String����
		StringBuilder hexString = new StringBuilder();
		
		for(int i = 0;i<byteArray.length;i++) {
			String hex = Integer.toHexString(byteArray[i]&0xff);
			
			if(hexString.length() == 1) {
				hexString.append("0");
			}
			hexString.append(hex);
		}
		
		return hexString.toString();
	}
}


