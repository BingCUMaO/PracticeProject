package cn.MyDesignMode;

/**
 * 	��������ģʽ
 * 
 * 	ʾ����
 * 	�����貥��������Ҫ����̨������в��ſ���
 * 	��Ҫ�����������������
 * 
 * @author BinGCU
 */
public class Example___AdapterMode_ClassMode {
	public static void main(String[] args) {
		AudioConsole ac = new AudioConsole();
		ac.Play();
	}
}

//���貥����
class AudioPlayer{
	protected void Play() {
		System.out.println("������������");
	}
}

//��Ƶ�����ӿ�
interface AudioDrivenInterface{
	void callPlay();
}

//��Ƶ����̨
class AudioConsole extends AudioPlayer implements AudioDrivenInterface{

	@Override
	public void callPlay() {
		Play();
	}

	@Override
	protected void Play() {
		System.out.println("�������ڲ��š�����");
	}
	
}
