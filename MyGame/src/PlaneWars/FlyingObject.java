package PlaneWars;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 	��ϵ������
 * 	������������
 * 
 * @author BinGCU
 *
 */

public abstract class FlyingObject {
	//��Ա����
	protected int width;
	protected int height;
	
	protected int x;
	protected int y;
	
	//�������״̬
	public static final byte LIFT = 0;	//���״̬
	public static final byte DEAD = 1;	//over״̬
	public static final byte REMOVE = 2;//ɾ��״̬
	public int state = LIFT;			//��ʼ��״̬Ϊ���
	
	/** ���췽��1���ṩ����(С�л�+��л�+�۷�) */
	public FlyingObject(int width, int height) {
		Random random = new Random();
		
		this.width = width;
		this.height = height;
		
		//���˳���λ��
		this.x = random.nextInt(ShootMain.SCREEN_WIDTH - this.width);
		this.y = random.nextInt(ShootMain.SCRREN_HEIGHT - this.height);
	}
	
	/** ���췽2:���ṩӢ�ۻ�+�ӵ�+��� */
	public FlyingObject(int width, int height, int x, int y) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	/** �ƶ��ķ��� */
	public abstract void move();
	
	/** ��ȡͼƬ */
	public static BufferedImage loadImage(String imagePath) {
		try {
			//ͬ���ڵ�ͼƬ��ȡ
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(imagePath));
			return img;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/** ��ȡͼƬ */
	public abstract BufferedImage getImage();
	
	/** ��ͼƬ */
	public void paintObject(Graphics g) {
		//g:����
		g.drawImage(this.getImage(),this.x,this.y,null);
	}
	
	
	/**
	 * 	���ã��жϵ�ǰ״̬
	 * 
	 * @return	state:isLife��isDead��isRemove
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
	
	/** ���Խ�� */
	public boolean outOfBounds() {
		return this.y + this.height >= ShootMain.SCRREN_HEIGHT||this.y < 0
				||this.x + this.width >= ShootMain.SCREEN_WIDTH||this.x < 0;
	}
	
	/** �ж������������������Ƿ�����ײ */
	public boolean hitEnemy(FlyingObject other) {
		//���this��ͼƬ�߽����
		int this_leftBound = this.x;
		int this_rightBound = this.x + this.width;
		int this_topBound = this.y;
		int this_bottomBound = this.y + this.height;
		
		//���other��ͼƬ�߽����
		int other_leftBound = other.x;
		int other_rightBound = other.x + other.width;
		int other_topBound = other.y;
		int other_bottomBound = other.y + other.height;
		
		//��other�ĸ��߽�Խ��this�ı߽����棬���ʾ��ײΪ��
		return other_topBound<this_bottomBound&&other_bottomBound>this_topBound
				&&other_leftBound<this_rightBound&&other_rightBound>this_leftBound;
	}
	
	/** ������over */
	public void toDie() {
		state = DEAD;	//���������state�޸�ΪDEAD
	}
	
}
