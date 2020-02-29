package cn.MyDesignMode;

/**
 * 	类适配器模式
 * 
 * 	示例：
 * 	有外设播放器，需要控制台对其进行播放控制
 * 	需要适配器对其进行适配
 * 
 * @author BinGCU
 */
public class Example___AdapterMode_ClassMode {
	public static void main(String[] args) {
		AudioConsole ac = new AudioConsole();
		ac.Play();
	}
}

//外设播放器
class AudioPlayer{
	protected void Play() {
		System.out.println("无声音。。。");
	}
}

//音频驱动接口
interface AudioDrivenInterface{
	void callPlay();
}

//音频控制台
class AudioConsole extends AudioPlayer implements AudioDrivenInterface{

	@Override
	public void callPlay() {
		Play();
	}

	@Override
	protected void Play() {
		System.out.println("声音正在播放。。。");
	}
	
}
