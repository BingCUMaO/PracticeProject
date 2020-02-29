package cn.BinGCU;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class practice_Reflect {
	public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException{

		//���෴��
		String simpleName = TestClass.class.getSimpleName();
		String modifierType = Modifier.toString(TestClass.class.getModifiers());
		System.out.println("�٣�"+simpleName);
		System.out.println("�٣�"+modifierType);
		
		//���ֶη���
		TestClass tc = new TestClass();
		Field field = TestClass.class.getDeclaredField("s");
		field.setAccessible(true);
		System.out.println("�ڣ�"+field.get(tc));	//get(Object obj)������Obj��ʾĿ�����getĿ����ֶ�ֵ
		
		//�۷�������
		Method method = TestClass.class.getDeclaredMethod("testFunction", String.class);
		method.setAccessible(true);
		System.out.println("�ۣ�"+Modifier.toString(method.getModifiers()));
		
		//�ܹ��캯������
		Constructor<TestClass> constructor = TestClass.class.getDeclaredConstructor(Integer.class);
		constructor.setAccessible(true);
		System.out.println("�ܣ�"+Modifier.toString(constructor.getModifiers()));
		TestClass tclass = constructor.newInstance(10);
		tclass.printDatas();
	}
}

class TestClass{
	private int a = 10;
	private  String s = "test01";
	private Integer data = null;
	private String dataString = null;
	
	private TestClass(Integer i) {this.data = i;}
	public TestClass() {}
	
	private void testFunction(String i) {};
	
	public void printDatas() {
		System.out.println(data+"->"+dataString);
	}
}