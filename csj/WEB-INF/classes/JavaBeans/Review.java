package JavaBeans;

import java.io.Serializable;

public class Review implements Serializable {
	
	private String productName;
	private String productType;
	private double price;
	private String retailer;
	private int zipCode;
	private String city;
	private String state;
	private boolean productOnSale;
	private String manufacturer;
	private boolean rebate;
	private String username;
	private int age;
	private String gender;
	private String occupation;
	private int reviewRating;
	private String reviewDate;
	private String reviewText;
	
	public Review() {
		
	}
	
	public Review(String productName,String productType,double price,String retailer,int zipCode,String city,String state,boolean productOnSale,String manufacturer,boolean rebate,String username,int age,String gender,String occupation,int reviewRating,String reviewDate,String reviewText) {
		this.productName = productName;
		this.productType = productType;
		this.price = price;
		this.retailer = retailer;
		this.zipCode = zipCode;
		this.city = city;
		this.state = state;
		this.productOnSale = productOnSale;
		this.manufacturer = manufacturer;
		this.rebate = rebate;
		this.username = username;
		this.age = age;
		this.gender = gender;
		this.occupation = occupation;
		this.reviewRating = reviewRating;
		this.reviewDate = reviewDate;
		this.reviewText = reviewText;
	}
	
	public String getProductName() {
		return this.productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProductType() {
		return this.productType;
	}
	
	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	public double getprice() {
		return this.price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getRetailer() {
		return this.retailer;
	}
	
	public void setRetailer(String retailer) {
		this.retailer = retailer;
	}
	
	public int getZipCode() {
		return this.zipCode;
	}
	
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return this.state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public boolean getProductOnSale() {
		return this.productOnSale;
	}
	
	public void setProductOnSale(boolean productOnSale) {
		this.productOnSale = productOnSale;
	}
	
	public String getManufacturer() {
		return this.manufacturer;
	}
	
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	public boolean getRebate() {
		return this.rebate;
	}
	
	public void setRebate(boolean rebate) {
		this.rebate = rebate;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getGender() {
		return this.gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getOccupation() {
		return this.occupation;
	}
	
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	public int getReviewRating() {
		return this.reviewRating;
	}
	
	public void setReviewRating(int reviewRating) {
		this.reviewRating = reviewRating;
	}
	
	public String getReviewDate() {
		return this.reviewDate;
	}
	
	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
	
	public String getReviewText() {
		return this.reviewText;
	}
	
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
}