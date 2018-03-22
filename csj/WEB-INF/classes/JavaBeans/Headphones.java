package JavaBeans;

import java.io.Serializable;
import java.util.HashMap;

public class Headphones implements Serializable{
	private String name;
	private String image;
	private String condition;
	private double price;
	private String manufacturer;
	private double discount;
	private String retailer;
	private String id;
	private HashMap<String, Accessories> accessories = new HashMap<String, Accessories>();
	
	public Headphones(String name,String image,String condition,double price,String manufacturer,double discount,String retailer,HashMap<String, Accessories> accessories,String id) {
		this.name = name;
		this.image = image;
		this.condition = condition;
		this.price = price;
		this.manufacturer = manufacturer;
		this.discount = discount;
		this.retailer = retailer;	
		this.id = id;
		this.accessories = accessories;
	}
	
	public Headphones() {
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public String getRetailer() {
		return retailer;
	}
	public void setRetailer(String retailer) {
		this.retailer = retailer;
	}	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public HashMap<String, Accessories> getAccessories() {
		return accessories;
	}
	public void setAccessories(HashMap<String, Accessories> accessories) {
		this.accessories = accessories;
	}
}