package cn.MyDesignMode;

/**
 * 	对象适配器模式
 * 
 * 	示例：
 * 	有音频驱动接口，外设播放器有耳机和扬声器
 * 	需要适配器对其进行适配
 * 
 * @author BinGCU
 */
public class Example___AdapterMode_ObjectMode {
	public static void main(String[] args) {
		_AudioDrivenInterface adi = new EarphoneDriver();
		_AudioConsole audioConsole = new _AudioConsole();
		
		//audioConsole.setAudioDrivenInterface(adi);
		audioConsole.play();
	}
}

class Earphone{
	public void play() {
		System.out.println("音频小声地播放。。。");
	}
}

class Loudspeaker{
	public void outLoudPlay(){
		System.out.println("音频大声地播放。。。");
	}
}


//音频驱动接口
interface _AudioDrivenInterface{
	void linkPlay();
}

//耳机接口驱动
class EarphoneDriver implements _AudioDrivenInterface{
	private Earphone earphone = new Earphone();
	
	@Override
	public void linkPlay() {
		earphone.play();
	}
}

//扬声器接口驱动
class LoudspeakerDriver implements _AudioDrivenInterface{
	private Loudspeaker loudspeaker = new Loudspeaker();
	
	@Override
	public void linkPlay() {
		loudspeaker.outLoudPlay();
	}
}

//控制台
class _AudioConsole{
	private _AudioDrivenInterface adi;
	
	public void setAudioDrivenInterface(_AudioDrivenInterface drive) {
		this.adi = drive;
	}
	
	public void play() {
		if(adi!=null) {
			adi.linkPlay();
		}else {
			System.out.println("无驱动。。。");
		}
	}
}
