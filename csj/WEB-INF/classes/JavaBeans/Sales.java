package JavaBeans;

import java.io.Serializable;

public class Sales implements Serializable {
	
	private String productID;
	private String productName;
	private double price;
	private int salesCount;
	private double totalSales;
	private String orderDate;
	
	public Sales() {
		
	}
	
	public Sales(String productID,String productName,double price,int salesCount,double totalSales,String orderDate) {
		this.productID = productID;
		this.productName = productName;
		this.price = price;
		this.salesCount = salesCount;
		this.totalSales = totalSales;
		this.orderDate = orderDate;
	}
	
	public String getProductID(){
		return this.productID;
	}
	
	public void setProductID(String productID){
		this.productID = productID;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public double getPrice(){
		return this.price;
	}
	
	public void setPrice(double price){
		this.price = price;
	}
	
	public int getSalesCount(){
		return this.salesCount;
	}
	
	public void setSalesCount(int salesCount){
		this.salesCount = salesCount;
	}
	
	public double getTotalSales() {
		return this.totalSales;
	}
	
	public void setTotalSales(double totalSales) {
		this.totalSales = totalSales;
	}
	
	public String getOrderDate() {
		return this.orderDate;
	}
	
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
}