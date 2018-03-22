package JavaBeans;

import java.io.Serializable;

public class Inventory implements Serializable {
	private String productID;
	private String productName;
	private double price;
	private int qnty;
	private boolean productOnSale;
	private boolean rebate;
	
	public Inventory(String productID,String productName,double price,int qnty,boolean productOnSale,boolean rebate){
		this.productID = productID;
		this.productName = productName;
		this.price = price;
		this.qnty = qnty;
		this.productOnSale = productOnSale;
		this.rebate = rebate;
	}
	
	public Inventory(){
		
	}
	
	public String getProductID(){
		return this.productID;
	}
	
	public void setProductID(String productID){
		this.productID = productID;
	}
	
	public String getProductName(){
		return this.productName;
	}
	
	public void setProductName(String productName){
		this.productName = productName;
	}
	
	public double getPrice(){
		return this.price;
	}
	
	public void setPrice(double price){
		this.price = price;
	}
	
	public int getQnty(){
		return this.qnty;
	}
	
	public void setQnty(int qnty){
		this.qnty = qnty;
	}
	
	public boolean getProductOnSale(){
		return this.productOnSale;
	}
	
	public void setProductOnSale(boolean productOnSale){
		this.productOnSale = productOnSale;
	}
	
	public boolean getRebate(){
		return this.rebate;
	}
	
	public void setRebate(boolean rebate){
		this.rebate = rebate;
	}
}