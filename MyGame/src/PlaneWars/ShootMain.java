package PlaneWars;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShootMain extends JPanel{
	public static final int SCREEN_WIDTH	= 400;
	public static final int SCRREN_HEIGHT 	= 700;
	
	/**
	 * 	起始状态：START/RUNNING/PAUSE/GAME OVER
	 * 	
	 * 	默认起始状态：
	 * 
	 * 		鼠标点击事件：
	 * 		开始--运行		结束--开始
	 * 
	 * 		鼠标移动事件：
	 * 		启动状态：	结束--启动
	 * 		暂停状态：	鼠标从屏幕里划到窗口外
	 * 		运行状态：	从窗口外到窗口内
	 * 		结束状态：	heroPlane的生命值为0
	 */
	public static final byte START			=	0;
	public static final byte RUNNING 		=	1;
	public static final byte PAUSE			=	2;
	public static final byte GAME_OVER		=	3;
	public static final byte DEFAULT_STATE 	=	START;	//默认状态为START状态
	
	public byte state = DEFAULT_STATE;
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage gameOver;
	
	//静态代码块
	static {
		start 		= Background.loadImage("PlaneWars_images/start.png");
		pause 		= Background.loadImage("PlaneWars_images/pause.png");
		gameOver 	= Background.loadImage("PlaneWars_imgaes/game over.png");
	}
	
	private Background 		bg 				= new Background();
	private HeroPlane 		heroPlane 		= new HeroPlane();
	private FlyingObject[] 	flys 			= null;
	private Bullet[]		bullets			= null;
	
	/** 产生敌机对象 */
	public FlyingObject appearOneEnemy() {
		Random rand = new Random();
		int typeOfEnemy = rand.nextInt(20);
		
		//挑选敌机类型：A/B/C
		if(typeOfEnemy < 5) {
			return new EnemyB();
		}
		else if (typeOfEnemy < 12) {
			return new EnemyC();
		}
		else {
			return new EnemyA();
		}
	}
	
	/** 实现敌机入场，即将产生的敌机加入到FlyingObject类型的flys数组中 */
	private int enterIndex = 0;
	public void enemyEnterAction() {
		enterIndex++;
		if(enterIndex%40 == 0) {
			FlyingObject fo = appearOneEnemy();
			
			flys = Arrays.copyOf(flys, flys.length+1);
			flys[flys.length-1] = fo;
		}
	}
	
	/** 子弹入场 */
	private int bulletsIndex = 0;
	public void shootAction() {
		bulletsIndex++;
		if(bulletsIndex%40 == 0) {
			Bullet[] bs = heroPlane.shoot();
			bullets = Arrays.copyOf(bullets, bullets.length+bs.length);
			
			//将产生的子弹数组放在源子弹数组的最后一个元素
			System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length);
		}
	}
	
	/** 飞行物与子弹移动 */
	public void moveAction() {
		bg.move();
		for(int i = 0; i < flys.length;i++) {
			flys[i].move();
		}
		for(int i = 0;i < bullets.length;i++) {
			bullets[i].move();
		}
	}
	
	/** 删除越界的飞行物 */
	public void outOfBoundAction() {
		int index = 0;		//存放不越界数组的个数
		
		//新建不越界的敌人数组
		FlyingObject[] flysLive = new FlyingObject[flys.length];
		for(int i = 0; i < flys.length; i++) {
			FlyingObject f = flys[i];	//获取到每一个敌人
			
			//如果敌人没越界
			if(!f.outOfBounds()) {
				flysLive[index] = f;
				index++;
			}
		}

		flys = Arrays.copyOf(flysLive, index);
		
		index = 0;
		Bullet[] bulletLive = new Bullet[bullets.length];
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if (!b.outOfBounds()) {
				bulletLive[index] = b;
				index++;
			}
		}
		bullets = Arrays.copyOf(bulletLive, index);
	}

	/**子弹与敌人的碰撞*/
	int score = 0;//玩家的得分记录
	public void bulletBangAction(){
		//遍历所有的子弹
		
		for (int i = 0; i < bullets.length; i++) {
			//获取每一个子弹
			Bullet b = bullets[i];
			//遍历所有的敌人
			for (int j = 0; j < flys.length; j++) {
				//获取每一个敌人
				FlyingObject f = flys[j];
				//判断碰撞
				if (f.isLife() && b.isLife() && f.hitEnemy(b)) {
					f.toDie();//敌人over
					b.toDie();//子弹over
					
					if (f instanceof Enemy) {//如果撞上的是敌人能得分
						Enemy e = (Enemy)f;
						score += e.getScore();
					}
					
				}
			}
		}
	}


	/**英雄机与敌人发生碰撞*/
	public void heroBangAction(){
		/*
		 * 思路：
		 * 1）借助FlyingObject中的hit()方法检测碰撞
		 * 2）借助FlyingObject中的goDead()方法over
		 * 3）在Hero类中设计一个方法实现碰撞之后
		 * substractLife()减少生命， clearDoubleFire()火力清0
		 * 4) 在run()方法中实现英雄机与敌人发生碰撞
		 * heroBangAction();
		 * 遍历敌人获取到每一个敌人：
		 * 判断撞上了：
		 * 敌人over,英雄机减少生命，清空火力
		 */
		//遍历所有的敌人
		for (int i = 0; i < flys.length; i++) {
			//获取每一个敌人
			FlyingObject f = flys[i];
			if (heroPlane.isLife() && f.isLife() && f.hitEnemy(heroPlane)) {
				f.toDie();
				heroPlane.subLife();//减少生命
				heroPlane.clearDoubleFire();//清空火力
			}
		}
	}
	/**检测游戏是否结束*/
	public void checkGameOverAction(){
		//判断英雄机的生命值，
		//如果小于0的话，游戏结束，修改状态
		if(heroPlane.getLife() <= 0){
			state = GAME_OVER;
		}
	}
	/** 测试方法 */
	public void action() {// 数组越界、数组空指针异常
		//鼠标适配器
		MouseAdapter ma = new MouseAdapter() {
			/**重写鼠标的移动事件*/
			public void mouseMoved(MouseEvent e){
				int x = e.getX();
				int y = e.getY();
				hero.movedTo(x, y);
			}
			
			/**重写鼠标的点击事件*/
			@Override
			public void mouseClicked(MouseEvent e) {
				//根据当前状态的不同，进行相应的处理
				switch (state) {
				case START:
					state = RUNNING;
					break;
				case GAME_OVER:
					score = 0;
					bg = new Background();
					heroPlane = new HeroPlane();
					flys = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START;//修改状态为开始状态
					break;
				}
			}
			/**重写鼠标的移入事件*/
			@Override
			public void mouseEntered(MouseEvent e) {
				if (state == PAUSE) {
					state = RUNNING;
				}
			}
			/**重写鼠标的移出事件*/
			@Override
			public void mouseExited(MouseEvent e) {
				if (state == RUNNING) {
					state = PAUSE;
				}
			}
		};
		this.addMouseListener(ma);//处理鼠标的操作事件
		this.addMouseMotionListener(ma);//处理鼠标的移动事件
		// 定时器对象
		Timer timer = new Timer();
		int inters = 10;
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (state == RUNNING) {
					enemyEnterAction();// 敌人入场
					shootAction();// 子弹入场
					moveAction();// 飞行物移动
					outOfBoundAction();//删除越界的飞行物
					bulletBangAction();//子弹与敌人的碰撞
					heroBangAction();//英雄机与敌人发生碰撞
					checkGameOverAction();//检测游戏是都结束
				}
				repaint();// 重绘，调用paint方法
			}
		}, inters, inters);// 计划任务
		/*
		 * 7.30 20 第一次 10 第一次---第二次
		 */
	}

	/** 画对象 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		bg.paintObject(g);
		heroPlane.paintObject(g);
		// 画敌人
		for (int i = 0; i < flys.length; i++) {
			flys[i].paintObject(g);
		}
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].paintObject(g);
		}
		
		g.drawString("分数：" + score, 30, 60);
		g.drawString("生命：" + heroPlane.getLife(), 30, 80);
		
		switch (state) {//根据不同状态画不同的状态图
		case START://开始状态的启动图
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE://暂停状态的图
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER://游戏结束的时候状态图
			g.drawImage(gameOver, 0, 0, null);
			break;
		}
	}

	
	public static void main(String[] args) {
		ShootMain sm = new ShootMain();
		JFrame jf = new JFrame();
		jf.add(sm);// 将画布装在窗体上
		jf.setSize(SCREEN_WIDTH, SCRREN_HEIGHT);// 窗体的大小
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);// 居中显示
		jf.setVisible(true);
		sm.action();

	}
}
