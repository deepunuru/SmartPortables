package DataAccess;

import java.io.*;
import java.util.*;
import java.util.Date;

import Controller.SaxParserDataStore;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import JavaBeans.*;

public class MySQLDataStoreUtilities{
	
	String conString = "jdbc:mysql://localhost:1111/SmartPortablesDB?useSSL=false";
	String dbUser = "";
	String dbPwd = "";
	
	public MySQLDataStoreUtilities() {
		
	}
	
	public void createDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:1111/?useSSL=false", dbUser, dbPwd); 
			Statement s = conn.createStatement();
			s.executeUpdate("CREATE DATABASE IF NOT EXISTS SmartPortablesDB");
			conn.close();
			
			Connection conn1 = DriverManager.getConnection("jdbc:mysql://localhost:1111/SmartPortablesDB?useSSL=false", dbUser, dbPwd);
			s = conn1.createStatement();
			s.executeUpdate("CREATE TABLE IF NOT EXISTS users(userType varchar(255) NOT NULL,username varchar(255) NOT NULL,firstName varchar(255) DEFAULT NULL,lastName varchar(255) DEFAULT NULL,address varchar(255) DEFAULT NULL,zipcode int(11) DEFAULT NULL,emailID varchar(255) DEFAULT NULL,dob varchar(20) DEFAULT NULL,password varchar(255) NOT NULL,PRIMARY KEY(username));");
			
			s = conn1.createStatement();
			s.executeUpdate("CREATE TABLE IF NOT EXISTS orders(customerName varchar(255) DEFAULT NULL,username varchar(255) NOT NULL,orderDate varchar(255) NOT NULL,confirmationNumber int(20) NOT NULL,deliveryDate varchar(255) NOT NULL,shippingPrice double(20,2) NOT NULL,totalPrice double(20,2) NOT NULL,productWarranty varchar(255) DEFAULT NULL,country varchar(255) DEFAULT NULL,state varchar(255) DEFAULT NULL,address1 varchar(255) DEFAULT NULL,address2 varchar(255) DEFAULT NULL,city varchar(255) DEFAULT NULL, zipCode int(11) DEFAULT NULL,mobileNumber varchar(255) DEFAULT NULL,emailAddress varchar(255) DEFAULT NULL,PRIMARY KEY(confirmationNumber));");
			
			s = conn1.createStatement();
			s.executeUpdate("CREATE TABLE IF NOT EXISTS orderProductList(confirmationNumber int(20) NOT NULL,productID varchar(255) NOT NULL,productType varchar(255) NOT NULL,qnty int(20) NOT NULL);");
			
			s = conn1.createStatement();
			s.executeUpdate("CREATE TABLE IF NOT EXISTS accessories(productID varchar(255) NOT NULL,productType varchar(255) NOT NULL,retailer varchar(255) NOT NULL,productName varchar(255) NOT NULL,image varchar(255) NOT NULL,productCondition varchar(255) NOT NULL,price double NOT NULL,manufacturer varchar(255) NOT NULL,discount double NOT NULL,PRIMARY KEY(productID));");
			
			s = conn1.createStatement();
			s.executeUpdate("CREATE TABLE IF NOT EXISTS products(productID varchar(255) NOT NULL,productType varchar(255) NOT NULL,retailer varchar(255) NOT NULL,productName varchar(255) NOT NULL,image varchar(255) NOT NULL,productCondition varchar(255) NOT NULL,price double NOT NULL,manufacturer varchar(255) NOT NULL,discount double NOT NULL,PRIMARY KEY(productID));");
			
			s = conn1.createStatement();
			s.executeUpdate("CREATE TABLE IF NOT EXISTS productaccessories(productID varchar(255) NOT NULL,productType varchar(255) NOT NULL,accessoryID varchar(255) NOT NULL);");
			
			s = conn1.createStatement();
			s.executeUpdate("CREATE TABLE IF NOT EXISTS inventory(productID varchar(255) NOT NULL,productName varchar(255) NOT NULL,price double NOT NULL,qnty int(11) DEFAULT NULL,productOnSale TINYINT(1) NOT NULL,rebate TINYINT(1) NOT NULL,PRIMARY KEY(productID));");
			
			s= conn1.createStatement();
			s.executeUpdate("CREATE OR REPLACE VIEW totalSalesTable AS SELECT o.productID,i.productName,price,sum(o.qnty) AS SalesCount,(price * sum(o.qnty)) AS TotalSales,o.confirmationNumber FROM orderProductList o,inventory i WHERE o.productID=i.productID GROUP BY o.productID;");
			
			s= conn1.createStatement();
			s.executeUpdate("CREATE OR REPLACE VIEW ProdCount AS select * from orderProductList group by confirmationNumber;");
			
