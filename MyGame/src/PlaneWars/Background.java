package PlaneWars;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Background extends FlyingObject{
	private static BufferedImage bg;
	static {
		bg = loadImage("PlaneWars_images/背景.jpg");
	}
	
	private int speed;					//背景图片移动的速度
	public int position_nextImage_y;	//接下来一张背景图的位置
	
	/** 构造方法 */
	public Background() {
		super(ShootMain.SCREEN_WIDTH, ShootMain.SCRREN_HEIGHT, 0, 0);
		speed = 1;
		position_nextImage_y = -ShootMain.SCRREN_HEIGHT;
	}
	
	/** 背景移动 */
	public void move() {
		this.y += speed;
		position_nextImage_y += speed;
		
		if(this.y >= ShootMain.SCRREN_HEIGHT) {
			this.y = -ShootMain.SCRREN_HEIGHT;
		}
		if(position_nextImage_y >= ShootMain.SCRREN_HEIGHT) {
			position_nextImage_y = -ShootMain.SCRREN_HEIGHT;
		}
	}

	@Override
	public BufferedImage getImage() {
		return bg;
	}

	//重写父类中的paintObject方法
	@Override
	public void paintObject(Graphics g) {
		g.drawImage(getImage(), this.x, this.y, null);
		g.drawImage(getImage(), this.x, position_nextImage_y, null);
	}
}
