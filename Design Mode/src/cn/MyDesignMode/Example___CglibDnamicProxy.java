package cn.MyDesignMode;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;



/**
 * 	CGLIB(Code Generation Library)
 * 	是一个开源项目。
 * 	是一个强大的、高性能的、高质量的Code生成类库
 * 	
 * 	Cglib可以在运行期拓展Java类与实现Java接口，比如Hibernate（一个开放源代码的对象关系映射框架）用它实现PO（Persistent Object，持久化对象）字节码的动态生成。
 * 
 * 	方法：Interception（拦截）
 * 		Cglib包的底层是通过使用一个小而快的【字节码处理框架ASM】，来转换字节码并生成新的类。
 * 
 * 	使用情况：
 * 			加入容器的目标对象没有实现接口时，使用Cglib动态代理模式
 * 
 * 
 * 
 * 	使用Cglib代理的前提条件：
 * 		a）	需要引入Cglib的jar文件cglib-nodep-3.2.6.jar
 * 			下载地址：	https://github.com/cglib/cglib/releases/tag/RELEASE_3_2_6
 * 		b）	目标类不能为final
 * 		c）	目标对象的方法如果为final static，那么久不会被拦截，即不会执行目标对象额外的业务方法
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
 * 		Cglib子类代理工厂，用来生成动态代理
 * */
class ProxyFactory implements MethodInterceptor{
	//维护目标对象
	private Object target = null;

	public ProxyFactory(Object target) {
		this.target = target;
	}
	
	//给目标对象创建一个代理对象,通过该方法获取创建的目标对象的代理对象
	@SuppressWarnings("unchecked")
	public <T>T getProxyInstance() {
		Enhancer enhancer = new Enhancer();				//1. 工具类
		
		enhancer.setSuperclass(target.getClass());			//2. 设置父类
		enhancer.setCallback(this);									//3. 设置单一回调对象，在调用中拦截对目标方法的调用
		
		return (T)enhancer.create();									//4. 创建子类（代理对象）
	}

	/*
	 * 		定义拦截器，在调用目标方法时，CGLIB会回调MethodInterceptor接口方法进行拦截，
	 * 		用来实现自定义的代理逻辑，类似于JDK动态代理中的InvocationHandler接口
	 * */
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("准备");
		
		Object returnMethod = method.invoke(target, args);				//执行目标对象的方法
		//或这种写法：
		//Object returnMethod = proxy.invokeSuper(obj, args);
		
		System.out.println("xiexie");
		
		return returnMethod;
	}
}


