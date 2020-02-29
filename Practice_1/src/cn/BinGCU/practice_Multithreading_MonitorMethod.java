package cn.BinGCU;

import java.util.HashMap;
import java.util.Map;

/**
 * 	�߲������ѧϰ֮����Э��
 * 
 * 	��������������ģʽ
 * 	---�̷ܳ�
 * 					(δʹ��jdk�ṩ�Ĳ�������,λ��<java.util.concurrent>Jar��)
 * 	
 * 	ʾ����ģ����������������ģʽ
 * 	
 * @author BinGCU
 *
 */
public class practice_Multithreading_MonitorMethod {
	public static void main(String[] args) {
		SynCacheRegion cache = new SynCacheRegion();
		Comsumer comsumer = new Comsumer(cache);
		KeyBoardFactory kbFactory = new KeyBoardFactory(cache);
		
		comsumer.start();
		kbFactory.start();

//		Map<Integer,KeyBoard> map = new HashMap<>(10);
//		for(int i = 1;i<=30;i++) {
//			map.put(i, new KeyBoard(i));
//		}
//		for(int i = 1;i<=5;i++) {
//			map.remove(map.size());
//		}
//		for(int i = 1;i<=10;i++) {
//			System.out.println(map.get(i).getID());
//		}
//		
//		System.out.println("~"+map.size());
	}
}



/**
 * 	�ν��������������ߵ�����
 */
class SynCacheRegion {
	private final static  int listLength = 10;
	private Map<Integer,KeyBoard> map = new HashMap<>();

	
	//�������ߵ���
	public synchronized void add(Integer key,KeyBoard kb) {
		//��������ѱ���������KeyBoardFactory������map�����ø�Comsumerȥ��ȡ��Ʒ
		//Ȼ��ȴ������ﵽδ����״̬�����ȴ�Comsumer���߳�ִ��wait()��
		if(map.size()>=listLength) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		
		//����δ��������������
		map.put(key, kb);
		System.out.println("�����߷����Ʒ���Ϊ��"+key+"\t�Ĳ�Ʒ");
		System.out.println(map.size()+"\t<--��������");
		
		//������װ����Ʒ��֪ͨ�����߹���
		this.notifyAll();
	}
	
	//�������ߵ���
	public synchronized KeyBoard buy(Integer key) {
		//�������Ϊ�գ���Comsumer������map�����ø�KeyBoardFactoryȥ����KeyBoard
		//Ȼ��ȴ������߽���Ʒ��������
		if(map.size() <= 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		
		//������Ϊ�գ���������������
		KeyBoard kb = map.get(map.size());
		map.remove(key);
		System.out.println("~�˿͹����˲�Ʒ���Ϊ��"+key+"\t�Ĳ�Ʒ");
		System.out.println(map.size()+"\t<--��������");
		
		//��ʱ����δ��������֪ͨ����������
		this.notifyAll();
		
		return kb;
	}
}



/**
 * 	������
 */
class KeyBoardFactory extends Thread{
	private SynCacheRegion scr = new SynCacheRegion();
	public KeyBoardFactory(SynCacheRegion scr) {		//������
		this.scr = scr;
	}
	
	private void produce() {
		//����100����Ʒ
		//��Ʒ���Ϊ1-100
		for(int i = 1;i <= 100;i++) {
			scr.add(i, new KeyBoard(i));
//			System.out.println("-�����߷����Ʒ���Ϊ��"+i+"\t�Ĳ�Ʒ");
		}
	}
	
	@Override
	public void run() {
		produce();
	}
}



/**
 * 	������
 */
class Comsumer extends Thread{
	private SynCacheRegion scr = new SynCacheRegion();
	public Comsumer(SynCacheRegion scr) {			//������
		this.scr = scr;
	}
	
	private void buy(){
		//����100����Ʒ����scr������
		//��Ʒ���Ϊ1-1000
		for(int i = 1;i<=1000;i++) {
			KeyBoard get = scr.buy(i);
//			System.out.println("-~�˿͹����˲�Ʒ���Ϊ��"+i+"\t�Ĳ�Ʒ");
		}
	}

	@Override
	public void run() {
		buy();
	}	
}

/**
 * 	��Ʒ
 */
class KeyBoard{
	private int id;		//��Ʒ���
	public KeyBoard(int id) {
		this.id = id;
	}
	public int getID() {
		return id;
	}
}
