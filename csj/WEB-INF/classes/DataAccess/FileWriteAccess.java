package DataAccess;

import java.io.*;
import java.util.*;
import JavaBeans.Users;
import JavaBeans.Orders;
import JavaBeans.ShoppingCart;

public class FileWriteAccess {
	String fileName;
	public static HashMap<String, Users> usersHashMap;
	public static HashMap<String, Orders> ordersHashMap;
	
	public FileWriteAccess(String fileName) {
		this.fileName = fileName;
        
		usersHashMap = new HashMap<String, Users>();
		ordersHashMap = new HashMap<String, Orders>();
	}
	
	@SuppressWarnings({"unchecked"})
	public boolean writeUserProfile(Users profile) {
		try {
			File fN = new File(fileName);
			if(fN.exists()) {	
				FileInputStream fis = new FileInputStream(fN);
				ObjectInputStream ois =new ObjectInputStream(fis);
				try {
					usersHashMap = (HashMap<String, Users>)ois.readObject();
				}
				catch(EOFException ex) {
					System.out.println("Reached End of File");
				}
				ois.close();
				fis.close();
			}
			usersHashMap.put(profile.getUsername(), profile);
			
			FileOutputStream fos = new FileOutputStream(fN);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(usersHashMap);
			oos.flush();
			oos.close();
			fos.close();
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings({"unchecked"})
	public boolean writeOrders(Orders order) {
		try {
			File fN = new File(fileName);
			if(fN.exists()) {
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
			}
			String confirmationNumber = String.valueOf(order.getConfirmationNumber());
			ordersHashMap.put(confirmationNumber, order);
			
			FileOutputStream fos = new FileOutputStream(fN);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(ordersHashMap);
			oos.flush();
			oos.close();
			fos.close();
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	@SuppressWarnings({"unchecked"})
	public boolean deleteOrders(String confirmationNumber) {
		try {
			File fN = new File(fileName);
			if(fN.exists()) {
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
			}
			ordersHashMap.remove(confirmationNumber);
			
			FileOutputStream fos = new FileOutputStream(fN);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(ordersHashMap);
			oos.flush();
			oos.close();
			fos.close();
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	} 
}