package JavaBeans;

import java.io.Serializable;
import java.util.HashMap;

public class Phones implements Serializable{
	private String name;
	private String image;
	private String condition;
	private double price;
	private String manufacturer;
	private double discount;
	private String retailer;
	private HashMap<String, Accessories> accessories = new HashMap<String, Accessories>();
	private String id;
	
	public Phones(String name,String image,String condition,double price,String manufacturer,double discount,String retailer,HashMap<String, Accessories> accessories,String id) {
		this.name = name;
		this.image = image;
		this.condition = condition;
		this.price = price;
		this.manufacturer = manufacturer;
		this.discount = discount;
		this.retailer = retailer;
		this.accessories = accessories;
		this.id = id;
	}
	
	public Phones() {
		
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
	public HashMap<String, Accessories> getAccessories() {
		return accessories;
	}
	public void setAccessories(HashMap<String, Accessories> accessories) {
		this.accessories = accessories;
	}	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}