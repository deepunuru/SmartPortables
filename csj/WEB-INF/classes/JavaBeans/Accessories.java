package JavaBeans;

import java.io.Serializable;

public class Accessories implements Serializable {
	private String name;
	private String image;
	private String condition;
	private double price;
	private String manufacturer;
	private double discount;
	private String retailer;
	private String id;
	private String type;
	private static final long serialVersionUID = 1L;
	
	public Accessories(String name,String image,String condition,double price,String manufacturer,double discount,String retailer,String id)
	{
		this.name = name;
		this.image = image;
		this.condition = condition;
		this.price = price;
		this.manufacturer = manufacturer;
		this.discount = discount;
		this.retailer = retailer;
		this.id = id;
	}
	
	public Accessories(String name,String image,String condition,double price,String manufacturer,double discount,String retailer,String id,String type)
	{
		this.name = name;
		this.image = image;
		this.condition = condition;
		this.price = price;
		this.manufacturer = manufacturer;
		this.discount = discount;
		this.retailer = retailer;
		this.id = id;
		this.type = type;
	}
	
	public Accessories() {
		
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
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
