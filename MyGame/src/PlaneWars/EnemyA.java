package PlaneWars;

import java.awt.image.BufferedImage;

/**
 * 	大敌机
 * 
 * @author BinGCU
 */

public class EnemyA extends FlyingObject_Enemy implements Enemy{
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[5];
		for(int i = 0;i < images.length;i++) {
			images[i] = loadImage("PlaneWars_images/enemyA"+i+".png");
		}
	}
	
	
	private int life;		//生命值
	private int doubleFire;	//火力值
	private int speed;	//敌机移动的速度
	
	/** 构造方法 */
	public EnemyA() {
		super(99, 89);
		this.speed = 2;
	}
	
	public void move() {
		this.y += speed;
	}

	@Override
	public int getScore() {
		return 3;	//EnemyA击中获3分
	}

	private int index = 1;	//得到图片
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
		
		/*
		 * images[index++]; 
		 * index = 1; 
		 * 10M images[1] index = 2 返回images[1] 
		 * 20M images[2] index = 3 返回images[2] 
		 * 30M images[3] index = 4 返回images[3]
		 * 40M images[4] index = 5 返回images[4]
		 */

		return null;
	}
	
	public Bullet_Enemy[] shoot() {
		int x_step = this.width/4;
		int y_step = 15;
		
		if(doubleFire > 0) {
			Bullet_Enemy[] bs = new Bullet_Enemy[2];
			bs[0] = new Bullet_Enemy(x_step+x_step*1,this.y - y_step);
			bs[1] = new Bullet_Enemy(x_step+x_step*3,this.y - y_step);
			
			doubleFire -= 2;
			
			return bs;
		}
		else {
			Bullet_Enemy[] bs = new Bullet_Enemy[1];
			bs[0] = new Bullet_Enemy(this.x+x_step*2, this.y-y_step);
			
			return bs;
		}
	}
	
	public void addDoubleFire() {
		doubleFire += 10;
	}
	
	public void addLife() {
		life += 1;
	}
	
	public int getLife() {
		return life;
	}
	
	public void subLife() {
		life--;
	}
	
	public void clearDoubleFire() {
		doubleFire = 0;
	}
	
}
