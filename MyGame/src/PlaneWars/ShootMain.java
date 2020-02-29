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
	 * 	��ʼ״̬��START/RUNNING/PAUSE/GAME OVER
	 * 	
	 * 	Ĭ����ʼ״̬��
	 * 
	 * 		������¼���
	 * 		��ʼ--����		����--��ʼ
	 * 
	 * 		����ƶ��¼���
	 * 		����״̬��	����--����
	 * 		��ͣ״̬��	������Ļ�ﻮ��������
	 * 		����״̬��	�Ӵ����⵽������
	 * 		����״̬��	heroPlane������ֵΪ0
	 */
	public static final byte START			=	0;
	public static final byte RUNNING 		=	1;
	public static final byte PAUSE			=	2;
	public static final byte GAME_OVER		=	3;
	public static final byte DEFAULT_STATE 	=	START;	//Ĭ��״̬ΪSTART״̬
	
	public byte state = DEFAULT_STATE;
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage gameOver;
	
	//��̬�����
	static {
		start 		= Background.loadImage("PlaneWars_images/start.png");
		pause 		= Background.loadImage("PlaneWars_images/pause.png");
		gameOver 	= Background.loadImage("PlaneWars_imgaes/game over.png");
	}
	
	private Background 		bg 				= new Background();
	private HeroPlane 		heroPlane 		= new HeroPlane();
	private FlyingObject[] 	flys 			= null;
	private Bullet[]		bullets			= null;
	
	/** �����л����� */
	public FlyingObject appearOneEnemy() {
		Random rand = new Random();
		int typeOfEnemy = rand.nextInt(20);
		
		//��ѡ�л����ͣ�A/B/C
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
	
	/** ʵ�ֵл��볡�����������ĵл����뵽FlyingObject���͵�flys������ */
	private int enterIndex = 0;
	public void enemyEnterAction() {
		enterIndex++;
		if(enterIndex%40 == 0) {
			FlyingObject fo = appearOneEnemy();
			
			flys = Arrays.copyOf(flys, flys.length+1);
			flys[flys.length-1] = fo;
		}
	}
	
	/** �ӵ��볡 */
	private int bulletsIndex = 0;
	public void shootAction() {
		bulletsIndex++;
		if(bulletsIndex%40 == 0) {
			Bullet[] bs = heroPlane.shoot();
			bullets = Arrays.copyOf(bullets, bullets.length+bs.length);
			
			//���������ӵ��������Դ�ӵ���������һ��Ԫ��
			System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length);
		}
	}
	
	/** ���������ӵ��ƶ� */
	public void moveAction() {
		bg.move();
		for(int i = 0; i < flys.length;i++) {
			flys[i].move();
		}
		for(int i = 0;i < bullets.length;i++) {
			bullets[i].move();
		}
	}
	
	/** ɾ��Խ��ķ����� */
	public void outOfBoundAction() {
		int index = 0;		//��Ų�Խ������ĸ���
		
		//�½���Խ��ĵ�������
		FlyingObject[] flysLive = new FlyingObject[flys.length];
		for(int i = 0; i < flys.length; i++) {
			FlyingObject f = flys[i];	//��ȡ��ÿһ������
			
			//�������ûԽ��
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

	/**�ӵ�����˵���ײ*/
	int score = 0;//��ҵĵ÷ּ�¼
	public void bulletBangAction(){
		//�������е��ӵ�
		
		for (int i = 0; i < bullets.length; i++) {
			//��ȡÿһ���ӵ�
			Bullet b = bullets[i];
			//�������еĵ���
			for (int j = 0; j < flys.length; j++) {
				//��ȡÿһ������
				FlyingObject f = flys[j];
				//�ж���ײ
				if (f.isLife() && b.isLife() && f.hitEnemy(b)) {
					f.toDie();//����over
					b.toDie();//�ӵ�over
					
					if (f instanceof Enemy) {//���ײ�ϵ��ǵ����ܵ÷�
						Enemy e = (Enemy)f;
						score += e.getScore();
					}
					
				}
			}
		}
	}


	/**Ӣ�ۻ�����˷�����ײ*/
	public void heroBangAction(){
		/*
		 * ˼·��
		 * 1������FlyingObject�е�hit()���������ײ
		 * 2������FlyingObject�е�goDead()����over
		 * 3����Hero�������һ������ʵ����ײ֮��
		 * substractLife()���������� clearDoubleFire()������0
		 * 4) ��run()������ʵ��Ӣ�ۻ�����˷�����ײ
		 * heroBangAction();
		 * �������˻�ȡ��ÿһ�����ˣ�
		 * �ж�ײ���ˣ�
		 * ����over,Ӣ�ۻ�������������ջ���
		 */
		//�������еĵ���
		for (int i = 0; i < flys.length; i++) {
			//��ȡÿһ������
			FlyingObject f = flys[i];
			if (heroPlane.isLife() && f.isLife() && f.hitEnemy(heroPlane)) {
				f.toDie();
				heroPlane.subLife();//��������
				heroPlane.clearDoubleFire();//��ջ���
			}
		}
	}
	/**�����Ϸ�Ƿ����*/
	public void checkGameOverAction(){
		//�ж�Ӣ�ۻ�������ֵ��
		//���С��0�Ļ�����Ϸ�������޸�״̬
		if(heroPlane.getLife() <= 0){
			state = GAME_OVER;
		}
	}
	/** ���Է��� */
	public void action() {// ����Խ�硢�����ָ���쳣
		//���������
		MouseAdapter ma = new MouseAdapter() {
			/**��д�����ƶ��¼�*/
			public void mouseMoved(MouseEvent e){
				int x = e.getX();
				int y = e.getY();
				hero.movedTo(x, y);
			}
			
			/**��д���ĵ���¼�*/
			@Override
			public void mouseClicked(MouseEvent e) {
				//���ݵ�ǰ״̬�Ĳ�ͬ��������Ӧ�Ĵ���
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
					state = START;//�޸�״̬Ϊ��ʼ״̬
					break;
				}
			}
			/**��д���������¼�*/
			@Override
			public void mouseEntered(MouseEvent e) {
				if (state == PAUSE) {
					state = RUNNING;
				}
			}
			/**��д�����Ƴ��¼�*/
			@Override
			public void mouseExited(MouseEvent e) {
				if (state == RUNNING) {
					state = PAUSE;
				}
			}
		};
		this.addMouseListener(ma);//�������Ĳ����¼�
		this.addMouseMotionListener(ma);//���������ƶ��¼�
		// ��ʱ������
		Timer timer = new Timer();
		int inters = 10;
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (state == RUNNING) {
					enemyEnterAction();// �����볡
					shootAction();// �ӵ��볡
					moveAction();// �������ƶ�
					outOfBoundAction();//ɾ��Խ��ķ�����
					bulletBangAction();//�ӵ�����˵���ײ
					heroBangAction();//Ӣ�ۻ�����˷�����ײ
					checkGameOverAction();//�����Ϸ�Ƕ�����
				}
				repaint();// �ػ棬����paint����
			}
		}, inters, inters);// �ƻ�����
		/*
		 * 7.30 20 ��һ�� 10 ��һ��---�ڶ���
		 */
	}

	/** ������ */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		bg.paintObject(g);
		heroPlane.paintObject(g);
		// ������
		for (int i = 0; i < flys.length; i++) {
			flys[i].paintObject(g);
		}
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].paintObject(g);
		}
		
		g.drawString("������" + score, 30, 60);
		g.drawString("������" + heroPlane.getLife(), 30, 80);
		
		switch (state) {//���ݲ�ͬ״̬����ͬ��״̬ͼ
		case START://��ʼ״̬������ͼ
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE://��ͣ״̬��ͼ
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER://��Ϸ������ʱ��״̬ͼ
			g.drawImage(gameOver, 0, 0, null);
			break;
		}
	}

	
	public static void main(String[] args) {
		ShootMain sm = new ShootMain();
		JFrame jf = new JFrame();
		jf.add(sm);// ������װ�ڴ�����
		jf.setSize(SCREEN_WIDTH, SCRREN_HEIGHT);// ����Ĵ�С
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);// ������ʾ
		jf.setVisible(true);
		sm.action();

	}
}
