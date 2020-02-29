package cn.BinGCU.MyBlockChain;

import java.security.MessageDigest;
import java.util.Date;

public class Test_BlockChain {
	public static void main(String[] args) {
		MyBlockChain myBC = new MyBlockChain("car-SA311");		//new 一个自定义区块链对象，并设置创纪元区块
		myBC.append("car-SA312");		//向区块链尾部添加数据名为car-SA312的区块节点
		myBC.append("car-SA313");
		myBC.append("car-SA314");
		myBC.append("car-SA315");
		myBC.append("car-SA316");
		myBC.append("car-SA317");
		myBC.append("car-SA318");
			
		System.out.println("区块节点数："+MyBlockChain.getNumberOfBlockChains());
		System.out.println("所有区块节点的Hash Code：");
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
	
	public String hash;					//当前节点的HashCode
	public String previousHash;		//储存前一个节点的HashCode
	private String data;					//当前节点的数据内容
	private long timeStamp;			//时间戳，保证HashCode的完整性
	
	private static long numberOfBlockchains = 1;
	public static long getNumberOfBlockChains(){
		return numberOfBlockchains;
	}
	
	public MyBlockChain(String data) {			//构造器
		this.data = data;
		this.previousHash = "0";
		this.timeStamp = new Date().getTime();
		
		this.hash = getHashCode();
	}
	
	private MyBlockChain(String data, String previousHash) {		//构造器重载，访问级别为private，用于内部调用，为client访问提供封装
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		
		this.hash = getHashCode();
	}
	
	public String getHashCode() {		//封装访问级别为private的getSHA256Code方法，为外部调用提供方法
		String getHash = getSHA256Code(previousHash+data+Long.toString(timeStamp));
		return getHash;
	}
	
	public String append(String data) {				//append方法，在区块链结尾处添加新节点
		MyBlockChain newAppend = new MyBlockChain(data, this.data);
		MyBlockChain temporaryBC = firstBlockChain;
		
		for(int i = 1;i<numberOfBlockchains;i++) {
			temporaryBC = temporaryBC.getNextBlockChain();
		}
		
		temporaryBC.setNextBlockChain(newAppend);
		numberOfBlockchains++;
		
		return newAppend.hash;
	}
	
	public String getAllHashCode() {	//getAllHashCode方法，返回当前区块链的所有hash code，用于console以String类型显示
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
	
	private String getSHA256Code(String string) {		//getSHA256Code方法，通过SHA256安全算法获取Hash Code，访问级别为private
		MessageDigest messageDigest;
		String stringAfterEncoding = "";
		
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");	//采用SHA256加密算法
			messageDigest.update(string.getBytes("UTF-8"));
			
			stringAfterEncoding = byteToHex(messageDigest.digest());
		} catch (Exception e) {
			System.out.println("getSHA256Code is Error:"+e.getMessage());
		}
		
		return stringAfterEncoding;
	}
	
	private String byteToHex(byte[] byteArray) {		//byteToHex方法，将SHA-256算法计算出来的Hash code从字节数组转化为16进制的String类型
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


