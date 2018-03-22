package DataAccess;

import java.io.*;
import java.sql.DriverManager;
import java.util.*;
import JavaBeans.Users;
import JavaBeans.Orders;
import JavaBeans.ShoppingCart;

public class FileReadAccess{
	String fileName;
	public static HashMap<String, Users> usersHashMap;
	public static HashMap<String, Orders> ordersHashMap;
	public static HashMap<String, ShoppingCart> cartHashMap;

	public FileReadAccess(String fileName) {
		this.fileName = fileName;
		
		usersHashMap = new HashMap<String, Users>();
		ordersHashMap = new HashMap<String, Orders>();
		cartHashMap = new HashMap<String, ShoppingCart>();
	}
	
	@SuppressWarnings({"unchecked"})
	public Users getUserProfile(String username) {
		try {
			File fN = new File(fileName);
			if(fN.exists()==true) {
				FileInputStream fis = new FileInputStream(fN);
				ObjectInputStream ois =new ObjectInputStream(fis);
				try
				{
					usersHashMap = (HashMap<String, Users>)ois.readObject();
				}
				catch(EOFException ex) {
					System.out.println("Reached End of File");
				}
				ois.close();
				fis.close();
				
				Users user = new Users();
				for(Map.Entry<String, Users> profile : usersHashMap.entrySet()) {
					if(profile.getKey().equalsIgnoreCase(username)) {
						user = profile.getValue();
						break;
					}
					else
						user = null;
				}
				return user;
			}
			else
				return null;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings({"unchecked"})
	public HashMap<String, Orders> getOrders(){
		try {
			File fN = new File(fileName);
			if(fN.exists()==true) {
				FileInputStream fis = new FileInputStream(fN);
				ObjectInputStream ois =new ObjectInputStream(fis);
				try {
					ordersHashMap = (HashMap<String, Orders>)ois.readObject();
				}
				catch(EOFException ex) {
					System.out.println("Reached End of File");
				}
				ois.close();
				fis.close();
				
				return ordersHashMap;
			}
			else
				return null;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings({"unchecked"})
	public Orders getOrder(String confirmationNumber){
		try {
			File fN = new File(fileName);
			if(fN.exists()==true) {
				FileInputStream fis = new FileInputStream(fN);
				ObjectInputStream ois =new ObjectInputStream(fis);
				try {
					ordersHashMap = (HashMap<String, Orders>)ois.readObject();
				}
				catch(EOFException ex) {
					System.out.println("Reached End of File");
				}
				ois.close();
				fis.close();
				
				Orders order = new Orders();
				for(Map.Entry<String, Orders> o : ordersHashMap.entrySet()) {
					if(o.getKey().equals(confirmationNumber)) {
						order = o.getValue();
						break;
					}
					else
						order = null;
				}
				return order;
			}
			else
				return null;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings({"unchecked"})
	public Orders getUserOrder(String username){
		try {
			File fN = new File(fileName);
			if(fN.exists()==true) {
				FileInputStream fis = new FileInputStream(fN);
				ObjectInputStream ois =new ObjectInputStream(fis);
				try {
					ordersHashMap = (HashMap<String, Orders>)ois.readObject();
				}
				catch(EOFException ex) {
					System.out.println("Reached End of File");
				}
				ois.close();
				fis.close();
				
				Orders order = new Orders();
				for(Map.Entry<String, Orders> o : ordersHashMap.entrySet()) {
					order = o.getValue();
					if(username.equals(order.getUsername())) {
						break;
					}
					else
						order = null;
				}
				return order;
			}
			else
				return null;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings({"unchecked"})
	public ShoppingCart getUserCart(String username){
		try {
			File fN = new File(fileName);
			if(fN.exists()==true) {
				FileInputStream fis = new FileInputStream(fN);
				ObjectInputStream ois =new ObjectInputStream(fis);
				try {
					cartHashMap = (HashMap<String, ShoppingCart>)ois.readObject();
				}
				catch(EOFException ex) {
					System.out.println("Reached End of File");
				}
				ois.close();
				fis.close();
				
				ShoppingCart cart = new ShoppingCart();
				for(Map.Entry<String, ShoppingCart> o : cartHashMap.entrySet()) {
					cart = o.getValue();
					if(username.equals(cart.getUsername())) {
						break;
					}
					else
						cart = null;
				}
				return cart;
			}
			else
				return null;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}