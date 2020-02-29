package cn.MyDesignMode;

public class Example___AbstractFactoryMode {
	public static void main(String[] args) {
		AbstractFactory af = new ConcreateFactoryA();
		AbstractProduct ap_A = af.newProductA();
		
		System.out.println(ap_A.getProductName());
		System.out.println(ap_A.getProductInformation());
	}
}


abstract class AbstractProduct{
	public abstract String getProductName();
	public abstract String getProductInformation();
}

class Product_A extends AbstractProduct{
	private String productName;
	private String information;
	
	public Product_A() {
		productName = "TV";
		information = "BBC-32bG";
	}

	@Override
	public String getProductName() {
		return productName;
	}

	@Override
	public String getProductInformation() {
		return information;
	}
}

class Product_B extends AbstractProduct{
	private String productName;
	private String information;
	
	public Product_B() {
		productName = "TV";
		information = "BJJ-32bG";
	}

	@Override
	public String getProductName() {
		return productName;
	}

	@Override
	public String getProductInformation() {
		return information;
	}
}


/**
 * 	抽象工厂中的产品等级的更改不符合开闭原则
 * @author BinGCU
 *
 */
abstract class AbstractFactory{
	abstract AbstractProduct newProductA() ;
	abstract AbstractProduct newProductB();
}

class ConcreateFactoryA extends AbstractFactory{

	@Override
	public AbstractProduct newProductA() {
		return new Product_A();
	}

	@Override
	public AbstractProduct newProductB() {
		return new Product_B();
	}

}
