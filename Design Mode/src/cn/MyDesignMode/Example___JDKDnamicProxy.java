package cn.MyDesignMode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 	ʹ�������
 * 			����������Ŀ�������ʵ�ֽӿڣ���JDK��̬����ģʽ
 * 
 * @author BinGCU
 *
 */

public class Example___JDKDnamicProxy {
	public static void main(String[] args) {
		ISinger proxy = new JDKDnamicProxy(new Singer()).getProxy();
		proxy.sing();
	}
}

interface ISinger{
	void sing();
}

class Singer implements ISinger{
	@Override
	public void sing() {
		System.out.println("Singing.");
	}
}

class JDKDnamicProxy implements InvocationHandler{
	private Object target = null;
	
	public JDKDnamicProxy(Object target) {
		this.target = target;
	}
	
	/**
	 * 	���ͷ���
	 * 
	 * @param <T>
	 * @return
	 */
	public <T>T getProxy() {
		@SuppressWarnings("unchecked")
		T proxy = 
		(T)Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
		return proxy;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("׼��");
		Object returnMethod = method.invoke(target, args);
		System.out.println("xiexxie");
		
		return returnMethod;
	}
	
}