package PlaneWars;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 	敌机类型：蜜蜂
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
	
	private int speed_x;	//蜜蜂x轴上的移动速度
	private int speed_y;	//蜜蜂y轴上的移动速度
	private int award;		//击中蜜蜂的奖励
	
	/** 构造方法 */
	public EnemyC() {
		super(29, 39);
		this.speed_x = 1;
		this.speed_y = 2;
		award = new Random().nextInt(2);		//生成范围[0,2)
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
