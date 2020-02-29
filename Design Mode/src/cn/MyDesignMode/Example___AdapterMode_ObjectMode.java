package cn.MyDesignMode;

/**
 * 	����������ģʽ
 * 
 * 	ʾ����
 * 	����Ƶ�����ӿڣ����貥�����ж�����������
 * 	��Ҫ�����������������
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
		System.out.println("��ƵС���ز��š�����");
	}
}

class Loudspeaker{
	public void outLoudPlay(){
		System.out.println("��Ƶ�����ز��š�����");
	}
}


//��Ƶ�����ӿ�
interface _AudioDrivenInterface{
	void linkPlay();
}

//�����ӿ�����
class EarphoneDriver implements _AudioDrivenInterface{
	private Earphone earphone = new Earphone();
	
	@Override
	public void linkPlay() {
		earphone.play();
	}
}

//�������ӿ�����
class LoudspeakerDriver implements _AudioDrivenInterface{
	private Loudspeaker loudspeaker = new Loudspeaker();
	
	@Override
	public void linkPlay() {
		loudspeaker.outLoudPlay();
	}
}

//����̨
class _AudioConsole{
	private _AudioDrivenInterface adi;
	
	public void setAudioDrivenInterface(_AudioDrivenInterface drive) {
		this.adi = drive;
	}
	
	public void play() {
		if(adi!=null) {
			adi.linkPlay();
		}else {
			System.out.println("������������");
		}
	}
}