			conn1.close();
		}
		catch(Exception ex) {
			System.out.println("Message" + ex.getMessage());
		}
	}
	
	public HashMap<String,String> checkDBConnection() {
		HashMap<String, String> dbStatus = new HashMap<String, String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
	         // Open a connection
	         Connection conn = DriverManager.getConnection(conString, dbUser, dbPwd);
	         dbStatus.put("status","true");
	         dbStatus.put("msg", "Connected");
			return dbStatus;
		}
		catch(com.mysql.jdbc.exceptions.jdbc4.CommunicationsException ex) {
			dbStatus.put("status","false");
			String msg = "MySQL server is not up and running";
			dbStatus.put("msg", msg);
			return dbStatus;
		}
		catch(Exception ex) {
			System.out.println("Message - " + ex.getMessage());
			dbStatus.put("status","false");
			/*
			String msg = "";
			if(ex.getMessage()=="Communications link failure\n\nThe last packet sent successfully to the server was 0 milliseconds ago. The driver has not received any packets from the server.")
			{
				msg = "MySQL server is not up and running";
			}
			else {
				msg = ex.getMessage();
			}*/
	        dbStatus.put("msg", ex.getMessage());			
			return dbStatus;
		}
	}
	
	public void insertSampleUser() {
		try {
			System.out.println("Inserting SampleUser");
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			//Checking if sample user already exists
			
			String query = "SELECT COUNT(*) FROM users WHERE username=? AND userType=?;";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, "user1");
			pst.setString(2, "Customer");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				if(rs.getInt(1) > 0) {
					query = "UPDATE users SET firstName=?,lastName=?,address=?,zipcode=?,emailID=?,dob=?,password=? where username=?";
					pst = con.prepareStatement(query);
					pst.setString(1, "FirstName");
					pst.setString(2, "LastName");
					pst.setString(3, "Address");
					pst.setInt(4, 60616);
					pst.setString(5, "EmailID");
					pst.setString(6, "11/11/1111");
					pst.setString(7, "user1");
					pst.setString(8, "user1");
				}
				else {
					query = "INSERT INTO users(userType,username,firstName,lastName,address,zipcode,emailID,dob,password) VALUES(?,?,?,?,?,?,?,?,?);";
					pst = con.prepareStatement(query);
					pst.setString(1, "Customer");
					pst.setString(2, "user1");
					pst.setString(3, "FirstName");
					pst.setString(4, "LastName");
					pst.setString(5, "Address");
					pst.setInt(6, 60616);
					pst.setString(7, "EmailID");
					pst.setString(8, "11/11/1111");
					pst.setString(9, "user1");
				}
			}
			boolean result = pst.execute();
			con.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
		}
	}
	
	public void populateDatabase(SaxParserDataStore datastoreObj) {
		try {
			System.out.println("Database Populating");
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			//Clear products
			String query = "DELETE FROM products;";
			PreparedStatement pst = con.prepareStatement(query);
			pst.execute();
			//Clear productaccessories
			query = "DELETE FROM productaccessories;";
			pst = con.prepareStatement(query);
			pst.execute();
			//Clear accessories
			query = "DELETE FROM accessories;";
			pst = con.prepareStatement(query);
			pst.execute();
			//Clear inventory
			query = "DELETE FROM inventory;";
			pst = con.prepareStatement(query);
			pst.execute();

			SmartWatch watch = new SmartWatch();
			for(Map.Entry<String, SmartWatch> w : datastoreObj.watchMap.entrySet()) {	
				watch = w.getValue(); 
				//Loading SmartWatches
				query = "INSERT INTO products(productID,productType,retailer,productName,image,productCondition,price,manufacturer,discount) VALUES(?,?,?,?,?,?,?,?,?);";
				pst = con.prepareStatement(query);
				pst.setString(1, watch.getId());
				pst.setString(2, "smartwatches");
				pst.setString(3, watch.getRetailer());
				pst.setString(4, watch.getName());
				pst.setString(5, watch.getImage());
				pst.setString(6, watch.getCondition());
				pst.setDouble(7, watch.getPrice());
				pst.setString(8, watch.getManufacturer());
				pst.setDouble(9, watch.getDiscount());				
				pst.execute();
				
				//Loading SmartWatch Accessories
				Accessories accessory = new Accessories();
				for(Map.Entry<String, Accessories> K : watch.getAccessories().entrySet()) {	
					accessory = K.getValue();
					query = "INSERT INTO productaccessories(productID,productType,accessoryID) VALUES(?,?,?);";
					pst = con.prepareStatement(query);
					pst.setString(1, watch.getId());
					pst.setString(2, "smartwatches");
					pst.setString(3, accessory.getId());
					pst.execute();
				}
			}
			
			Speakers speaker = new Speakers();
			for(Map.Entry<String, Speakers> w : datastoreObj.speakersMap.entrySet()) {	
				speaker = w.getValue(); 
				//Loading Speakers
				query = "INSERT INTO products(productID,productType,retailer,productName,image,productCondition,price,manufacturer,discount) VALUES(?,?,?,?,?,?,?,?,?);";
				pst = con.prepareStatement(query);
				pst.setString(1, speaker.getId());
				pst.setString(2, "speakers");
				pst.setString(3, speaker.getRetailer());
				pst.setString(4, speaker.getName());
				pst.setString(5, speaker.getImage());
				pst.setString(6, speaker.getCondition());
				pst.setDouble(7, speaker.getPrice());
				pst.setString(8, speaker.getManufacturer());
				pst.setDouble(9, speaker.getDiscount());				
				pst.execute();
				
				//Loading Speakers Accessories
				Accessories accessory = new Accessories();
				for(Map.Entry<String, Accessories> K : speaker.getAccessories().entrySet()) {	
					accessory = K.getValue();
					query = "INSERT INTO productaccessories(productID,productType,accessoryID) VALUES(?,?,?);";
					pst = con.prepareStatement(query);
					pst.setString(1, speaker.getId());
					pst.setString(2, "speakers");
					pst.setString(3, accessory.getId());
					pst.execute();
				}
			}
			
			Headphones headphone = new Headphones();
			for(Map.Entry<String, Headphones> w : datastoreObj.headphonesMap.entrySet()) {	
				headphone = w.getValue(); 
				//Loading Headphones
				query = "INSERT INTO products(productID,productType,retailer,productName,image,productCondition,price,manufacturer,discount) VALUES(?,?,?,?,?,?,?,?,?);";
				pst = con.prepareStatement(query);
				pst.setString(1, headphone.getId());
				pst.setString(2, "headphones");
				pst.setString(3, headphone.getRetailer());
				pst.setString(4, headphone.getName());
				pst.setString(5, headphone.getImage());
				pst.setString(6, headphone.getCondition());
				pst.setDouble(7, headphone.getPrice());
				pst.setString(8, headphone.getManufacturer());
				pst.setDouble(9, headphone.getDiscount());				
				pst.execute();
				
				//Loading Headphone Accessories
				Accessories accessory = new Accessories();
				for(Map.Entry<String, Accessories> K : headphone.getAccessories().entrySet()) {	
					accessory = K.getValue();
					query = "INSERT INTO productaccessories(productID,productType,accessoryID) VALUES(?,?,?);";
					pst = con.prepareStatement(query);
					pst.setString(1, headphone.getId());
					pst.setString(2, "headphones");
					pst.setString(3, accessory.getId());
					pst.execute();
				}
			}
			
			Phones phone = new Phones();
			for(Map.Entry<String, Phones> w : datastoreObj.phonesMap.entrySet()) {	
				phone = w.getValue(); 
				//Loading Phones
				query = "INSERT INTO products(productID,productType,retailer,productName,image,productCondition,price,manufacturer,discount) VALUES(?,?,?,?,?,?,?,?,?);";
				pst = con.prepareStatement(query);
				pst.setString(1, phone.getId());
				pst.setString(2, "smartphones");
				pst.setString(3, phone.getRetailer());
				pst.setString(4, phone.getName());
				pst.setString(5, phone.getImage());
				pst.setString(6, phone.getCondition());
				pst.setDouble(7, phone.getPrice());
				pst.setString(8, phone.getManufacturer());
				pst.setDouble(9, phone.getDiscount());				
				pst.execute();
				
				//Loading Phone Accessories
				Accessories accessory = new Accessories();
				for(Map.Entry<String, Accessories> K : phone.getAccessories().entrySet()) {	
					accessory = K.getValue();
					query = "INSERT INTO productaccessories(productID,productType,accessoryID) VALUES(?,?,?);";
					pst = con.prepareStatement(query);
					pst.setString(1, phone.getId());
					pst.setString(2, "smartphones");
					pst.setString(3, accessory.getId());
					pst.execute();
				}
			}
			
			Laptops laptop = new Laptops();
			for(Map.Entry<String, Laptops> w : datastoreObj.laptopsMap.entrySet()) {	
				laptop = w.getValue(); 
				//Loading Laptops
				query = "INSERT INTO products(productID,productType,retailer,productName,image,productCondition,price,manufacturer,discount) VALUES(?,?,?,?,?,?,?,?,?);";
				pst = con.prepareStatement(query);
				pst.setString(1, laptop.getId());
				pst.setString(2, "laptops");
				pst.setString(3, laptop.getRetailer());
				pst.setString(4, laptop.getName());
				pst.setString(5, laptop.getImage());
				pst.setString(6, laptop.getCondition());
				pst.setDouble(7, laptop.getPrice());
				pst.setString(8, laptop.getManufacturer());
				pst.setDouble(9, laptop.getDiscount());				
				pst.execute();
				
				//Loading Laptop Accessories
				Accessories accessory = new Accessories();
				for(Map.Entry<String, Accessories> K : laptop.getAccessories().entrySet()) {	
					accessory = K.getValue();
					query = "INSERT INTO productaccessories(productID,productType,accessoryID) VALUES(?,?,?);";
					pst = con.prepareStatement(query);
					pst.setString(1, laptop.getId());
					pst.setString(2, "laptops");
					pst.setString(3, accessory.getId());
					pst.execute();
				}
			}
			
			ExternalStorage storage = new ExternalStorage();
			for(Map.Entry<String, ExternalStorage> w : datastoreObj.externalStorageMap.entrySet()) {	
				storage = w.getValue(); 
				//Loading ExternalStorage
				query = "INSERT INTO products(productID,productType,retailer,productName,image,productCondition,price,manufacturer,discount) VALUES(?,?,?,?,?,?,?,?,?);";
				pst = con.prepareStatement(query);
				pst.setString(1, storage.getId());
				pst.setString(2, "externalstorage");
				pst.setString(3, storage.getRetailer());
				pst.setString(4, storage.getName());
				pst.setString(5, storage.getImage());
				pst.setString(6, storage.getCondition());
				pst.setDouble(7, storage.getPrice());
				pst.setString(8, storage.getManufacturer());
				pst.setDouble(9, storage.getDiscount());				
				pst.execute();
				
				//Loading ExternalStorage Accessories
				Accessories accessory = new Accessories();
				for(Map.Entry<String, Accessories> K : storage.getAccessories().entrySet()) {	
					accessory = K.getValue();
					query = "INSERT INTO productaccessories(productID,productType,accessoryID) VALUES(?,?,?);";
					pst = con.prepareStatement(query);
					pst.setString(1, storage.getId());
					pst.setString(2, "externalstorage");
					pst.setString(3, accessory.getId());
					pst.execute();
				}
			}
			
			Accessories accessory = new Accessories();
			for(Map.Entry<String, Accessories> w : datastoreObj.accessoryMap.entrySet()) {	
				accessory = w.getValue(); 
				//Loading Accessories
				query = "INSERT INTO accessories(productID,productType,retailer,productName,image,productCondition,price,manufacturer,discount) VALUES(?,?,?,?,?,?,?,?,?);";
				pst = con.prepareStatement(query);
				pst.setString(1, accessory.getId());
				pst.setString(2, accessory.getType());
				pst.setString(3, accessory.getRetailer());
				pst.setString(4, accessory.getName());
				pst.setString(5, accessory.getImage());
				pst.setString(6, accessory.getCondition());
				pst.setDouble(7, accessory.getPrice());
				pst.setString(8, accessory.getManufacturer());
				pst.setDouble(9, accessory.getDiscount());				
				pst.execute();
			}
			
			Inventory inventory = new Inventory();
			for(Map.Entry<String, Inventory> w : datastoreObj.inventoryMap.entrySet()) {	
				inventory = w.getValue(); 
				//Loading inventory				
				query = "INSERT INTO inventory(productID,productName,price,qnty,productOnSale,rebate) VALUES(?,?,?,?,?,?);";
				pst = con.prepareStatement(query);
				pst.setString(1, inventory.getProductID());
				pst.setString(2, inventory.getProductName());
				pst.setDouble(3, inventory.getPrice());								
				pst.setInt(4, inventory.getQnty());				
				pst.setBoolean(5, inventory.getProductOnSale());
				pst.setBoolean(6, inventory.getRebate());				
				pst.execute();
			}
			
			con.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
		}
	}
	
	public HashMap<String, Product> getAjaxProductMapDB() {
		try {
			Product product = new Product();
			HashMap<String, Product> productMap = new HashMap<String, Product>();
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			String query = "select * from products;";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				product.setId(rs.getString("productID"));
				product.setType(rs.getString("productType"));
				product.setRetailer(rs.getString("retailer"));
				product.setName(rs.getString("productName"));
				product.setImage(rs.getString("image"));
				product.setCondition(rs.getString("productCondition"));
				product.setPrice(rs.getDouble("price"));
				product.setManufacturer(rs.getString("manufacturer"));
				product.setDiscount(rs.getDouble("discount"));
				productMap.put(product.getId(),product);				
				product = new Product();
			}
			con.close();
			return productMap;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
	
	public HashMap<String, SmartWatch> getWatchMapDB(){
		try {
			SmartWatch watch = new SmartWatch();
			Accessories accessory = new Accessories();
			HashMap<String, SmartWatch> watchMap = new HashMap<String, SmartWatch>();
			HashMap<String, Accessories> accessories = new HashMap<String, Accessories>();
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			String query = "select * from products where productType=?;";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, "smartwatches");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				watch.setId(rs.getString("productID"));
				watch.setRetailer(rs.getString("retailer"));
				watch.setName(rs.getString("productName"));
				watch.setImage(rs.getString("image"));
				watch.setCondition(rs.getString("productCondition"));
				watch.setPrice(rs.getDouble("price"));
				watch.setManufacturer(rs.getString("manufacturer"));
				watch.setDiscount(rs.getDouble("discount"));						
				
				pst = con.prepareStatement("select * from productaccessories where productID=?;");
				pst.setString(1, rs.getString("productID"));
				ResultSet rsAcc = pst.executeQuery();
				int i=1;
				while(rsAcc.next()) {
					accessory.setId(rsAcc.getString("accessoryID"));
					accessories.put("accessory-" + i, accessory);
					accessory = new Accessories();
					i++;
				}
				watch.setAccessories(accessories);
				watchMap.put(watch.getId(),watch);
				watch = new SmartWatch();
				accessories = new HashMap<String, Accessories>();
			}
			con.close();
			return watchMap;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
	
	public HashMap<String, Speakers> getSpeakerMapDB(){
		try {
			Speakers speaker = new Speakers();
			Accessories accessory = new Accessories();
			HashMap<String, Speakers> speakerMap = new HashMap<String, Speakers>();
			HashMap<String, Accessories> accessories = new HashMap<String, Accessories>();
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			String query = "select * from products where productType=?;";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, "speakers");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				speaker.setId(rs.getString("productID"));
				speaker.setRetailer(rs.getString("retailer"));
				speaker.setName(rs.getString("productName"));
				speaker.setImage(rs.getString("image"));
				speaker.setCondition(rs.getString("productCondition"));
				speaker.setPrice(rs.getDouble("price"));
				speaker.setManufacturer(rs.getString("manufacturer"));
				speaker.setDiscount(rs.getDouble("discount"));						
				
				pst = con.prepareStatement("select * from productaccessories where productID=?;");
				pst.setString(1, rs.getString("productID"));
				ResultSet rsAcc = pst.executeQuery();
				int i=1;
				while(rsAcc.next()) {
					accessory.setId(rsAcc.getString("accessoryID"));
					accessories.put("accessory-" + i, accessory);
					accessory = new Accessories();
					i++;
				}
				speaker.setAccessories(accessories);
				speakerMap.put(speaker.getId(),speaker);
				speaker = new Speakers();
				accessories = new HashMap<String, Accessories>();
			}
			con.close();
			return speakerMap;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
	
	public HashMap<String, Headphones> getHeadphoneMapDB(){
		try {
			Headphones headphone = new Headphones();
			Accessories accessory = new Accessories();
			HashMap<String, Headphones> headphoneMap = new HashMap<String, Headphones>();
			HashMap<String, Accessories> accessories = new HashMap<String, Accessories>();
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			String query = "select * from products where productType=?;";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, "headphones");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				headphone.setId(rs.getString("productID"));
				headphone.setRetailer(rs.getString("retailer"));
				headphone.setName(rs.getString("productName"));
				headphone.setImage(rs.getString("image"));
				headphone.setCondition(rs.getString("productCondition"));
				headphone.setPrice(rs.getDouble("price"));
				headphone.setManufacturer(rs.getString("manufacturer"));
				headphone.setDiscount(rs.getDouble("discount"));						
				
				pst = con.prepareStatement("select * from productaccessories where productID=?;");
				pst.setString(1, rs.getString("productID"));
				ResultSet rsAcc = pst.executeQuery();
				//accessory = null;
				int i=1;
				while(rsAcc.next()) {
					accessory.setId(rsAcc.getString("accessoryID"));
					accessories.put("accessory-" + i, accessory);
					accessory = new Accessories();
					i++;
				}
				headphone.setAccessories(accessories);
				headphoneMap.put(headphone.getId(),headphone);
				headphone = new Headphones();
				accessories = new HashMap<String, Accessories>();
			}
			con.close();
			return headphoneMap;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
	
	public HashMap<String, Phones> getPhoneMapDB(){
		try {
			Phones phone = new Phones();
			Accessories accessory = new Accessories();
			HashMap<String, Phones> phoneMap = new HashMap<String, Phones>();
			HashMap<String, Accessories> accessories = new HashMap<String, Accessories>();
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			String query = "select * from products where productType=?;";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, "smartphones");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				phone.setId(rs.getString("productID"));
				phone.setRetailer(rs.getString("retailer"));
				phone.setName(rs.getString("productName"));
				phone.setImage(rs.getString("image"));
				phone.setCondition(rs.getString("productCondition"));
				phone.setPrice(rs.getDouble("price"));
				phone.setManufacturer(rs.getString("manufacturer"));
				phone.setDiscount(rs.getDouble("discount"));						
				
				pst = con.prepareStatement("select * from productaccessories where productID=?;");
				pst.setString(1, rs.getString("productID"));
				ResultSet rsAcc = pst.executeQuery();
				//accessory = null;
				int i=1;
				while(rsAcc.next()) {
					accessory.setId(rsAcc.getString("accessoryID"));
					accessories.put("accessory-" + i, accessory);
					accessory = new Accessories();
					i++;
				}
				phone.setAccessories(accessories);
				phoneMap.put(phone.getId(),phone);
				phone = new Phones();
				accessories = new HashMap<String, Accessories>();
			}
			con.close();
			return phoneMap;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
	
	public HashMap<String, Laptops> getLaptopMapDB(){
		try {
			Laptops laptop = new Laptops();
			Accessories accessory = new Accessories();
			HashMap<String, Laptops> laptopMap = new HashMap<String, Laptops>();
			HashMap<String, Accessories> accessories = new HashMap<String, Accessories>();
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			String query = "select * from products where productType=?;";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, "laptops");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				laptop.setId(rs.getString("productID"));
				laptop.setRetailer(rs.getString("retailer"));
				laptop.setName(rs.getString("productName"));
				laptop.setImage(rs.getString("image"));
				laptop.setCondition(rs.getString("productCondition"));
				laptop.setPrice(rs.getDouble("price"));
				laptop.setManufacturer(rs.getString("manufacturer"));
				laptop.setDiscount(rs.getDouble("discount"));						
				
				pst = con.prepareStatement("select * from productaccessories where productID=?;");
				pst.setString(1, rs.getString("productID"));
				ResultSet rsAcc = pst.executeQuery();
				//accessory = null;
				int i=1;
				while(rsAcc.next()) {
					accessory.setId(rsAcc.getString("accessoryID"));
					accessories.put("accessory-" + i, accessory);
					accessory = new Accessories();
					i++;
				}
				laptop.setAccessories(accessories);
				laptopMap.put(laptop.getId(),laptop);
				laptop = new Laptops();
				accessories = new HashMap<String, Accessories>();
			}
			con.close();
			return laptopMap;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
	
	public HashMap<String, ExternalStorage> getStorageMapDB(){
		try {
			ExternalStorage storage = new ExternalStorage();
			Accessories accessory = new Accessories();
			HashMap<String, ExternalStorage> storageMap = new HashMap<String, ExternalStorage>();
			HashMap<String, Accessories> accessories = new HashMap<String, Accessories>();
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			String query = "select * from products where productType=?;";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, "externalstorage");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				storage.setId(rs.getString("productID"));
				storage.setRetailer(rs.getString("retailer"));
				storage.setName(rs.getString("productName"));
				storage.setImage(rs.getString("image"));
				storage.setCondition(rs.getString("productCondition"));
				storage.setPrice(rs.getDouble("price"));
				storage.setManufacturer(rs.getString("manufacturer"));
				storage.setDiscount(rs.getDouble("discount"));						
				
				pst = con.prepareStatement("select * from productaccessories where productID=?;");
				pst.setString(1, rs.getString("productID"));
				ResultSet rsAcc = pst.executeQuery();
				//accessory = null;
				int i=1;
				while(rsAcc.next()) {
					accessory.setId(rsAcc.getString("accessoryID"));
					accessories.put("accessory-" + i, accessory);
					accessory = new Accessories();
					i++;
				}
				storage.setAccessories(accessories);
				storageMap.put(storage.getId(),storage);
				storage = new ExternalStorage();
				accessories = new HashMap<String, Accessories>();
			}
			con.close();
			return storageMap;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
	
	public HashMap<String, Accessories> getAccessoryMapDB(){
		try {
			Accessories accessory = new Accessories();
			HashMap<String, Accessories> accessoryMap = new HashMap<String, Accessories>();
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			String query = "select * from accessories;";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				accessory.setId(rs.getString("productID"));
				accessory.setRetailer(rs.getString("retailer"));
				accessory.setName(rs.getString("productName"));
				accessory.setImage(rs.getString("image"));
				accessory.setCondition(rs.getString("productCondition"));
				accessory.setPrice(rs.getDouble("price"));
				accessory.setManufacturer(rs.getString("manufacturer"));
				accessory.setDiscount(rs.getDouble("discount"));
				accessory.setType(rs.getString("productType"));
				
				accessoryMap.put(accessory.getId(),accessory);
				accessory = new Accessories();
			}
			con.close();
			return accessoryMap;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
	
	public HashMap<String, Inventory> getInventoryMapDB(){
		try {
			Inventory inventory = new Inventory();
			HashMap<String, Inventory> inventoryMap = new HashMap<String, Inventory>();
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			String query = "select * from inventory;";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				inventory.setProductID(rs.getString("productID"));
				inventory.setProductName(rs.getString("productName"));
				inventory.setPrice(rs.getDouble("price"));
				inventory.setQnty(rs.getInt("qnty"));
				inventory.setProductOnSale(rs.getBoolean("productOnSale"));
				inventory.setRebate(rs.getBoolean("rebate"));
				
				inventoryMap.put(inventory.getProductID(), inventory);
				inventory = new Inventory();				
			}
			con.close();
			return inventoryMap;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
	
	public HashMap<String, Inventory> getProductOnSaleMapDB(){
		try {
			Inventory inventory = new Inventory();
			HashMap<String, Inventory> inventoryMap = new HashMap<String, Inventory>();
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			String query = "select * from inventory where productOnSale=TRUE;";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				inventory.setProductID(rs.getString("productID"));
				inventory.setProductName(rs.getString("productName"));
				inventory.setPrice(rs.getDouble("price"));
				inventory.setQnty(rs.getInt("qnty"));
				inventory.setProductOnSale(rs.getBoolean("productOnSale"));
				inventory.setRebate(rs.getBoolean("rebate"));
				
				inventoryMap.put(inventory.getProductID(), inventory);
				inventory = new Inventory();				
			}
			con.close();
			return inventoryMap;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
	
	
	
	public HashMap<String, Inventory> getRebateMapDB(){
		try {
			Inventory inventory = new Inventory();
			HashMap<String, Inventory> inventoryMap = new HashMap<String, Inventory>();
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			String query = "select * from inventory where rebate=TRUE;";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				inventory.setProductID(rs.getString("productID"));
				inventory.setProductName(rs.getString("productName"));
				inventory.setPrice(rs.getDouble("price"));
				inventory.setQnty(rs.getInt("qnty"));
				inventory.setProductOnSale(rs.getBoolean("productOnSale"));
				inventory.setRebate(rs.getBoolean("rebate"));
				
				inventoryMap.put(inventory.getProductID(), inventory);
				inventory = new Inventory();				
			}
			con.close();
			return inventoryMap;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
	
	public HashMap<String, Sales> getSalesMapDB(){
		try {
			Sales sales = new Sales();
			HashMap<String, Sales> salesMap = new HashMap<String, Sales>();
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			String query = "select o.productID,i.productName,price,sum(o.qnty) as SalesCount,(price * sum(o.qnty)) as TotalSales from orderProductList o,inventory i where o.productID=i.productID group by o.productID;";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				sales.setProductID(rs.getString("productID"));
				sales.setProductName(rs.getString("productName"));
				sales.setPrice(rs.getDouble("price"));
				sales.setSalesCount(rs.getInt("SalesCount"));
				sales.setTotalSales(rs.getDouble("TotalSales"));
				salesMap.put(sales.getProductName(), sales);
				sales = new Sales();				
			}
			con.close();
			return salesMap;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
	
	public Vector<Sales> getDailySalesData(){
		try {
			Sales sales = new Sales();
			Vector<Sales> salesList = new Vector<Sales>();
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			String query = "select orderDate,sum(totalPrice) as TotalSales from orders group by orderDate;";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				sales.setOrderDate(rs.getString("orderDate"));
				sales.setTotalSales(rs.getDouble("TotalSales"));
				salesList.add(sales);
				sales = new Sales();				
			}
			con.close();
			return salesList;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
	
	public String[] getAccessoryList(String productID) {
		try {
			String[] accessoryList = new String[30];
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			String query = "select * from productAccessories where productID=?;";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, productID);
			ResultSet rs = pst.executeQuery();
			int i=0;
			while(rs.next()) {
				accessoryList[i] = rs.getString("accessoryID");
				i++;
			}
			return accessoryList;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
	
	public boolean deleteProduct(String productID) {
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			String query = "DELETE FROM products WHERE productID=?;";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, productID);
			pst.execute();
			query = "DELETE FROM productaccessories WHERE productID=?;";
			pst = con.prepareStatement(query);
			pst.setString(1, productID);
			boolean result = pst.execute();
			con.close();
			return result;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return false;
		}
	}
	
	public boolean addProduct(String productID,String productType,String retailer,String productName,String image,String condition,String price,String manufacturer,String discount,String[] accessoryList) {
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			//Loading Product
			String query = "INSERT INTO products(productID,productType,retailer,productName,image,productCondition,price,manufacturer,discount) VALUES(?,?,?,?,?,?,?,?,?);";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, productID);
			pst.setString(2, productType);
			pst.setString(3, retailer);
			pst.setString(4, productName);
			pst.setString(5, image);
			pst.setString(6, condition);
			pst.setDouble(7, Double.parseDouble(price));
			pst.setString(8, manufacturer);
			pst.setDouble(9, Double.parseDouble(discount));				
			boolean result = pst.execute();
			if(productType!="accessories" && accessoryList!=null) {
				//Loading Product Accessories
				int i = 0;
				while(accessoryList[i]!=null) {
					query = "INSERT INTO productaccessories(productID,productType,accessoryID) VALUES(?,?,?);";
					pst = con.prepareStatement(query);
					pst.setString(1, productID);
					pst.setString(2, productType);
					pst.setString(3, accessoryList[i]);
					pst.execute();
					i++;
				}
			}
			con.close();
			return result;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return false;
		}
	}
	
	public boolean updateProduct(String oldID,String newID,String productType,String retailer,String productName,String image,String condition,String price,String manufacturer,String discount,String[] accessoryList) {
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			//Loading Product
			String query = "UPDATE products SET productID=?,productType=?,retailer=?,productName=?,image=?,productCondition=?,price=?,manufacturer=?,discount=? WHERE productID=?;";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, newID);
			pst.setString(2, productType);
			pst.setString(3, retailer);
			pst.setString(4, productName);
			pst.setString(5, image);
			pst.setString(6, condition);
			pst.setDouble(7, Double.parseDouble(price));
			pst.setString(8, manufacturer);
			pst.setDouble(9, Double.parseDouble(discount));	
			pst.setString(10, oldID);
			boolean result = pst.execute();
			
			String[] oldAccessoryList = new String[30];
			oldAccessoryList = getAccessoryList(oldID);
			if(productType!="accessories" && accessoryList[0]!=null) {
				//Loading Product Accessories
				if(oldAccessoryList[0]!=null) {
					for(int i=0;i<7;i++) {
						query = "UPDATE productaccessories SET productID=?,productType=?,accessoryID=? WHERE accessoryID=?";
						pst = con.prepareStatement(query);
						pst.setString(1, newID);
						pst.setString(2, productType);
						pst.setString(3, accessoryList[i]);
						pst.setString(4, oldAccessoryList[i]);
						pst.execute();
					}
				}
				else
					System.out.println("OldAccessoryList returned null");
				
			}
			con.close();
			return result;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return false;
		}
	}
	
	public Users getUserProfile(String username) {
		try {
			Users user = null;
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			String query = "select * from users where username=?;";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				user = new Users(rs.getString("userType"),rs.getString("username"),rs.getString("firstName"),rs.getString("lastName"),rs.getString("address"),rs.getInt("zipcode"),rs.getString("emailID"),rs.getString("dob"),rs.getString("password"));
			}
			con.close();
			return user;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
		
	public boolean writeUserProfile(Users profile) {
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			String query = "INSERT INTO users(userType,username,firstName,lastName,address,zipcode,emailID,dob,password) VALUES(?,?,?,?,?,?,?,?,?);";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, profile.getUserType());
			pst.setString(2, profile.getUsername());
			pst.setString(3, profile.getFirstName());
			pst.setString(4, profile.getLastName());
			pst.setString(5, profile.getAddress());
			pst.setInt(6, profile.getZipCode());
			pst.setString(7, profile.getEmailID());
			pst.setString(8, profile.getDOB());
			pst.setString(9, profile.getPWD());
			boolean result = pst.execute();
			con.close();
			return result;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return false;
		}
	}
	
	public boolean writeOrders(Orders order) {
		try {			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			String[] prodID = order.getProductID();
			String[] prodType = order.getProductType();
			int[] qnty = order.getQuantity();
			int[] inventoryQnty = new int[100];
			int i=0;
			boolean flag = true;
			if(prodID!=null) {
				for(i=0;i<prodID.length;i++) {
					if(prodID[i]==null) {
						break;
					}
					String query = "select qnty from inventory where productID=?;";
					PreparedStatement pst = con.prepareStatement(query);					
					pst.setString(1, prodID[i]);
					System.out.println("ProductID-" + prodID[i]);
					ResultSet rs = pst.executeQuery();
					if(rs.next()) {
						inventoryQnty[i] = rs.getInt(1);
					}
				}
			}
			for(int j=0;j<i;j++) {
				if(inventoryQnty[j]<qnty[j])
					flag = false;
			}
			
			if(flag) {
				if(prodID!=null) {
					for(int k=0;k<prodID.length;k++) {
						if(prodID[k]==null)
						{
							break;
						}
						String query = "INSERT INTO orderProductList(confirmationNumber,productID,productType,qnty) VALUES(?,?,?,?);";
						PreparedStatement pst = con.prepareStatement(query);
						pst.setInt(1, order.getConfirmationNumber());
						pst.setString(2,prodID[k]);
						pst.setString(3,prodType[k]);
						pst.setInt(4, qnty[k]);
						pst.execute();
						
						query = "UPDATE inventory SET qnty=? WHERE productID=?;";
						pst = con.prepareStatement(query);
						pst.setInt(1, inventoryQnty[k]-qnty[k]);
						pst.setString(2,prodID[k]);
						pst.execute();
					}
				}
				
				
				String query = "INSERT INTO orders(customerName,username,orderDate,confirmationNumber,deliveryDate,shippingPrice,totalPrice,productWarranty,country,state,address1,address2,city,zipCode,mobileNumber,emailAddress) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
				PreparedStatement pst = con.prepareStatement(query);
				pst.setString(1,order.getCustomerName());
				pst.setString(2, order.getUsername());
				pst.setString(3, order.getOrderDate());
				pst.setInt(4, order.getConfirmationNumber());
				pst.setString(5, order.getDeliveryDate());
				pst.setDouble(6, order.getShippingPrice());
				pst.setDouble(7, order.getTotalPrice());
				pst.setString(8, order.getProductWarranty());
				pst.setString(9, order.getCountry());
				pst.setString(10, order.getState());
				pst.setString(11, order.getAddress1());
				pst.setString(12, order.getAddress2());
				pst.setString(13, order.getCity());
				pst.setInt(14, order.getZipCode());
				pst.setString(15, order.getMobileNumber());
				pst.setString(16, order.getEmailAddress());
				pst.execute();
			}
			con.close();
			return flag;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return false;
		}
	}
	
	public void insertSampleOrders() {
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			String query = "SELECT confirmationNumber FROM orders WHERE username='user1';";
			PreparedStatement pst = con.prepareStatement(query);					
			ResultSet rsDel = pst.executeQuery();
			while(rsDel.next()) {
				query = "DELETE FROM orderProductList WHERE confirmationNumber=?;";
				pst = con.prepareStatement(query);
				pst.setInt(1, rsDel.getInt("confirmationNumber"));
				pst.execute();
			}
			query = "DELETE FROM orders WHERE username='user1';";
			pst = con.prepareStatement(query);
			pst.execute();

			SmartWatch watch = new SmartWatch();
			for(Map.Entry<String, SmartWatch> w : getWatchMapDB().entrySet()) {	
				watch = w.getValue(); 
				
				Random rand = new Random();
				int  confirmationNumber = rand.nextInt(999999999) + 99999999;
				
				int min = 1;
				int max = 4;
				int Quantity = rand.nextInt((max - min) + 1) + min;
				
				int inventoryQnty = 0;				
				query = "select qnty from inventory where productID=?;";
				pst = con.prepareStatement(query);					
				pst.setString(1, watch.getId());
				ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					inventoryQnty = rs.getInt(1);
				}
				
				if(inventoryQnty>=Quantity) {
					query = "UPDATE inventory SET qnty=? WHERE productID=?;";
					pst = con.prepareStatement(query);
					pst.setInt(1, inventoryQnty-Quantity);
					pst.setString(2,watch.getId());
					pst.execute();
					
					query = "INSERT INTO orderProductList(confirmationNumber,productID,productType,qnty) VALUES(?,?,?,?);";
					pst = con.prepareStatement(query);				
					pst.setInt(1, confirmationNumber);
					pst.setString(2,watch.getId());
					pst.setString(3, "smartwatches");				
					pst.setInt(4, Quantity);
					pst.execute();
					
					query = "INSERT INTO orders(customerName,username,orderDate,confirmationNumber,deliveryDate,shippingPrice,totalPrice,productWarranty,country,state,address1,address2,city,zipCode,mobileNumber,emailAddress) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
					pst = con.prepareStatement(query);
					pst.setString(1,"user1");
					pst.setString(2, "user1");
					DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					
					Calendar orderDateCalender = Calendar.getInstance();
					orderDateCalender.setTime(date);
					orderDateCalender.add(Calendar.DAY_OF_MONTH, -6);
					Date orderDate = orderDateCalender.getTime();
					SimpleDateFormat sdfOrderDate = new SimpleDateFormat("yyyy/MM/dd");
					
					pst.setString(3, sdfOrderDate.format(orderDate));
					pst.setInt(4, confirmationNumber);
					int noOfDays = 14; //i.e two weeks
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);            
					calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
					Date deliveryDate = calendar.getTime();
					pst.setString(5, sdf.format(deliveryDate));
					double shippingPrice = 5.60;
					double warranty = 24.99;
					pst.setDouble(6, shippingPrice);
					
					double totPrice = warranty + shippingPrice + (Quantity * watch.getPrice());
					pst.setDouble(7, totPrice);
					pst.setString(8, "OneYearWarranty");
					
					String[] cities = {"New York","Washington D.C","San Francisco","Chicago","Boston","Log Angeles","Seattle","San Diego","Austin","Philadelphia","New Orleans","Portland","Nashville","Houston","Denver","Miami","Detroit","Dallas","Las Vegas","Phoenix","San Jose","Minneapolis","Atlanta","Indianapolis","Orlando","Pittsburgh","Madison"};
			        List<String> names = Arrays.asList(cities);
			        int index = new Random().nextInt(names.size());
			        String cityName = names.get(index);
			        
			        String[] states = {"California","Texas","Georgia","Tennessee","Ohio","Indiana","Arizona","Virginia","Florida","Alabama","Missouri","Massachusetts","Pennsylvania","Wisconsin","Arkansas","Alaska","Utah","Illinois","Minnesota","New Jersey","South Carolina","West Virginia","Hawaii","Michigan"};
			        List<String> stateNames = Arrays.asList(states);
			        int stateIndex = new Random().nextInt(stateNames.size());
			        String stateName = stateNames.get(stateIndex);
			        
					pst.setString(9, "USA");
					pst.setString(10, stateName);
					pst.setString(11, "Address1");
					pst.setString(12, "Address2");
					pst.setString(13, cityName);
					int minZip = 60601;
					int maxZip = 60616;
					int randomZip = rand.nextInt((maxZip - minZip) + 1) + minZip;
					pst.setInt(14, randomZip);
					int  mobileNumber = rand.nextInt(999999999) + 99999999;
					pst.setString(15, String.valueOf(mobileNumber));
					pst.setString(16, "user1@gmail.com");
					pst.execute();
				}
			}
			
			Speakers speaker = new Speakers();
			for(Map.Entry<String, Speakers> w : getSpeakerMapDB().entrySet()) {	
				speaker = w.getValue(); 
				
				Random rand = new Random();
				int  confirmationNumber = rand.nextInt(999999999) + 99999999;
				
				int min = 1;
				int max = 4;
				int Quantity = rand.nextInt((max - min) + 1) + min;
				
				int inventoryQnty = 0;				
				query = "select qnty from inventory where productID=?;";
				pst = con.prepareStatement(query);					
				pst.setString(1, speaker.getId());
				ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					inventoryQnty = rs.getInt(1);
				}
				
				if(inventoryQnty>=Quantity) {
					query = "UPDATE inventory SET qnty=? WHERE productID=?;";
					pst = con.prepareStatement(query);
					pst.setInt(1, inventoryQnty-Quantity);
					pst.setString(2,speaker.getId());
					pst.execute();
					
					query = "INSERT INTO orderProductList(confirmationNumber,productID,productType,qnty) VALUES(?,?,?,?);";
					pst = con.prepareStatement(query);				
					pst.setInt(1, confirmationNumber);
					pst.setString(2,speaker.getId());
					pst.setString(3, "speakers");				
					pst.setInt(4, Quantity);
					pst.execute();
					
					query = "INSERT INTO orders(customerName,username,orderDate,confirmationNumber,deliveryDate,shippingPrice,totalPrice,productWarranty,country,state,address1,address2,city,zipCode,mobileNumber,emailAddress) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
					pst = con.prepareStatement(query);
					pst.setString(1,"user1");
					pst.setString(2, "user1");
					DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					
					Calendar orderDateCalender = Calendar.getInstance();
					orderDateCalender.setTime(date);
					orderDateCalender.add(Calendar.DAY_OF_MONTH, -5);
					Date orderDate = orderDateCalender.getTime();
					SimpleDateFormat sdfOrderDate = new SimpleDateFormat("yyyy/MM/dd");
					
					pst.setString(3, sdfOrderDate.format(orderDate));
					pst.setInt(4, confirmationNumber);
					int noOfDays = 14; //i.e two weeks
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);            
					calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
					Date deliveryDate = calendar.getTime();
					pst.setString(5, sdf.format(deliveryDate));
					double shippingPrice = 5.60;
					double warranty = 24.99;
					pst.setDouble(6, shippingPrice);
					
					double totPrice = warranty + shippingPrice + (Quantity * speaker.getPrice());
					pst.setDouble(7, totPrice);
					pst.setString(8, "OneYearWarranty");
					
					String[] cities = {"New York","Washington D.C","San Francisco","Chicago","Boston","Log Angeles","Seattle","San Diego","Austin","Philadelphia","New Orleans","Portland","Nashville","Houston","Denver","Miami","Detroit","Dallas","Las Vegas","Phoenix","San Jose","Minneapolis","Atlanta","Indianapolis","Orlando","Pittsburgh","Madison"};
			        List<String> names = Arrays.asList(cities);
			        int index = new Random().nextInt(names.size());
			        String cityName = names.get(index);
			        
			        String[] states = {"California","Texas","Georgia","Tennessee","Ohio","Indiana","Arizona","Virginia","Florida","Alabama","Missouri","Massachusetts","Pennsylvania","Wisconsin","Arkansas","Alaska","Utah","Illinois","Minnesota","New Jersey","South Carolina","West Virginia","Hawaii","Michigan"};
			        List<String> stateNames = Arrays.asList(states);
			        int stateIndex = new Random().nextInt(stateNames.size());
			        String stateName = stateNames.get(stateIndex);
			        
					pst.setString(9, "USA");
					pst.setString(10, stateName);
					pst.setString(11, "Address1");
					pst.setString(12, "Address2");
					pst.setString(13, cityName);
					int minZip = 60601;
					int maxZip = 60616;
					int randomZip = rand.nextInt((maxZip - minZip) + 1) + minZip;
					pst.setInt(14, randomZip);
					int  mobileNumber = rand.nextInt(999999999) + 99999999;
					pst.setString(15, String.valueOf(mobileNumber));
					pst.setString(16, "user1@gmail.com");
					pst.execute();
				}
			}
			
			Headphones headphone = new Headphones();
			for(Map.Entry<String, Headphones> w : getHeadphoneMapDB().entrySet()) {	
				headphone = w.getValue(); 
				
				Random rand = new Random();
				int  confirmationNumber = rand.nextInt(999999999) + 99999999;
				
				int min = 1;
				int max = 4;
				int Quantity = rand.nextInt((max - min) + 1) + min;
				
				int inventoryQnty = 0;				
				query = "select qnty from inventory where productID=?;";
				pst = con.prepareStatement(query);					
				pst.setString(1, headphone.getId());
				ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					inventoryQnty = rs.getInt(1);
				}
				
				if(inventoryQnty>=Quantity) {
					query = "UPDATE inventory SET qnty=? WHERE productID=?;";
					pst = con.prepareStatement(query);
					pst.setInt(1, inventoryQnty-Quantity);
					pst.setString(2,headphone.getId());
					pst.execute();
					
					query = "INSERT INTO orderProductList(confirmationNumber,productID,productType,qnty) VALUES(?,?,?,?);";
					pst = con.prepareStatement(query);				
					pst.setInt(1, confirmationNumber);
					pst.setString(2,headphone.getId());
					pst.setString(3, "headphones");				
					pst.setInt(4, Quantity);
					pst.execute();
					
					query = "INSERT INTO orders(customerName,username,orderDate,confirmationNumber,deliveryDate,shippingPrice,totalPrice,productWarranty,country,state,address1,address2,city,zipCode,mobileNumber,emailAddress) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
					pst = con.prepareStatement(query);
					pst.setString(1,"user1");
					pst.setString(2, "user1");
					DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					Calendar orderDateCalender = Calendar.getInstance();
					orderDateCalender.setTime(date);
					orderDateCalender.add(Calendar.DAY_OF_MONTH, -4);
					Date orderDate = orderDateCalender.getTime();
					SimpleDateFormat sdfOrderDate = new SimpleDateFormat("yyyy/MM/dd");
					pst.setString(3, sdfOrderDate.format(orderDate));
					pst.setInt(4, confirmationNumber);
					int noOfDays = 14; //i.e two weeks
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);            
					calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
					Date deliveryDate = calendar.getTime();
					pst.setString(5, sdf.format(deliveryDate));
					double shippingPrice = 5.60;
					double warranty = 24.99;
					pst.setDouble(6, shippingPrice);
					
					double totPrice = warranty + shippingPrice + (Quantity * headphone.getPrice());
					pst.setDouble(7, totPrice);
					pst.setString(8, "OneYearWarranty");
					
					String[] cities = {"New York","Washington D.C","San Francisco","Chicago","Boston","Log Angeles","Seattle","San Diego","Austin","Philadelphia","New Orleans","Portland","Nashville","Houston","Denver","Miami","Detroit","Dallas","Las Vegas","Phoenix","San Jose","Minneapolis","Atlanta","Indianapolis","Orlando","Pittsburgh","Madison"};
			        List<String> names = Arrays.asList(cities);
			        int index = new Random().nextInt(names.size());
			        String cityName = names.get(index);
			        
			        String[] states = {"California","Texas","Georgia","Tennessee","Ohio","Indiana","Arizona","Virginia","Florida","Alabama","Missouri","Massachusetts","Pennsylvania","Wisconsin","Arkansas","Alaska","Utah","Illinois","Minnesota","New Jersey","South Carolina","West Virginia","Hawaii","Michigan"};
			        List<String> stateNames = Arrays.asList(states);
			        int stateIndex = new Random().nextInt(stateNames.size());
			        String stateName = stateNames.get(stateIndex);
			        
					pst.setString(9, "USA");
					pst.setString(10, stateName);
					pst.setString(11, "Address1");
					pst.setString(12, "Address2");
					pst.setString(13, cityName);
					int minZip = 60601;
					int maxZip = 60616;
					int randomZip = rand.nextInt((maxZip - minZip) + 1) + minZip;
					pst.setInt(14, randomZip);
					int  mobileNumber = rand.nextInt(999999999) + 99999999;
					pst.setString(15, String.valueOf(mobileNumber));
					pst.setString(16, "user1@gmail.com");
					pst.execute();
				}
			}
			
			Phones phone = new Phones();
			for(Map.Entry<String, Phones> w : getPhoneMapDB().entrySet()) {	
				phone = w.getValue(); 
				
				Random rand = new Random();
				int  confirmationNumber = rand.nextInt(999999999) + 99999999;
				
				int min = 1;
				int max = 4;
				int Quantity = rand.nextInt((max - min) + 1) + min;
				
				int inventoryQnty = 0;				
				query = "select qnty from inventory where productID=?;";
				pst = con.prepareStatement(query);					
				pst.setString(1, phone.getId());
				ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					inventoryQnty = rs.getInt(1);
				}
				
				if(inventoryQnty>=Quantity) {
					query = "UPDATE inventory SET qnty=? WHERE productID=?;";
					pst = con.prepareStatement(query);
					pst.setInt(1, inventoryQnty-Quantity);
					pst.setString(2,phone.getId());
					pst.execute();
					
					query = "INSERT INTO orderProductList(confirmationNumber,productID,productType,qnty) VALUES(?,?,?,?);";
					pst = con.prepareStatement(query);				
					pst.setInt(1, confirmationNumber);
					pst.setString(2,phone.getId());
					pst.setString(3, "smartphones");				
					pst.setInt(4, Quantity);
					pst.execute();
					
					query = "INSERT INTO orders(customerName,username,orderDate,confirmationNumber,deliveryDate,shippingPrice,totalPrice,productWarranty,country,state,address1,address2,city,zipCode,mobileNumber,emailAddress) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
					pst = con.prepareStatement(query);
					pst.setString(1,"user1");
					pst.setString(2, "user1");
					DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					Calendar orderDateCalender = Calendar.getInstance();
					orderDateCalender.setTime(date);
					orderDateCalender.add(Calendar.DAY_OF_MONTH, -3);
					Date orderDate = orderDateCalender.getTime();
					SimpleDateFormat sdfOrderDate = new SimpleDateFormat("yyyy/MM/dd");
					pst.setString(3, sdfOrderDate.format(orderDate));
					pst.setInt(4, confirmationNumber);
					int noOfDays = 14; //i.e two weeks
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);            
					calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
					Date deliveryDate = calendar.getTime();
					pst.setString(5, sdf.format(deliveryDate));
					double shippingPrice = 5.60;
					double warranty = 24.99;
					pst.setDouble(6, shippingPrice);
					
					double totPrice = warranty + shippingPrice + (Quantity * phone.getPrice());
					pst.setDouble(7, totPrice);
					pst.setString(8, "OneYearWarranty");
					
					String[] cities = {"New York","Washington D.C","San Francisco","Chicago","Boston","Log Angeles","Seattle","San Diego","Austin","Philadelphia","New Orleans","Portland","Nashville","Houston","Denver","Miami","Detroit","Dallas","Las Vegas","Phoenix","San Jose","Minneapolis","Atlanta","Indianapolis","Orlando","Pittsburgh","Madison"};
			        List<String> names = Arrays.asList(cities);
			        int index = new Random().nextInt(names.size());
			        String cityName = names.get(index);
			        
			        String[] states = {"California","Texas","Georgia","Tennessee","Ohio","Indiana","Arizona","Virginia","Florida","Alabama","Missouri","Massachusetts","Pennsylvania","Wisconsin","Arkansas","Alaska","Utah","Illinois","Minnesota","New Jersey","South Carolina","West Virginia","Hawaii","Michigan"};
			        List<String> stateNames = Arrays.asList(states);
			        int stateIndex = new Random().nextInt(stateNames.size());
			        String stateName = stateNames.get(stateIndex);
			        
					pst.setString(9, "USA");
					pst.setString(10, stateName);
					pst.setString(11, "Address1");
					pst.setString(12, "Address2");
					pst.setString(13, cityName);
					int minZip = 60601;
					int maxZip = 60616;
					int randomZip = rand.nextInt((maxZip - minZip) + 1) + minZip;
					pst.setInt(14, randomZip);
					int  mobileNumber = rand.nextInt(999999999) + 99999999;
					pst.setString(15, String.valueOf(mobileNumber));
					pst.setString(16, "user1@gmail.com");
					pst.execute();
				}
			}
			
			Laptops laptop = new Laptops();
			for(Map.Entry<String, Laptops> w : getLaptopMapDB().entrySet()) {	
				laptop = w.getValue(); 
				
				Random rand = new Random();
				int  confirmationNumber = rand.nextInt(999999999) + 99999999;
				
				int min = 1;
				int max = 4;
				int Quantity = rand.nextInt((max - min) + 1) + min;
				
				int inventoryQnty = 0;				
				query = "select qnty from inventory where productID=?;";
				pst = con.prepareStatement(query);					
				pst.setString(1, laptop.getId());
				ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					inventoryQnty = rs.getInt(1);
				}
				
				if(inventoryQnty>=Quantity) {
					query = "UPDATE inventory SET qnty=? WHERE productID=?;";
					pst = con.prepareStatement(query);
					pst.setInt(1, inventoryQnty-Quantity);
					pst.setString(2,laptop.getId());
					pst.execute();
					
					query = "INSERT INTO orderProductList(confirmationNumber,productID,productType,qnty) VALUES(?,?,?,?);";
					pst = con.prepareStatement(query);				
					pst.setInt(1, confirmationNumber);
					pst.setString(2,laptop.getId());
					pst.setString(3, "laptops");				
					pst.setInt(4, Quantity);
					pst.execute();
					
					query = "INSERT INTO orders(customerName,username,orderDate,confirmationNumber,deliveryDate,shippingPrice,totalPrice,productWarranty,country,state,address1,address2,city,zipCode,mobileNumber,emailAddress) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
					pst = con.prepareStatement(query);
					pst.setString(1,"user1");
					pst.setString(2, "user1");
					DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					Calendar orderDateCalender = Calendar.getInstance();
					orderDateCalender.setTime(date);
					orderDateCalender.add(Calendar.DAY_OF_MONTH, -2);
					Date orderDate = orderDateCalender.getTime();
					SimpleDateFormat sdfOrderDate = new SimpleDateFormat("yyyy/MM/dd");
					pst.setString(3, sdfOrderDate.format(orderDate));
					pst.setInt(4, confirmationNumber);
					int noOfDays = 14; //i.e two weeks
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);            
					calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
					Date deliveryDate = calendar.getTime();
					pst.setString(5, sdf.format(deliveryDate));
					double shippingPrice = 5.60;
					double warranty = 24.99;
					pst.setDouble(6, shippingPrice);
					
					double totPrice = warranty + shippingPrice + (Quantity * laptop.getPrice());
					pst.setDouble(7, totPrice);
					pst.setString(8, "OneYearWarranty");
					
					String[] cities = {"New York","Washington D.C","San Francisco","Chicago","Boston","Log Angeles","Seattle","San Diego","Austin","Philadelphia","New Orleans","Portland","Nashville","Houston","Denver","Miami","Detroit","Dallas","Las Vegas","Phoenix","San Jose","Minneapolis","Atlanta","Indianapolis","Orlando","Pittsburgh","Madison"};
			        List<String> names = Arrays.asList(cities);
			        int index = new Random().nextInt(names.size());
			        String cityName = names.get(index);
			        
			        String[] states = {"California","Texas","Georgia","Tennessee","Ohio","Indiana","Arizona","Virginia","Florida","Alabama","Missouri","Massachusetts","Pennsylvania","Wisconsin","Arkansas","Alaska","Utah","Illinois","Minnesota","New Jersey","South Carolina","West Virginia","Hawaii","Michigan"};
			        List<String> stateNames = Arrays.asList(states);
			        int stateIndex = new Random().nextInt(stateNames.size());
			        String stateName = stateNames.get(stateIndex);
			        
					pst.setString(9, "USA");
					pst.setString(10, stateName);
					pst.setString(11, "Address1");
					pst.setString(12, "Address2");
					pst.setString(13, cityName);
					int minZip = 60601;
					int maxZip = 60616;
					int randomZip = rand.nextInt((maxZip - minZip) + 1) + minZip;
					pst.setInt(14, randomZip);
					int  mobileNumber = rand.nextInt(999999999) + 99999999;
					pst.setString(15, String.valueOf(mobileNumber));
					pst.setString(16, "user1@gmail.com");
					pst.execute();
				}
			}
			
			ExternalStorage storage = new ExternalStorage();
			for(Map.Entry<String, ExternalStorage> w : getStorageMapDB().entrySet()) {	
				storage = w.getValue(); 
				
				Random rand = new Random();
				int  confirmationNumber = rand.nextInt(999999999) + 99999999;
				
				int min = 1;
				int max = 4;
				int Quantity = rand.nextInt((max - min) + 1) + min;
				
				int inventoryQnty = 0;				
				query = "select qnty from inventory where productID=?;";
				pst = con.prepareStatement(query);					
				pst.setString(1, storage.getId());
				ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					inventoryQnty = rs.getInt(1);
				}
				
				if(inventoryQnty>=Quantity) {
					query = "UPDATE inventory SET qnty=? WHERE productID=?;";
					pst = con.prepareStatement(query);
					pst.setInt(1, inventoryQnty-Quantity);
					pst.setString(2,storage.getId());
					pst.execute();
					
					query = "INSERT INTO orderProductList(confirmationNumber,productID,productType,qnty) VALUES(?,?,?,?);";
					pst = con.prepareStatement(query);				
					pst.setInt(1, confirmationNumber);
					pst.setString(2,storage.getId());
					pst.setString(3, "externalstorage");				
					pst.setInt(4, Quantity);
					pst.execute();
					
					query = "INSERT INTO orders(customerName,username,orderDate,confirmationNumber,deliveryDate,shippingPrice,totalPrice,productWarranty,country,state,address1,address2,city,zipCode,mobileNumber,emailAddress) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
					pst = con.prepareStatement(query);
					pst.setString(1,"user1");
					pst.setString(2, "user1");
					DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					Calendar orderDateCalender = Calendar.getInstance();
					orderDateCalender.setTime(date);
					orderDateCalender.add(Calendar.DAY_OF_MONTH, -1);
					Date orderDate = orderDateCalender.getTime();
					SimpleDateFormat sdfOrderDate = new SimpleDateFormat("yyyy/MM/dd");
					pst.setString(3, sdfOrderDate.format(orderDate));
					pst.setInt(4, confirmationNumber);
					int noOfDays = 14; //i.e two weeks
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);            
					calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
					Date deliveryDate = calendar.getTime();
					pst.setString(5, sdf.format(deliveryDate));
					double shippingPrice = 5.60;
					double warranty = 24.99;
					pst.setDouble(6, shippingPrice);
					
					double totPrice = warranty + shippingPrice + (Quantity * storage.getPrice());
					pst.setDouble(7, totPrice);
					pst.setString(8, "OneYearWarranty");
					
					String[] cities = {"New York","Washington D.C","San Francisco","Chicago","Boston","Log Angeles","Seattle","San Diego","Austin","Philadelphia","New Orleans","Portland","Nashville","Houston","Denver","Miami","Detroit","Dallas","Las Vegas","Phoenix","San Jose","Minneapolis","Atlanta","Indianapolis","Orlando","Pittsburgh","Madison"};
			        List<String> names = Arrays.asList(cities);
			        int index = new Random().nextInt(names.size());
			        String cityName = names.get(index);
			        
			        String[] states = {"California","Texas","Georgia","Tennessee","Ohio","Indiana","Arizona","Virginia","Florida","Alabama","Missouri","Massachusetts","Pennsylvania","Wisconsin","Arkansas","Alaska","Utah","Illinois","Minnesota","New Jersey","South Carolina","West Virginia","Hawaii","Michigan"};
			        List<String> stateNames = Arrays.asList(states);
			        int stateIndex = new Random().nextInt(stateNames.size());
			        String stateName = stateNames.get(stateIndex);
			        
					pst.setString(9, "USA");
					pst.setString(10, stateName);
					pst.setString(11, "Address1");
					pst.setString(12, "Address2");
					pst.setString(13, cityName);
					int minZip = 60601;
					int maxZip = 60616;
					int randomZip = rand.nextInt((maxZip - minZip) + 1) + minZip;
					pst.setInt(14, randomZip);
					int  mobileNumber = rand.nextInt(999999999) + 99999999;
					pst.setString(15, String.valueOf(mobileNumber));
					pst.setString(16, "user1@gmail.com");
					pst.execute();
				}
			}
			
			Accessories accessory = new Accessories();
			for(Map.Entry<String, Accessories> w : getAccessoryMapDB().entrySet()) {	
				accessory = w.getValue(); 
				
				Random rand = new Random();
				int  confirmationNumber = rand.nextInt(999999999) + 99999999;
				
				int min = 1;
				int max = 4;
				int Quantity = rand.nextInt((max - min) + 1) + min;
				
				int inventoryQnty = 0;				
				query = "select qnty from inventory where productID=?;";
				pst = con.prepareStatement(query);					
				pst.setString(1, accessory.getId());
				ResultSet rs = pst.executeQuery();
				if(rs.next()) {
					inventoryQnty = rs.getInt(1);
				}
				
				if(inventoryQnty>=Quantity) {
					query = "UPDATE inventory SET qnty=? WHERE productID=?;";
					pst = con.prepareStatement(query);
					pst.setInt(1, inventoryQnty-Quantity);
					pst.setString(2,accessory.getId());
					pst.execute();
					
					query = "INSERT INTO orderProductList(confirmationNumber,productID,productType,qnty) VALUES(?,?,?,?);";
					pst = con.prepareStatement(query);				
					pst.setInt(1, confirmationNumber);
					pst.setString(2,accessory.getId());
					pst.setString(3, "accessories");				
					pst.setInt(4, Quantity);
					pst.execute();
					
					query = "INSERT INTO orders(customerName,username,orderDate,confirmationNumber,deliveryDate,shippingPrice,totalPrice,productWarranty,country,state,address1,address2,city,zipCode,mobileNumber,emailAddress) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
					pst = con.prepareStatement(query);
					pst.setString(1,"user1");
					pst.setString(2, "user1");
					DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					SimpleDateFormat sdfOrderDate = new SimpleDateFormat("yyyy/MM/dd");
					pst.setString(3, sdfOrderDate.format(date));					
					pst.setInt(4, confirmationNumber);
					int noOfDays = 14; //i.e two weeks
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);            
					calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
					Date deliveryDate = calendar.getTime();
					pst.setString(5, sdf.format(deliveryDate));
					double shippingPrice = 5.60;
					double warranty = 24.99;
					pst.setDouble(6, shippingPrice);
					
					double totPrice = warranty + shippingPrice + (Quantity * accessory.getPrice());
					pst.setDouble(7, totPrice);
					pst.setString(8, "OneYearWarranty");
					
					String[] cities = {"New York","Washington D.C","San Francisco","Chicago","Boston","Log Angeles","Seattle","San Diego","Austin","Philadelphia","New Orleans","Portland","Nashville","Houston","Denver","Miami","Detroit","Dallas","Las Vegas","Phoenix","San Jose","Minneapolis","Atlanta","Indianapolis","Orlando","Pittsburgh","Madison"};
			        List<String> names = Arrays.asList(cities);
			        int index = new Random().nextInt(names.size());
			        String cityName = names.get(index);
			        
			        String[] states = {"California","Texas","Georgia","Tennessee","Ohio","Indiana","Arizona","Virginia","Florida","Alabama","Missouri","Massachusetts","Pennsylvania","Wisconsin","Arkansas","Alaska","Utah","Illinois","Minnesota","New Jersey","South Carolina","West Virginia","Hawaii","Michigan"};
			        List<String> stateNames = Arrays.asList(states);
			        int stateIndex = new Random().nextInt(stateNames.size());
			        String stateName = stateNames.get(stateIndex);
			        
					pst.setString(9, "USA");
					pst.setString(10, stateName);
					pst.setString(11, "Address1");
					pst.setString(12, "Address2");
					pst.setString(13, cityName);
					int minZip = 60601;
					int maxZip = 60616;
					int randomZip = rand.nextInt((maxZip - minZip) + 1) + minZip;
					pst.setInt(14, randomZip);
					int  mobileNumber = rand.nextInt(999999999) + 99999999;
					pst.setString(15, String.valueOf(mobileNumber));
					pst.setString(16, "user1@gmail.com");
					pst.execute();
				}
			}
			con.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());			
		}
	}
	
	public boolean deleteOrders(String confirmationNumber) {
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			
			int orderQnty = 0;
			String prodID = null;
			int inventoryQnty = 0;
			int i=0;
			
			String query = "select productID,qnty from orderProductList where confirmationNumber=?;";			
			PreparedStatement pst = con.prepareStatement(query);					
			pst.setInt(1, Integer.parseInt(confirmationNumber));
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				inventoryQnty = 0;
				orderQnty = 0;
				prodID = null;
				
				prodID = rs.getString(1);
				orderQnty = rs.getInt(2);
				pst = con.prepareStatement("select qnty from inventory where productID=?;");				
				pst.setString(1, prodID);
				ResultSet rs1 = pst.executeQuery();
				if(rs1.next()) {
					inventoryQnty = rs1.getInt(1);
				}
				inventoryQnty = inventoryQnty + orderQnty;
				
				pst = con.prepareStatement("UPDATE inventory SET qnty=? WHERE productID=?;");
				pst.setInt(1, inventoryQnty);
				pst.setString(2, prodID);
				pst.execute();
			}
			
			query = "DELETE FROM orders WHERE confirmationNumber=?;";
			pst = con.prepareStatement(query);
			pst.setInt(1,Integer.parseInt(confirmationNumber));
			boolean result = pst.execute();
			
			query = "DELETE FROM orderProductList WHERE confirmationNumber=?;";
			pst = con.prepareStatement(query);			
			pst.setInt(1, Integer.parseInt(confirmationNumber));
			result = pst.execute();
			con.close();
			return result;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return false;
		}
	}
	
	public HashMap<String, Orders> getOrders(){
		try {
			Orders order = new Orders();
			int confirmationNumber = 0;
			HashMap<String, Orders> ordersHashMap = new HashMap<String, Orders>();
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			String query = "select * from orders;";
			PreparedStatement pst = con.prepareStatement(query);	
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				confirmationNumber  = rs.getInt("confirmationNumber");
				/*
				String query = "select COUNT(*) from orderProductList where confirmationNumber=?;";
				PreparedStatement pst = con.prepareStatement(query);
				pst.setInt(1, confirmationNumber);
				ResultSet rs1 = pst.executeQuery();
				int rowCount = rs1.getInt(1);*/
				String[] prodType = new String[100];
				String[] prodID = new String[100];
				int[] qnty = new int[100];
				int i=0;
				
				query = "select * from orderProductList where confirmationNumber=?;";
				pst = con.prepareStatement(query);			
				pst.setInt(1, confirmationNumber);
				ResultSet rs2 = pst.executeQuery();
				
				while(rs2.next()) {
					prodType[i] = rs2.getString("productType");
					prodID[i] = rs2.getString("productID");
					qnty[i] = rs2.getInt("qnty");
					i++;
				}
				order = new Orders(rs.getString("username"),rs.getString("customerName"),prodType,prodID,qnty,rs.getString("orderDate"),confirmationNumber,rs.getString("deliveryDate"),rs.getDouble("shippingPrice"),rs.getDouble("totalPrice"),rs.getString("productWarranty"),rs.getString("country"),rs.getString("state"),rs.getString("address1"),rs.getString("address2"),rs.getString("city"),rs.getInt("zipCode"),rs.getString("mobileNumber"),rs.getString("emailAddress"));
				ordersHashMap.put(String.valueOf(confirmationNumber), order);
			}
			con.close();
			return ordersHashMap;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
	
	public Orders getOrder(String confirmationNumber){
		try {
			Orders order = null;
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			String query = "select * from orders where confirmationNumber=?;";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, Integer.parseInt(confirmationNumber));
			ResultSet rs = pst.executeQuery();
			if(rs.next())
			{
				/*
				query = "select COUNT(*) from orderProductList where confirmationNumber=?;";
				pst = con.prepareStatement(query);
				pst.setInt(1, Integer.parseInt(confirmationNumber));
				ResultSet rs1 = pst.executeQuery();
				int rowCount = rs1.getInt(1);*/
				String[] prodType = new String[100];
				String[] prodID = new String[100];
				int[] qnty = new int[100];
				int i=0;
				query = "select * from orderProductList where confirmationNumber=?;";
				pst = con.prepareStatement(query);			
				pst.setInt(1, Integer.parseInt(confirmationNumber));
				ResultSet rs2 = pst.executeQuery();
				while(rs2.next()) {
					prodType[i] = rs2.getString("productType");
					prodID[i] = rs2.getString("productID");
					qnty[i] = rs2.getInt("qnty");
					i++;
				}
				order = new Orders(rs.getString("username"),rs.getString("customerName"),prodType,prodID,qnty,rs.getString("orderDate"),rs.getInt("confirmationNumber"),rs.getString("deliveryDate"),rs.getDouble("shippingPrice"),rs.getDouble("totalPrice"),rs.getString("productWarranty"),rs.getString("country"),rs.getString("state"),rs.getString("address1"),rs.getString("address2"),rs.getString("city"),rs.getInt("zipCode"),rs.getString("mobileNumber"),rs.getString("emailAddress"));
			}
			con.close();
			return order;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
	
	public String[][] getTotalProductSales(){
		try {
			//HashMap<String, Integer> TotalSalesMap = new HashMap<String, Integer>();
			String[][] totalSalesArray = new String[50][2];
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			String query = "select state,sum(qnty) as TotalProductSales from orders,ProdCount where orders.confirmationNumber=ProdCount.confirmationNumber group by state;";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			int rowCount=0;
			while(rs.next())
			{
				totalSalesArray[rowCount][0] = rs.getString("state");
				totalSalesArray[rowCount][1] = String.valueOf(rs.getInt("TotalProductSales"));
				System.out.println("DBState-" + totalSalesArray[rowCount][0] + "\tDBSales-" + Integer.parseInt(totalSalesArray[rowCount][1]));
				rowCount++;
				/*
				TotalSalesMap.put(rs.getString("state"), rs.getInt("TotalProductSales"));
				System.out.println("State -" + rs.getString("state") + "\tTotalProductSales-" + String.valueOf(rs.getInt("TotalProductSales")));
				System.out.println("Map total Sales -" + TotalSalesMap.get(rs.getString("state")));*/
			}
			con.close();
			return totalSalesArray;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
	
	public String[][] getAverageProductSoldPrices(){
		try {
			String[][] averageSalesArray = new String[50][2];
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			String query = "select state,avg(totalPrice) as AverageProductPrices from orders group by state;";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			int rowCount=0;
			while(rs.next())
			{
				averageSalesArray[rowCount][0] = rs.getString("state");
				averageSalesArray[rowCount][1] = String.valueOf(rs.getDouble("AverageProductPrices"));
				rowCount++;
			}
			con.close();
			return averageSalesArray;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
	
	public String[][] getTotalProductSoldPrices(){
		try {
			String[][] totalSalesArray = new String[50][2];
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con = DriverManager.getConnection(conString,dbUser,dbPwd); 
			String query = "select state,sum(totalPrice) as TotalProductPrices from orders group by state;";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			int rowCount=0;
			while(rs.next())
			{
				totalSalesArray[rowCount][0] = rs.getString("state");
				totalSalesArray[rowCount][1] = String.valueOf(rs.getDouble("TotalProductPrices"));				
				rowCount++;
			}
			con.close();
			return totalSalesArray;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("Message - " + ex.getMessage());
			return null;
		}
	}
}