
import java.awt.*;
import javax.swing.*;


public class BallGame extends JFrame {
	
	
	Image ball = Toolkit.getDefaultToolkit().getImage("image/ball.png");
	Image desk = Toolkit.getDefaultToolkit().getImage("image/desk.jpg");
	double x = 100;
	double y = 100;
	
	double degree = 3.14/3;		//��ʾ���ȣ��˴�Ϊ60��
	
	//��ͼƬ
	public void paint(Graphics g) {
		System.out.println("���ڱ�����һ��");
		
		g.drawImage(desk,0,0,null);
		g.drawImage(ball,(int)x,(int)y,null);
		
		x = x + 10*Math.cos(degree);
		y = y + 10*Math.cos(degree);       
		
		//�������±߽�
		if(y > 400 - 40 - 30||y < 40 + 40)
		{
			//500�Ǵ��ڸ߶ȣ�40�����ӱ߿�30����ֱ�������һ��40�Ǳ������ĸ߶�
			degree = -degree;
		}
		
		//�������ұ߽�
		if(x < 40||x > 856 - 40 - 30)
		{
			degree = 3.14 - degree;
		}
	
	}
	
	
	//���д���
	public void launchFrame() {
		setSize(856,500);
		setLocation(100,100);
		setVisible(true);
		
		
		//�ػ����ڣ�ÿ�뻭25��
		while(true)
		{
			repaint();
			try {
				Thread.sleep(40);	//40ms, 1m = 1000ms,��Լ 1 �뻭25�δ���
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args)
	{
		BallGame game = new BallGame();
		
		game.launchFrame();
	}
}
