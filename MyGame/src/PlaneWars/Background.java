package PlaneWars;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Background extends FlyingObject{
	private static BufferedImage bg;
	static {
		bg = loadImage("PlaneWars_images/����.jpg");
	}
	
	private int speed;					//����ͼƬ�ƶ����ٶ�
	public int position_nextImage_y;	//������һ�ű���ͼ��λ��
	
	/** ���췽�� */
	public Background() {
		super(ShootMain.SCREEN_WIDTH, ShootMain.SCRREN_HEIGHT, 0, 0);
		speed = 1;
		position_nextImage_y = -ShootMain.SCRREN_HEIGHT;
	}
	
	/** �����ƶ� */
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

	//��д�����е�paintObject����
	@Override
	public void paintObject(Graphics g) {
		g.drawImage(getImage(), this.x, this.y, null);
		g.drawImage(getImage(), this.x, position_nextImage_y, null);
	}
}
