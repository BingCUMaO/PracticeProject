package cn.MyDesignMode;

public class Example___ProtoType_ShallowClone {
	public static void main(String[] args) throws CloneNotSupportedException {
		RealizeType rt = new RealizeType();
		rt.name = "rtԭ��";
		rt.age = "1";
		rt.innerClass.isInner = true;
		
		RealizeType rt2 = (RealizeType)rt.clone();
		
		System.out.println("rt2--name:"+rt2.name);
		System.out.println("rt2--age:"+rt2.age);
		System.out.println(rt2.innerClass.isInner);
		
		/*
		 * 	ǳ��¡
		 * 	��Ϊ��ǳ��¡�����Բ�ͬʵ����rt��rt2����ͬһ����
		 * 	��rt2�ڴ�rt�п�¡�����Ĺ���ȴ����ǳ��¡��ɵ�ͬһ����
		 * */
		System.out.println(rt);
		System.out.println(rt2);
		System.out.println("#####");
		System.out.println(rt.innerClass);
		System.out.println(rt2.innerClass);
	}
}

class RealizeType implements Cloneable{
	public String name;
	public String age;
	public InnerClass innerClass = new InnerClass();
	
	public RealizeType() {
		System.out.println("ԭ��ʵ�������ɹ���");
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		System.out.println("ǳ��¡�ɹ���");
		return super.clone();
	}
}

class InnerClass{
	public Boolean isInner = false;
}