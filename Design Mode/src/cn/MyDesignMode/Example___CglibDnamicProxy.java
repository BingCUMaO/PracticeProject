package cn.MyDesignMode;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;



/**
 * 	CGLIB(Code Generation Library)
 * 	��һ����Դ��Ŀ��
 * 	��һ��ǿ��ġ������ܵġ���������Code�������
 * 	
 * 	Cglib��������������չJava����ʵ��Java�ӿڣ�����Hibernate��һ������Դ����Ķ����ϵӳ���ܣ�����ʵ��PO��Persistent Object���־û������ֽ���Ķ�̬���ɡ�
 * 
 * 	������Interception�����أ�
 * 		Cglib���ĵײ���ͨ��ʹ��һ��С����ġ��ֽ��봦����ASM������ת���ֽ��벢�����µ��ࡣ
 * 
 * 	ʹ�������
 * 			����������Ŀ�����û��ʵ�ֽӿ�ʱ��ʹ��Cglib��̬����ģʽ
 * 
 * 
 * 
 * 	ʹ��Cglib�����ǰ��������
 * 		a��	��Ҫ����Cglib��jar�ļ�cglib-nodep-3.2.6.jar
 * 			���ص�ַ��	https://github.com/cglib/cglib/releases/tag/RELEASE_3_2_6
 * 		b��	Ŀ���಻��Ϊfinal
 * 		c��	Ŀ�����ķ������Ϊfinal static����ô�ò��ᱻ���أ�������ִ��Ŀ���������ҵ�񷽷�
 * 
 * @author BinGCU
 *
 */
public class Example___CglibDnamicProxy {
	public static void main(String[] args) {
		Singer singer = new Singer();
		Singer proxy = new ProxyFactory(singer).getProxyInstance();
		
		proxy.sing();
	}
}

/*
 * 		Cglib������������������ɶ�̬����
 * */
class ProxyFactory implements MethodInterceptor{
	//ά��Ŀ�����
	private Object target = null;

	public ProxyFactory(Object target) {
		this.target = target;
	}
	
	//��Ŀ����󴴽�һ���������,ͨ���÷�����ȡ������Ŀ�����Ĵ������
	@SuppressWarnings("unchecked")
	public <T>T getProxyInstance() {
		Enhancer enhancer = new Enhancer();				//1. ������
		
		enhancer.setSuperclass(target.getClass());			//2. ���ø���
		enhancer.setCallback(this);									//3. ���õ�һ�ص������ڵ��������ض�Ŀ�귽���ĵ���
		
		return (T)enhancer.create();									//4. �������ࣨ�������
	}

	/*
	 * 		�������������ڵ���Ŀ�귽��ʱ��CGLIB��ص�MethodInterceptor�ӿڷ����������أ�
	 * 		����ʵ���Զ���Ĵ����߼���������JDK��̬�����е�InvocationHandler�ӿ�
	 * */
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("׼��");
		
		Object returnMethod = method.invoke(target, args);				//ִ��Ŀ�����ķ���
		//������д����
		//Object returnMethod = proxy.invokeSuper(obj, args);
		
		System.out.println("xiexie");
		
		return returnMethod;
	}
}


