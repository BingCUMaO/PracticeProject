package cn.MyDesignMode;

/**
 * 	ʾ��
 * 	�̲��ζ�ͱ��ӳߴ���Ž�
 * 
 * 	�󱭡��б���С��
 * 	ԭζ����ݮζ������ζ
 * 
 * @author BinGCU
 */
public class Example___BridgeMode {
	public static void main(String[] args) {
		SizeOfCup size = new LongSize();
		TeaOfMilk mt = new PureFlavor();
		
		//mt.setSizeOfCup(size);
		mt.show();
	}
}

//���ӳߴ�
interface SizeOfCup{
	int getSize();
}
class LongSize implements SizeOfCup{
	@Override
	public int getSize() {
		return 30;
	}
}
class MiddleSize implements SizeOfCup{
	@Override
	public int getSize() {
		return 20;
	}
}
class ShortSize implements SizeOfCup{
	@Override
	public int getSize() {
		return 30;
	}
}

abstract class TeaOfMilk{
	protected SizeOfCup sizeOfCup;
	public void setSizeOfCup(SizeOfCup size) {
		this.sizeOfCup = size;
	}
	
	abstract String flavor();
	
	void show() {
		if(sizeOfCup!=null) {
			System.out.println(flavor()+"..."+sizeOfCup.getSize()+"ml");
		}else {
			System.out.println("The size of cup is unknow.");
			System.out.println("So fail to order.");
		}
	};
}
class PureFlavor extends TeaOfMilk{
	@Override
	String flavor() {
		return "Pure Flavor";
	}
}
class StrawberryFlavor extends TeaOfMilk{
	@Override
	String flavor() {
		return "Strawberry Flavor";
	}
}
class CoffeeFlavor extends TeaOfMilk{
	@Override
	String flavor() {
		return "Coffee Flavor";
	}
}
