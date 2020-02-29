package cn.BinGCU;

/**
 * 	���Կɱ��ַ����кͲ��ɱ��ַ�����ʹ�õ�����
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
		/*ʹ��String�����ַ�����ƴ��*/
		/*ʮ������ϵͳ��Դ*/
		String str8 = "";
		long remainingMemorySpace_1 = Runtime.getRuntime().freeMemory();		//���ϵͳʣ���ڴ�ռ�
		long getCurrentTime_1 = System.currentTimeMillis();						//���ϵͳ�ĵ�ǰʱ��
		System.out.println("ϵͳʣ����ڴ�ռ䣺"+remainingMemorySpace_1);
		
		//������ʹ��StringBuilder����ƴ�ӣ���ÿ��ѭ���������һ��StringBuilder����
		for(int i = 0;i < 10000;i++) {
			str8 = str8 + i;
		}
		

		long remainingMemorySpace_2 = Runtime.getRuntime().freeMemory();
		long getCurrentTime_2 = System.currentTimeMillis();
		System.out.println("ϵͳʣ����ڴ�ռ䣺"+remainingMemorySpace_2);
		
		System.out.println("-------------------------");
		System.out.println("Stringռ�õ��ڴ棺"+(remainingMemorySpace_1-remainingMemorySpace_2));
		System.out.println("Stringռ�õ�ʱ�䣺"+(getCurrentTime_2-getCurrentTime_1));
	}
}

class TestStringBuilder{
	public static void Test() {
		/*ʹ��StringBuilder����ƴ��*/
		StringBuilder sb = new StringBuilder("");
		long getCurrentTime_1 = System.currentTimeMillis();						//���ϵͳ�ĵ�ǰʱ��
		long remainingMemorySpace_1 = Runtime.getRuntime().freeMemory();		//���ϵͳʣ���ڴ�ռ�
		System.out.println("ϵͳʣ����ڴ�ռ䣺"+remainingMemorySpace_1);
		
		for(int i = 0;i < 10000;i++) {
			sb.append(i);
		}
		
		long remainingMemorySpace_2 = Runtime.getRuntime().freeMemory();
		long getCurrentTime_2 = System.currentTimeMillis();
		System.out.println("ϵͳʣ����ڴ�ռ䣺"+remainingMemorySpace_2);
		
		System.out.println("-------------------------");
		System.out.println("StringBuilderռ�õ��ڴ棺"+(remainingMemorySpace_1-remainingMemorySpace_2));
		System.out.println("StringBuilderռ�õ�ʱ�䣺"+(getCurrentTime_2-getCurrentTime_1));
	}
}
