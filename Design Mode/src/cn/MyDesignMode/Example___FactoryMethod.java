package cn.MyDesignMode;

public class Example___FactoryMethod {
	public static void main(String[] args) {
		Factory factory = new FactoryA();
		
		Product productA = factory.makeProduct();
		
		System.out.println(productA.getProductName());
		System.out.println(productA.getProductInformation());
	}
}


abstract class Product{
	abstract String getProductName();
	abstract String getProductInformation();
}

class ProductA extends Product{
	private String productName;
	private String information;
	
	public ProductA() {
		productName = "car";
		information = "SA-311";
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

abstract class Factory{
	abstract Product makeProduct();
}

class FactoryA extends Factory{

	@Override
	Product makeProduct() {
		return new ProductA();
	}
}
