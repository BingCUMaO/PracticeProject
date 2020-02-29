package cn.MyDesignMode;

public class Example___ProtoType_ShallowClone {
	public static void main(String[] args) throws CloneNotSupportedException {
		RealizeType rt = new RealizeType();
		rt.name = "rt原型";
		rt.age = "1";
		rt.innerClass.isInner = true;
		
		RealizeType rt2 = (RealizeType)rt.clone();
		
		System.out.println("rt2--name:"+rt2.name);
		System.out.println("rt2--age:"+rt2.age);
		System.out.println(rt2.innerClass.isInner);
		
		/*
		 * 	浅克隆
		 * 	因为是浅克隆，所以不同实例的rt和rt2并非同一引用
		 * 	但rt2在从rt中克隆过来的过程却是因浅克隆造成的同一引用
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
		System.out.println("原型实例创建成功！");
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		System.out.println("浅克隆成功！");
		return super.clone();
	}
}

class InnerClass{
	public Boolean isInner = false;
}