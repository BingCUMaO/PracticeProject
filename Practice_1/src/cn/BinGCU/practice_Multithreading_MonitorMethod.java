package cn.BinGCU;

import java.util.HashMap;
import java.util.Map;

/**
 * 	高并发编程学习之并发协作
 * 
 * 	生产者与消费者模式
 * 	---管程法
 * 					(未使用jdk提供的并发容器,位于<java.util.concurrent>Jar包)
 * 	
 * 	示例：模拟生产者与消费者模式
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
 * 	衔接生产者与消费者的容器
 */
class SynCacheRegion {
	private final static  int listLength = 10;
	private Map<Integer,KeyBoard> map = new HashMap<>();

	
	//供生产者调用
	public synchronized void add(Integer key,KeyBoard kb) {
		//如果容器已被填满，将KeyBoardFactory对容器map的锁让给Comsumer去获取商品
		//然后等待容器达到未填满状态（即等待Comsumer的线程执行wait()）
		if(map.size()>=listLength) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		
		//容器未满，生产者生产
		map.put(key, kb);
		System.out.println("生产者放入产品编号为："+key+"\t的产品");
		System.out.println(map.size()+"\t<--容器容量");
		
		//将容器装入商品后，通知消费者购买
		this.notifyAll();
	}
	
	//供消费者调用
	public synchronized KeyBoard buy(Integer key) {
		//如果容器为空，将Comsumer对容器map的锁让给KeyBoardFactory去生产KeyBoard
		//然后等待生产者将产品放入容器
		if(map.size() <= 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
			}
		}
		
		//容器不为空，消费者正常购买
		KeyBoard kb = map.get(map.size());
		map.remove(key);
		System.out.println("~顾客购买了产品编号为："+key+"\t的产品");
		System.out.println(map.size()+"\t<--容器容量");
		
		//此时容器未被填满，通知生产者生产
		this.notifyAll();
		
		return kb;
	}
}



/**
 * 	生产方
 */
class KeyBoardFactory extends Thread{
	private SynCacheRegion scr = new SynCacheRegion();
	public KeyBoardFactory(SynCacheRegion scr) {		//构造器
		this.scr = scr;
	}
	
	private void produce() {
		//生产100件商品
		//商品编号为1-100
		for(int i = 1;i <= 100;i++) {
			scr.add(i, new KeyBoard(i));
//			System.out.println("-生产者放入产品编号为："+i+"\t的产品");
		}
	}
	
	@Override
	public void run() {
		produce();
	}
}



/**
 * 	消费者
 */
class Comsumer extends Thread{
	private SynCacheRegion scr = new SynCacheRegion();
	public Comsumer(SynCacheRegion scr) {			//构造器
		this.scr = scr;
	}
	
	private void buy(){
		//购买100件商品，从scr中拿走
		//商品编号为1-1000
		for(int i = 1;i<=1000;i++) {
			KeyBoard get = scr.buy(i);
//			System.out.println("-~顾客购买了产品编号为："+i+"\t的产品");
		}
	}

	@Override
	public void run() {
		buy();
	}	
}

/**
 * 	商品
 */
class KeyBoard{
	private int id;		//产品编号
	public KeyBoard(int id) {
		this.id = id;
	}
	public int getID() {
		return id;
	}
}
