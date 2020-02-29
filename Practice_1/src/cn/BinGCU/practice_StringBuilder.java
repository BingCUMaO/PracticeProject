package cn.BinGCU;

/**
 * 	测试可变字符序列和不可变字符序列使用的陷阱
 * @author BinGCU
 *
 */

public class practice_StringBuilder {
	public static void main(String[] args) {
		
		TestString.Test();
		System.out.println("\n##################################\n");
		System.out.println("##################################\n");
		TestStringBuilder.Test();
		
	}
}

class TestString{
	public static void Test() {
		/*使用String进行字符串的拼接*/
		/*十分消耗系统资源*/
		String str8 = "";
		long remainingMemorySpace_1 = Runtime.getRuntime().freeMemory();		//获得系统剩余内存空间
		long getCurrentTime_1 = System.currentTimeMillis();						//获得系统的当前时间
		System.out.println("系统剩余的内存空间："+remainingMemorySpace_1);
		
		//本质上使用StringBuilder进行拼接，但每次循环都会产生一个StringBuilder对象
		for(int i = 0;i < 10000;i++) {
			str8 = str8 + i;
		}
		

		long remainingMemorySpace_2 = Runtime.getRuntime().freeMemory();
		long getCurrentTime_2 = System.currentTimeMillis();
		System.out.println("系统剩余的内存空间："+remainingMemorySpace_2);
		
		System.out.println("-------------------------");
		System.out.println("String占用的内存："+(remainingMemorySpace_1-remainingMemorySpace_2));
		System.out.println("String占用的时间："+(getCurrentTime_2-getCurrentTime_1));
	}
}

class TestStringBuilder{
	public static void Test() {
		/*使用StringBuilder进行拼接*/
		StringBuilder sb = new StringBuilder("");
		long getCurrentTime_1 = System.currentTimeMillis();						//获得系统的当前时间
		long remainingMemorySpace_1 = Runtime.getRuntime().freeMemory();		//获得系统剩余内存空间
		System.out.println("系统剩余的内存空间："+remainingMemorySpace_1);
		
		for(int i = 0;i < 10000;i++) {
			sb.append(i);
		}
		
		long remainingMemorySpace_2 = Runtime.getRuntime().freeMemory();
		long getCurrentTime_2 = System.currentTimeMillis();
		System.out.println("系统剩余的内存空间："+remainingMemorySpace_2);
		
		System.out.println("-------------------------");
		System.out.println("StringBuilder占用的内存："+(remainingMemorySpace_1-remainingMemorySpace_2));
		System.out.println("StringBuilder占用的时间："+(getCurrentTime_2-getCurrentTime_1));
	}
}
