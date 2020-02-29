package PlaneWars;

import java.awt.image.BufferedImage;

public class HeroPlane extends FlyingObject_Hero{
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[2];
		
		for(int i = 0; i < images.length;i++) {
			images[i] = loadImage("PlaneWars_image/hero"+i+".png");
		}
	}
	
	//heroPlane�ĳ�Ա����
	public int life;		//����ֵ
	public int doubleFire;	//����ֵ
	
	/** ���췽�� */
	public HeroPlane() {
		super(97,124,140,400);
		
		life = 6;
		doubleFire = 0;			//��������
	}
	
	/** heroPlane���ƶ���������� */
	public void moveTo(int x, int y) {
		this.x = x - this.width/2;
		this.y = y - this.height/2;
	}


	@Override
	/** �������û�в����б�����û����heroPlane�е��ƶ��и������ */
	public void move() {
		System.out.println("ͼƬ���л�");
	}
	
	
	private int index = 0;
	@Override
	public BufferedImage getImage() {
		return images[index++/10%images.length];
	}
	
	/** heroPlane�����ӵ� */
	public Bullet_Hero[] shoot() {
		int x_step = this.width/4;
		int y_step = 15;
		
		if(doubleFire > 0) {
			Bullet_Hero[] bs = new Bullet_Hero[2];
			bs[0] = new Bullet_Hero(x_step+x_step*1,this.y - y_step);
			bs[1] = new Bullet_Hero(x_step+x_step*3,this.y - y_step);
			
			doubleFire -= 2;
			
			return bs;
		}
		else {
			Bullet_Hero[] bs = new Bullet_Hero[1];
			bs[0] = new Bullet_Hero(this.x+x_step*2, this.y-y_step);
			
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
