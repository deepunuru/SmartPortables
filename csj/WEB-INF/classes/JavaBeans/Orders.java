package JavaBeans;

import java.io.Serializable;

public class Orders implements Serializable {
	
	private String customerName;
	private String username;
	private String[] productType;
	private String[] productID;
	private int[] qnty;
	private String orderDate;
	private int confirmationNumber;
	private String deliveryDate;
	private double shippingPrice;
	private double totalPrice;
	private String productWarranty;
	
	private String country;
	private String state;
	private String address1;
	private String address2;
	private String city;
	private int zipCode;
	private String mobileNumber;
	private String emailAddress;
	
	public Orders() {
		
	}
	
	public Orders(String username,String customerName,String[] productType,String[] productID,int[] qnty,String orderDate,int confirmationNumber,String deliveryDate,double shippingPrice,double totalPrice,String productWarranty,String country,String state,String address1,String address2,String city,int zipCode,String mobileNumber,String emailAddress) {
		this.username = username;
		this.customerName = customerName;
		this.productType = productType;
		this.productID = productID;
		this.qnty = qnty;
		this.orderDate = orderDate;
		this.confirmationNumber = confirmationNumber;
		this.deliveryDate = deliveryDate;
		this.shippingPrice = shippingPrice;
		this.totalPrice = totalPrice;
		this.productWarranty = productWarranty;
		this.country = country;
		this.state = state;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.zipCode = zipCode;
		this.mobileNumber = mobileNumber;
		this.emailAddress = emailAddress;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String[] getProductType() {
		return this.productType;
	}
	
	public void setProductType(String[] productType) {
		this.productType = productType;
	}
	
	public String[] getProductID() {
		return this.productID;
	}
	
	public void setProductID(String[] productID) {
		this.productID = productID;
	}
	
	public int[] getQuantity() {
		return this.qnty;
	}
	
	public void setQuantity(int[] qnty) {
		this.qnty = qnty;
	}
	
	public String getOrderDate() {
		return this.orderDate;
	}
	
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
	public int getConfirmationNumber() {
		return this.confirmationNumber;
	}
	
	public void setConfirmationNumber(int confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}
	
	public String getDeliveryDate() {
		return this.deliveryDate;
	}
	
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	public double getShippingPrice() {
		return this.shippingPrice;
	}
	
	public void setShippingPrice(double shippingPrice) {
		this.shippingPrice = shippingPrice;
	}
	
	public double getTotalPrice() {
		return this.totalPrice;
	}
	
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public String getProductWarranty() {
		return this.productWarranty;
	}
	
	public void setProductWarranty(String productWarranty) {
		this.productWarranty = productWarranty;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getState() {
		return this.state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getAddress1() {
		return this.address1;
	}
	
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	public String getAddress2() {
		return this.address2;
	}
	
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public int getZipCode() {
		return this.zipCode;
	}
	
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getMobileNumber() {
		return this.mobileNumber;
	}
	
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public String getEmailAddress() {
		return this.emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}