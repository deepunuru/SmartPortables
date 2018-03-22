package JavaBeans;

import java.io.*;
import java.util.*;

public class ShoppingCart implements Serializable{
	
	private String username;
	//private HashMap<String, String> productList;
	private Vector<String> productList = new Vector<String>();
	private Vector<String> productType = new Vector<String>();
	private int cartSize;
	
	public ShoppingCart(String username,Vector<String> productList,int cartSize) {
		super();
		this.username = username;
		this.productList = productList;
		this.cartSize = cartSize;
		this.productType = productType;
	}
	
	public ShoppingCart() {
		
	}
	
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Vector<String> getProductList() {
		return this.productList;
	}
	
	public void setProductList(Vector<String> productList) {
		this.productList = productList;
	}
	
	public int getCartSize() {
		return this.cartSize;
	}
	
	public void setCartSize(int cartSize) {
		this.cartSize = cartSize;
	}
	
	public Vector<String> getProductType(){
		return this.productType;
	}
	
	public void setProductType(Vector<String> productType) {
		this.productType = productType;
	}
}