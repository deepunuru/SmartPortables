package Controller;

import java.io.*;
import java.util.*;
import java.text.ParseException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.Serializable;

import JavaBeans.*;

public class SaxParserDataStore extends DefaultHandler {
	SmartWatch watch;
	Speakers speakers;
	Headphones headphones;
	Phones phones;
	Laptops laptops;
	ExternalStorage storage;
	Accessories accessories;
	Accessories accessoryTmp;	
	Inventory inventory;
	int i=1;
	
	public HashMap<String, SmartWatch> watchMap;
	public HashMap<String, Speakers> speakersMap;
	public HashMap<String, Headphones> headphonesMap;
	public HashMap<String, Phones> phonesMap;
	public HashMap<String, Laptops> laptopsMap;
	public HashMap<String, ExternalStorage> externalStorageMap;
	public HashMap<String, Accessories> accessoryMap;
	public HashMap<String, Accessories> tmpMap;
	public HashMap<String, Inventory> inventoryMap;
	
	String currentElement="";
	String elementValueRead;
	
	
	public SaxParserDataStore() {
		
		watchMap = new HashMap<String, SmartWatch>();
		speakersMap = new HashMap<String, Speakers>();
		headphonesMap = new HashMap<String, Headphones>();
		phonesMap = new HashMap<String, Phones>();
		laptopsMap = new HashMap<String, Laptops>();
		externalStorageMap = new HashMap<String, ExternalStorage>();
		accessoryMap = new HashMap<String, Accessories>();
		tmpMap = new HashMap<String, Accessories>();
		accessoryTmp = new Accessories();
		inventoryMap = new HashMap<String, Inventory>();
	}
	
	public HashMap<String, SmartWatch> getWatchMap(){
		return watchMap;
	}
	
	@Override
	public void startElement(String str1, String str2, String elementName,Attributes attributes) throws SAXException {
		if(elementName.equalsIgnoreCase("smartwatch")) {
			currentElement = "smartwatch";
			watch = new SmartWatch();
			watch.setId(attributes.getValue("id"));
			watch.setRetailer(attributes.getValue("retailer"));
		}
		if(elementName.equalsIgnoreCase("speakers")) {
			currentElement = "speakers";
			speakers = new Speakers();
			speakers.setId(attributes.getValue("id"));
			speakers.setRetailer(attributes.getValue("retailer"));
		}
		if(elementName.equalsIgnoreCase("headphones")) {
			currentElement = "headphones";
			headphones = new Headphones();
			headphones.setId(attributes.getValue("id"));
			headphones.setRetailer(attributes.getValue("retailer"));
		}
		if(elementName.equalsIgnoreCase("phone")) {
			currentElement = "phone";
			phones = new Phones();
			phones.setId(attributes.getValue("id"));
			phones.setRetailer(attributes.getValue("retailer"));
		}
		if(elementName.equalsIgnoreCase("laptop")) {
			currentElement = "laptop";
			laptops = new Laptops();
			laptops.setId(attributes.getValue("id"));
			laptops.setRetailer(attributes.getValue("retailer"));
		}
		if(elementName.equalsIgnoreCase("storage")) {
			currentElement = "storage";
			storage = new ExternalStorage();
			storage.setId(attributes.getValue("id"));
			storage.setRetailer(attributes.getValue("retailer"));
		}
		if(elementName.equalsIgnoreCase("accessorymain")) {
			currentElement = "accessorymain";
			accessories = new Accessories();
			
			accessories.setId(attributes.getValue("id"));
			accessories.setType(attributes.getValue("type"));
			accessories.setRetailer(attributes.getValue("retailer"));
		}
		if(elementName.equalsIgnoreCase("smartwatch") || elementName.equalsIgnoreCase("speakers") || elementName.equalsIgnoreCase("headphones") || elementName.equalsIgnoreCase("phone") || elementName.equalsIgnoreCase("laptop") || elementName.equalsIgnoreCase("storage") || elementName.equalsIgnoreCase("accessorymain")) {
			inventory = new Inventory();
			inventory.setProductID(attributes.getValue("id"));			
		}
	}
	
