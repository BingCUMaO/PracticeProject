package cn.MyDesignMode;

public class Example___Singleton_HungryMode {
	public static void main(String[] args) {
		Evil evil = Evil.getEvilInstance();
		
		evil.setHealthPoint(100);
		evil.setManaPoint(200);
		System.out.println(evil.getHealthPoint());
		System.out.println(evil.getManaPoint());
	}
}

class Evil{
	private static Evil evil = new Evil();
	private int healthPoint;
	private int manaPoint;
	
	public int getHealthPoint() {
		return healthPoint;
	}

	public void setHealthPoint(int healthPoint) {
		this.healthPoint = healthPoint;
	}

	public int getManaPoint() {
		return manaPoint;
	}

	public void setManaPoint(int manaPoint) {
		this.manaPoint = manaPoint;
	}

	private Evil() {}
	
	public static Evil getEvilInstance() {
		return evil;
	}
}