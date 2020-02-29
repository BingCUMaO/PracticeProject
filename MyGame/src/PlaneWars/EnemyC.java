package PlaneWars;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 	�л����ͣ��۷�
 * 	
 * @author BinGCU
 *
 */

public class EnemyC extends FlyingObject implements Enemy{
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[5];
		for(int i = 0;i<images.length;i++) {
			images[i] = loadImage("PlaneWars_images/enemyC"+i+".png");
		}
	}
	
	private int speed_x;	//�۷�x���ϵ��ƶ��ٶ�
	private int speed_y;	//�۷�y���ϵ��ƶ��ٶ�
	private int award;		//�����۷�Ľ���
	
	/** ���췽�� */
	public EnemyC() {
		super(29, 39);
		this.speed_x = 1;
		this.speed_y = 2;
		award = new Random().nextInt(2);		//���ɷ�Χ[0,2)
	}
	
	public void move() {
		this.x += speed_x;
		this.y += speed_y;
		
		if(this.x < 0 || this.x > ShootMain.SCREEN_WIDTH - this.width) {
			speed_x *= -1;
		}
	}

	@Override
	public int getScore() {
		return award;
	}

	private int index = 1;
	@Override
	public BufferedImage getImage() {
		if(isLife()) {
			return images[0];
		}
		else if (isDead()) {
			BufferedImage img = images[index];
			index++;
			
			if(index == images.length) {
				state = REMOVE;
			}
			return img;
		}
		return null;
	}
	
	
}
