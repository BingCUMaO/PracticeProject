package cn.MyDesignMode;

public class Example___Singleton_LazyMode {
	public static void main(String[] args) {
		Monitor monitor = Monitor.getMonitorInstance();
		
		monitor.setInformation("ÕÅÈý", 183, 18);
		
		System.out.println(monitor.getName());
		System.out.println(monitor.getHeight());
		System.out.println(monitor.getAge());
	}
}

class Monitor{
	private static Monitor monitor;
	private String name;
	private int height;
	private int age;
	
	private Monitor() {}
	
	public static Monitor getMonitorInstance() {
		if(monitor==null) {
			monitor = new Monitor();
		}
		return monitor;
	}
	
	public void setInformation(String name, int height, int age) {
		this.name = name;
		this.height = height;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getHeight() {
		return height;
	}

	public int getAge() {
		return age;
	}
	
}
