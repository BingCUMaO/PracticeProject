
import java.awt.*;
import javax.swing.*;


public class BallGame extends JFrame {
	
	
	Image ball = Toolkit.getDefaultToolkit().getImage("image/ball.png");
	Image desk = Toolkit.getDefaultToolkit().getImage("image/desk.jpg");
	double x = 100;
	double y = 100;
	
	double degree = 3.14/3;		//表示弧度，此处为60度
	
	//贴图片
	public void paint(Graphics g) {
		System.out.println("窗口被画了一次");
		
		g.drawImage(desk,0,0,null);
		g.drawImage(ball,(int)x,(int)y,null);
		
		x = x + 10*Math.cos(degree);
		y = y + 10*Math.cos(degree);       
		
		//碰到上下边界
		if(y > 400 - 40 - 30||y < 40 + 40)
		{
			//500是窗口高度，40是桌子边宽，30是球直径，最后一个40是标题栏的高度
			degree = -degree;
		}
		
		//碰到左右边界
		if(x < 40||x > 856 - 40 - 30)
		{
			degree = 3.14 - degree;
		}
	
	}
	
	
	//运行窗口
	public void launchFrame() {
		setSize(856,500);
		setLocation(100,100);
		setVisible(true);
		
		
		//重画窗口，每秒画25次
		while(true)
		{
			repaint();
			try {
				Thread.sleep(40);	//40ms, 1m = 1000ms,大约 1 秒画25次窗口
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
