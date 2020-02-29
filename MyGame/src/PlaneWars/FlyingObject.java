package PlaneWars;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 	关系：父类
 * 	所属：飞行物
 * 
 * @author BinGCU
 *
 */

public abstract class FlyingObject {
	//成员变量
	protected int width;
	protected int height;
	
	protected int x;
	protected int y;
	
	//设计三种状态
	public static final byte LIFT = 0;	//存活状态
	public static final byte DEAD = 1;	//over状态
	public static final byte REMOVE = 2;//删除状态
	public int state = LIFT;			//初始化状态为存活
	
	/** 构造方法1：提供敌人(小敌机+大敌机+蜜蜂) */
	public FlyingObject(int width, int height) {
		Random random = new Random();
		
		this.width = width;
		this.height = height;
		
		//敌人出现位置
		this.x = random.nextInt(ShootMain.SCREEN_WIDTH - this.width);
		this.y = random.nextInt(ShootMain.SCRREN_HEIGHT - this.height);
	}
	
	/** 构造方2:：提供英雄机+子弹+天空 */
	public FlyingObject(int width, int height, int x, int y) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	/** 移动的方法 */
	public abstract void move();
	
	/** 读取图片 */
	public static BufferedImage loadImage(String imagePath) {
		try {
			//同包内的图片读取
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(imagePath));
			return img;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/** 获取图片 */
	public abstract BufferedImage getImage();
	
	/** 画图片 */
	public void paintObject(Graphics g) {
		//g:画笔
		g.drawImage(this.getImage(),this.x,this.y,null);
	}
	
	
	/**
	 * 	作用：判断当前状态
	 * 
	 * @return	state:isLife、isDead、isRemove
	 */
	public boolean isLife() {
		return state == LIFT;
	}
	public boolean isDead() {
		return state == DEAD;
	}
	public boolean isRemove() {
		return state == REMOVE;
	}
	
	/** 检测越界 */
	public boolean outOfBounds() {
		return this.y + this.height >= ShootMain.SCRREN_HEIGHT||this.y < 0
				||this.x + this.width >= ShootMain.SCREEN_WIDTH||this.x < 0;
	}
	
	/** 判断自身与其他飞行物是否发生碰撞 */
	public boolean hitEnemy(FlyingObject other) {
		//求出this的图片边界参数
		int this_leftBound = this.x;
		int this_rightBound = this.x + this.width;
		int this_topBound = this.y;
		int this_bottomBound = this.y + this.height;
		
		//求出other的图片边界参数
		int other_leftBound = other.x;
		int other_rightBound = other.x + other.width;
		int other_topBound = other.y;
		int other_bottomBound = other.y + other.height;
		
		//当other的各边界越过this的边界里面，则表示碰撞为真
		return other_topBound<this_bottomBound&&other_bottomBound>this_topBound
				&&other_leftBound<this_rightBound&&other_rightBound>this_leftBound;
	}
	
	/** 飞行物over */
	public void toDie() {
		state = DEAD;	//将飞行物的state修改为DEAD
	}
	
}
