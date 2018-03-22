package Controller;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMParser{
	public DOMParser(){
		
	}
	
	public void addProduct(String absoluteDiskPath,String productType,String id,String retailer,String name,String image,String condition,String price,String manufacturer,String discount,String[] accessoryList) {
		try {
			System.out.println("Initializing DOMParser");
			System.out.println("File path - " + absoluteDiskPath);
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(absoluteDiskPath);
			
			String tagType = new String();
			switch(productType) {
				case "smartwatches": {
					tagType = "smartwatch";
					break;
				}
				case "speakers": {
					tagType = "speakers";
					break;
				}
				case "headphones": {
					tagType = "headphones";
					break;
				}
				case "smartphones":{
					tagType = "phone";
					break;
				}
				case "laptops": {
					tagType = "laptop";
					break;
				}
				case "externalstorage": {
					tagType = "storage";
					break;
				}
				case "accessories": {
					tagType = "accessorymain";
					break;
				}
				default:{
					break;
				}
			}
			Node modNode = doc.getElementsByTagName(tagType).item(0);
			Node newNode = modNode.cloneNode(true);
			
			NamedNodeMap Attr = newNode.getAttributes();
			
			Node idAttr = Attr.getNamedItem("id");
			idAttr.setTextContent(id);
			Node retailerAttr = Attr.getNamedItem("retailer");
			retailerAttr.setTextContent(retailer);
			
			NodeList list = newNode.getChildNodes();
			
			for(int i=0;i<list.getLength();i++) 
			{
				Node node = list.item(i);
				if("name".equals(node.getNodeName())) {
					node.setTextContent(name);
				}
				if("image".equals(node.getNodeName())) {
					node.setTextContent(image);
				}
				if("condition".equals(node.getNodeName())) {
					node.setTextContent(condition);
				}
				if("price".equals(node.getNodeName())) {
					node.setTextContent(price);
				}
				if("manufacturer".equals(node.getNodeName())) {
					node.setTextContent(manufacturer);
				}
				if("discount".equals(node.getNodeName())) {
					node.setTextContent(discount);
				}
				if("accessories".equals(node.getNodeName())) {
					newNode.removeChild(node);
					//add new accessories node
					Node acc = doc.createElement("accessories");
					
					Node accChild0 = doc.createElement("accessory");
					Node textAccChild0 = doc.createTextNode(accessoryList[0]);
					accChild0.appendChild(textAccChild0);
					acc.appendChild(accChild0);
					
					Node accChild1 = doc.createElement("accessory");
					Node textAccChild1 = doc.createTextNode(accessoryList[1]);
					accChild1.appendChild(textAccChild1);
					acc.appendChild(accChild1);
					
					Node accChild2 = doc.createElement("accessory");
					Node textAccChild2 = doc.createTextNode(accessoryList[2]);
					accChild2.appendChild(textAccChild2);
					acc.appendChild(accChild2);
					
					Node accChild3 = doc.createElement("accessory");
					Node textAccChild3 = doc.createTextNode(accessoryList[3]);
					accChild3.appendChild(textAccChild3);
					acc.appendChild(accChild3);
					
					Node accChild4 = doc.createElement("accessory");
					Node textAccChild4 = doc.createTextNode(accessoryList[4]);
					accChild4.appendChild(textAccChild4);
					acc.appendChild(accChild4);
					
					Node accChild5 = doc.createElement("accessory");
					Node textAccChild5 = doc.createTextNode(accessoryList[5]);
					accChild5.appendChild(textAccChild5);
					acc.appendChild(accChild5);
					
					Node accChild6 = doc.createElement("accessory");
					Node textAccChild6 = doc.createTextNode(accessoryList[6]);
					accChild6.appendChild(textAccChild6);
					acc.appendChild(accChild6);
					
					newNode.appendChild(acc);
				}
			}
			
			String CatalogType = new String();
			switch(productType) {
				case "smartwatches": {
					CatalogType = "SmartWatchCatalog";
					break;
				}
				case "speakers": {
					CatalogType = "SpeakersCatalog";
					break;
				}
				case "headphones": {
					CatalogType = "HeadphonesCatalog";
					break;
				}
				case "smartphones":{
					CatalogType = "PhonesCatalog";
					break;
				}
				case "laptops": {
					CatalogType = "LaptopsCatalog";
					break;
				}
				case "externalstorage": {
					CatalogType = "ExternalStorageCatalog";
					break;
				}
				case "accessories": {
					CatalogType = "AccessoriesCatalog";
					break;
				}
				default:{
					break;
				}
			}
			
			Node typeCatalog = doc.getElementsByTagName(CatalogType).item(0);
			typeCatalog.appendChild(newNode);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(absoluteDiskPath));
			transformer.transform(source, result);

			System.out.println("Node successfully added");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}	
	}
	
	public void ModifyXML(String absoluteDiskPath,String productType,String oldID,String newID,String retailer,String name,String image,String condition,String price,String manufacturer,String discount,String[] accessoryList) {
		try {
			System.out.println("Initializing DOMParser");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(absoluteDiskPath);
			
			String tagType = new String();
			String CatalogType = new String();
			switch(productType) {
				case "smartwatches": {
					tagType = "smartwatch";
					CatalogType = "SmartWatchCatalog";
					break;
				}
				case "speakers": {
					tagType = "speakers";
					CatalogType = "SpeakersCatalog";
					break;
				}
				case "headphones": {
					tagType = "headphones";
					CatalogType = "HeadphonesCatalog";
					break;
				}
				case "smartphones":{
					tagType = "phone";
					CatalogType = "PhonesCatalog";
					break;
				}
				case "laptops": {
					tagType = "laptop";
					CatalogType = "LaptopsCatalog";
					break;
				}
				case "externalstorage": {
					tagType = "storage";
					CatalogType = "ExternalStorageCatalog";
					break;
				}
				case "accessories": {
					tagType = "accessorymain";
					CatalogType = "AccessoriesCatalog";
					break;
				}
				default:{
					break;
				}
			}
			
			Node modNode = null;
			//Selecting correct node
			Node typeCatalog = doc.getElementsByTagName(CatalogType).item(0);
			System.out.println("Node Name - " + typeCatalog.getNodeName());
			NodeList list1 = typeCatalog.getChildNodes();
			
			System.out.println("List size-" + list1.getLength());
			for(int i=0;i<list1.getLength();i++) 
			{
				Node node = list1.item(i);
				//Node node = doc.getElementsByTagName(tagType).item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
			        System.out.println("node name: " + node.getNodeName());
					NamedNodeMap Attr = node.getAttributes();
					
					Node idAttr = Attr.getNamedItem("id");
					String idTemp = idAttr.getTextContent();
					System.out.println("Given ID - " + oldID + "\t" + "node id: " + idTemp);
					if(oldID.equals(idTemp)) {
						modNode = node;
						System.out.println("Matching ID - " + idTemp);
						break;
					}
			    }
			}
			
			
			//Node modNode = doc.getElementById(id);
			
			NamedNodeMap Attr = modNode.getAttributes();
			Node idAttr = Attr.getNamedItem("id");
			idAttr.setTextContent(newID);
			Node retailerAttr = Attr.getNamedItem("retailer");
			retailerAttr.setTextContent(retailer);
			
			NodeList list2 = modNode.getChildNodes();
			if(accessoryList == null) {
				for(int i=0;i<list2.getLength();i++) 
				{
					Node node = list2.item(i);
					if("name".equals(node.getNodeName())) {
						node.setTextContent(name);
					}
					if("image".equals(node.getNodeName())) {
						node.setTextContent(image);
					}
					if("condition".equals(node.getNodeName())) {
						node.setTextContent(condition);
					}
					if("price".equals(node.getNodeName())) {
						node.setTextContent(price);
					}
					if("manufacturer".equals(node.getNodeName())) {
						node.setTextContent(manufacturer);
					}
					if("discount".equals(node.getNodeName())) {
						node.setTextContent(discount);
					}
				}
			}
			else {
				for(int i=0;i<list2.getLength();i++) 
				{
					Node node = list2.item(i);
					if("name".equals(node.getNodeName())) {
						node.setTextContent(name);
					}
					if("image".equals(node.getNodeName())) {
						node.setTextContent(image);
					}
					if("condition".equals(node.getNodeName())) {
						node.setTextContent(condition);
					}
					if("price".equals(node.getNodeName())) {
						node.setTextContent(price);
					}
					if("manufacturer".equals(node.getNodeName())) {
						node.setTextContent(manufacturer);
					}
					if("discount".equals(node.getNodeName())) {
						node.setTextContent(discount);
					}
					if("accessories".equals(node.getNodeName())) {
						NodeList aList = node.getChildNodes();
						int len = aList.getLength();
						int k=0;
						for(int j=0;j<len;j++) {
							Node accessory = aList.item(j);
							if (accessory.getNodeType() == Node.ELEMENT_NODE) {
								if(accessoryList[k]!=null) {
									accessory.setTextContent(accessoryList[k]);
									k++;
								}
							}
						}
						/*if(len == acclen) {
							for(int j=0;j<len;j++) {
								Node accessory = aList.item(j);
								accessory.setTextContent(accessoryList[j]);
							}
						}
						if(len < acclen) {
							Node accessory;
							for(int k=0;k<len;k++) {
								accessory = aList.item(k);
								accessory.setTextContent(accessoryList[k]);
							}							
							for(int k=len;k<acclen;k++) {
								Element ac = doc.createElement("accessory");
								ac.appendChild(doc.createTextNode(accessoryList[k]));
								node.appendChild(ac);
							}
						}
						if(len > acclen) {
							int deleteNum = len - acclen;
							for(int j=0;j<len;j++) {
								if(j>=(acclen-1))
								{
									Node accessory = aList.item(j);	
									node.removeChild(accessory);
								}
								else {
									Node accessory = aList.item(j);
									accessory.setTextContent(accessoryList[j]);
								}
							}
						}*/
					}
				}
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(absoluteDiskPath));
			transformer.transform(source, result);
			System.out.println("File path is " + absoluteDiskPath);
			System.out.println("XML Successfully modified");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void deleteProduct(String absoluteDiskPath,String productType,String id) {
		try {
			System.out.println("Initializing DOMParser");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(absoluteDiskPath);
			
			String tagType = new String();
			String CatalogType = new String();
			switch(productType) {
				case "smartwatches": {
					tagType = "smartwatch";
					CatalogType = "SmartWatchCatalog";
					break;
				}
				case "speakers": {
					tagType = "speakers";
					CatalogType = "SpeakersCatalog";
					break;
				}
				case "headphones": {
					tagType = "headphones";
					CatalogType = "HeadphonesCatalog";
					break;
				}
				case "smartphones":{
					tagType = "phone";
					CatalogType = "PhonesCatalog";
					break;
				}
				case "laptops": {
					tagType = "laptop";
					CatalogType = "LaptopsCatalog";
					break;
				}
				case "externalstorage": {
					tagType = "storage";
					CatalogType = "ExternalStorageCatalog";
					break;
				}
				case "accessories": {
					tagType = "accessorymain";
					CatalogType = "AccessoriesCatalog";
					break;
				}
				default:{
					break;
				}
			}
			
			Node typeCatalog = doc.getElementsByTagName(CatalogType).item(0);
			System.out.println("Node Name - " + typeCatalog.getNodeName());
			NodeList list = typeCatalog.getChildNodes(); 
			
			for(int i=0;i<list.getLength();i++) 
			{
				//Node node = list.item(i);
				Node node = doc.getElementsByTagName(tagType).item(i);
				System.out.println("Node Name - " + node.getNodeName());
				NamedNodeMap Attr = node.getAttributes();
				
				Node idAttr = Attr.getNamedItem("id");
				String idTemp = idAttr.getTextContent();
				if(id.equals(idTemp)) {
					typeCatalog.removeChild(node);
					break;
				}
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(absoluteDiskPath));
			transformer.transform(source, result);

			System.out.println("Node successfully removed");	
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}