	@Override
	public void endElement(String str1,String str2,String element) throws SAXException {
		if(element.equals("smartwatch")) {
			watchMap.put(watch.getId(),watch);
			inventoryMap.put(inventory.getProductID(), inventory);
			return;
		}
		if(element.equals("speakers")) {
			speakersMap.put(speakers.getId(),speakers);
			inventoryMap.put(inventory.getProductID(), inventory);
			return;
		}
		if(element.equals("headphones")) {
			headphonesMap.put(headphones.getId(),headphones);
			inventoryMap.put(inventory.getProductID(), inventory);
			return;
		}
		if(element.equals("phone")) {
			phonesMap.put(phones.getId(),phones);
			inventoryMap.put(inventory.getProductID(), inventory);
			return;
		}
		if(element.equals("laptop")) {
			laptopsMap.put(laptops.getId(),laptops);	
			inventoryMap.put(inventory.getProductID(), inventory);
			return;
		}
		if(element.equals("storage")) {
			externalStorageMap.put(storage.getId(),storage);
			inventoryMap.put(inventory.getProductID(), inventory);
			return;
		}
		if(element.equals("accessorymain")) {
			accessoryMap.put(accessories.getId(),accessories);
			inventoryMap.put(inventory.getProductID(), inventory);
			return;
		}
		if(element.equalsIgnoreCase("accessory")) {
			if(currentElement.equals("smartwatch") || currentElement.equals("speakers") || currentElement.equals("headphones") || currentElement.equals("phone") || currentElement.equals("laptop") || currentElement.equals("storage")) {
				accessoryTmp.setId(elementValueRead);
				tmpMap.put("accessory-" + i, accessoryTmp);
				accessoryTmp = new Accessories();
				i++;
				return;
			}
		}
		if(element.equalsIgnoreCase("accessories")) {
			if(currentElement.equals("smartwatch")) {
				watch.setAccessories(tmpMap);
				tmpMap = new HashMap<String, Accessories>();
			}
			if(currentElement.equals("speakers")) {
				speakers.setAccessories(tmpMap);
				tmpMap = new HashMap<String, Accessories>();
			}
			if(currentElement.equals("headphones")) {
				headphones.setAccessories(tmpMap);
				tmpMap = new HashMap<String, Accessories>();
			}
			if(currentElement.equals("phone")) {
				phones.setAccessories(tmpMap);
				tmpMap = new HashMap<String, Accessories>();
			}
			if(currentElement.equals("laptop")) {
				laptops.setAccessories(tmpMap);
				tmpMap = new HashMap<String, Accessories>();
			}
			if(currentElement.equals("storage")) {
				storage.setAccessories(tmpMap);
				tmpMap = new HashMap<String, Accessories>();
			}
		}
		if(element.equalsIgnoreCase("name")) {
			if(currentElement.equals("smartwatch")) {
				watch.setName(elementValueRead);
			}
			if(currentElement.equals("speakers")) {
				speakers.setName(elementValueRead);
			}
			if(currentElement.equals("headphones")) {
				headphones.setName(elementValueRead);
			}
			if(currentElement.equals("phone")) {
				phones.setName(elementValueRead);
			}
			if(currentElement.equals("laptop")) {
				laptops.setName(elementValueRead);
			}
			if(currentElement.equals("storage")) {
				storage.setName(elementValueRead);
			}
			if(currentElement.equals("accessorymain")) {
				accessories.setName(elementValueRead);
			}
			inventory.setProductName(elementValueRead);
			return;
		}
		if(element.equalsIgnoreCase("image")) {
			if(currentElement.equals("smartwatch")) {
				watch.setImage(elementValueRead);
			}
			if(currentElement.equals("speakers")) {
				speakers.setImage(elementValueRead);
			}
			if(currentElement.equals("headphones")) {
				headphones.setImage(elementValueRead);
			}
			if(currentElement.equals("phone")) {
				phones.setImage(elementValueRead);
			}
			if(currentElement.equals("laptop")) {
				laptops.setImage(elementValueRead);
			}
			if(currentElement.equals("storage")) {
				storage.setImage(elementValueRead);
			}
			if(currentElement.equals("accessorymain")) {
				accessories.setImage(elementValueRead);
			}
			return;
		}
		if(element.equalsIgnoreCase("condition")) {
			if(currentElement.equals("smartwatch")) {
				watch.setCondition(elementValueRead);
			}
			if(currentElement.equals("speakers")) {
				speakers.setCondition(elementValueRead);
			}
			if(currentElement.equals("headphones")) {
				headphones.setCondition(elementValueRead);
			}
			if(currentElement.equals("phone")) {
				phones.setCondition(elementValueRead);
			}
			if(currentElement.equals("laptop")) {
				laptops.setCondition(elementValueRead);
			}
			if(currentElement.equals("storage")) {
				storage.setCondition(elementValueRead);
			}
			if(currentElement.equals("accessorymain")) {
				accessories.setCondition(elementValueRead);
			}
			return;
		}
		if(element.equalsIgnoreCase("price")) {
			if(currentElement.equals("smartwatch")) {
				watch.setPrice(Double.parseDouble(elementValueRead));
			}
			if(currentElement.equals("speakers")) {
				speakers.setPrice(Double.parseDouble(elementValueRead));
			}
			if(currentElement.equals("headphones")) {
				headphones.setPrice(Double.parseDouble(elementValueRead));
			}
			if(currentElement.equals("phone")) {
				phones.setPrice(Double.parseDouble(elementValueRead));
			}
			if(currentElement.equals("laptop")) {
				laptops.setPrice(Double.parseDouble(elementValueRead));
			}
			if(currentElement.equals("storage")) {
				storage.setPrice(Double.parseDouble(elementValueRead));
			}
			if(currentElement.equals("accessorymain")) {
				accessories.setPrice(Double.parseDouble(elementValueRead));
			}
			inventory.setPrice(Double.parseDouble(elementValueRead));
			return;
		}
		if(element.equalsIgnoreCase("manufacturer")) {
			if(currentElement.equals("smartwatch")) {
				watch.setManufacturer(elementValueRead);
			}
			if(currentElement.equals("speakers")) {
				speakers.setManufacturer(elementValueRead);
			}
			if(currentElement.equals("headphones")) {
				headphones.setManufacturer(elementValueRead);
			}
			if(currentElement.equals("phone")) {
				phones.setManufacturer(elementValueRead);
			}
			if(currentElement.equals("laptop")) {
				laptops.setManufacturer(elementValueRead);
			}
			if(currentElement.equals("storage")) {
				storage.setManufacturer(elementValueRead);
			}
			if(currentElement.equals("accessorymain")) {
				accessories.setManufacturer(elementValueRead);
			}
			return;
		}
		if(element.equalsIgnoreCase("discount")) {
			if(currentElement.equals("smartwatch")) {
				watch.setDiscount(Double.parseDouble(elementValueRead));
			}
			if(currentElement.equals("speakers")) {
				speakers.setDiscount(Double.parseDouble(elementValueRead));
			}
			if(currentElement.equals("headphones")) {
				headphones.setDiscount(Double.parseDouble(elementValueRead));
			}
			if(currentElement.equals("phone")) {
				phones.setDiscount(Double.parseDouble(elementValueRead));
			}
			if(currentElement.equals("laptop")) {
				laptops.setDiscount(Double.parseDouble(elementValueRead));
			}
			if(currentElement.equals("storage")) {
				storage.setDiscount(Double.parseDouble(elementValueRead));
			}
			if(currentElement.equals("accessorymain")) {
				accessories.setDiscount(Double.parseDouble(elementValueRead));
			}
			return;
		}
		if(element.equalsIgnoreCase("qnty")) {
			inventory.setQnty(Integer.parseInt(elementValueRead));
		}
		if(element.equalsIgnoreCase("productOnSale")) {
			if(elementValueRead.equalsIgnoreCase("Yes")) {
				inventory.setProductOnSale(true);
			}
			else {
				inventory.setProductOnSale(false);
			}
		}
		if(element.equalsIgnoreCase("rebate")) {
			if(elementValueRead.equalsIgnoreCase("Yes")) {
				inventory.setRebate(true);
			}
			else {
				inventory.setRebate(false);
			}
		}
	}
	
	@Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }
}