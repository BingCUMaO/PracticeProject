package PlaneWars;

import java.awt.image.BufferedImage;

public class Bullet_Hero extends Bullet{
	private static BufferedImage images;
	static {
		images = loadImage("PlaneWars_images/bullet.png");
	}
	
	private int speed;	//�ӵ��ٶ�
	
	/** ������ */
	public Bullet_Hero(int x, int y) {
		super(8, 14, x, y);
		speed = 2;
	}

	@Override
	public void move() {
		this.y -= speed;
	}

	@Override
	public BufferedImage getImage() {
		if(isLife()) {
			return images;
		}
		else if(isDead()) {
			state = REMOVE;
		}
		return null;
	}

	@Override
	public boolean outOfBounds() {
		return this.y + this.height <= 0||this.y>=ShootMain.SCREEN_WIDTH;	//Խ��ʱ���ж�����Ӧ��bulletͼƬ���±߽�
	}
}
