package Controller;

import java.io.*;
import java.util.*;
import java.text.*;
import java.time.*;
import javax.servlet.*;
import javax.servlet.http.*;
import Controller.HtmlEngine;
import JavaBeans.*;
import DataAccess.MySQLDataStoreUtilities;

public class DisplayProduct extends HttpServlet{
	private static final long serialVersionUID = 1L;
	MySQLDataStoreUtilities dbObj;
	
	public DisplayProduct() {
		dbObj = new MySQLDataStoreUtilities();
	}
	
	/*
	public void printCarousel(PrintWriter pWriter,HttpServletRequest request,String productID,String productType) {

		String lastID = productID;
		String lastType = productType;
		HashMap<String, String> dbStatus = new HashMap<String, String>();
		dbStatus = dbObj.checkDBConnection();
		if(dbStatus.get("status").equals("true")) {
			HashMap<String, Accessories> lastItemMap = new HashMap<String, Accessories>();
			switch(lastType) {
				case "smartwatches":{
					SmartWatch tmp = dbObj.getWatchMapDB().get(lastID);
					if(tmp!=null)
					{
						lastItemMap = tmp.getAccessories();
					}
					break;
				}
				case "speakers":{
					Speakers tmp = dbObj.getSpeakerMapDB().get(lastID);
					if(tmp!=null)
					{
						lastItemMap = tmp.getAccessories();
					}
					break;
				}
				case "headphones":{
					Headphones tmp = dbObj.getHeadphoneMapDB().get(lastID);
					if(tmp!=null)
					{
						lastItemMap = tmp.getAccessories();
					}
					break;
				}
				case "smartphones":{
					Phones tmp = dbObj.getPhoneMapDB().get(lastID);
					if(tmp!=null)
					{
						lastItemMap = tmp.getAccessories();
					}
					break;
				}
				case "laptops":{
					Laptops tmp = dbObj.getLaptopMapDB().get(lastID);
					if(tmp!=null)
					{
						lastItemMap = tmp.getAccessories();
					}
					break;
				}
				case "externalstorage":{
					ExternalStorage tmp = dbObj.getStorageMapDB().get(lastID); 
					if(tmp!=null)
					{
						lastItemMap = tmp.getAccessories();
					}
					break;
				}
				case "accessories":{
					//SmartWatch temp = new SmartWatch();
					//for(Map.Entry<String, SmartWatch> k : dbObj.watchMap.entrySet()) {					
					//	temp = k.getValue();
					//	break;
					//}
					lastItemMap = dbObj.getAccessoryMapDB();
					break;
				}
				default:{
					SmartWatch temp = new SmartWatch();
					for(Map.Entry<String, SmartWatch> k : dbObj.getWatchMapDB().entrySet()) {					
						temp = k.getValue();
						break;
					}
					lastItemMap = temp.getAccessories();
					break;
				}
			}
			
			
			String[] idList = new String[30];
			String[] priceList = new String[30];
			String[] nameList = new String[30];
			String[] imgList = new String[30];
			Accessories accessory= new Accessories();
			Accessories accessoryTmp= new Accessories();
			int n=0;
			for(Map.Entry<String, Accessories> w : lastItemMap.entrySet()) {
				accessoryTmp = w.getValue();
				
				idList[n] = accessoryTmp.getId();
				System.out.println(idList[n] + "\t" + n);
				n++;
			}
			
			for(int k=0;k<n;k++) {
				accessory = dbObj.getAccessoryMapDB().get(idList[k]);
				if(accessory!=null)
				{
					imgList[k] = accessory.getImage();
					priceList[k] = String.valueOf(accessory.getPrice());
					nameList[k] = accessory.getName();
				}
			}
			
			
			
			pWriter.println("<table id='carouselTable'>\n" +
								"<tr>\n" +
									"<td style='width:80%'>\n" +
										"<div id='myCarousel1' class='carousel slide' data-ride='carousel'>\n" +
												"<ol class='carousel-indicators'>\n" +
													"<li data-target='#myCarousel' data-slide-to='0' class='active'></li>\n" +
													"<li data-target='#myCarousel' data-slide-to='1'></li>\n" +
													"<li data-target='#myCarousel' data-slide-to='2'></li>\n" + 
													"<li data-target='#myCarousel' data-slide-to='3'></li>\n" +
													"<li data-target='#myCarousel' data-slide-to='4'></li>\n" +
													"<li data-target='#myCarousel' data-slide-to='5'></li>\n" + 
													"<li data-target='#myCarousel' data-slide-to='6'></li>\n" +
												"</ol>\n" + 
											"<div class='carousel-inner'>\n" +
												"<div class='item active'>\n" +
													"<p style='text-align:center;'><img src='/csj/" + imgList[0] + "' style='width:300px;height:300px' alt='" + imgList[0] + "'></p>\n" +
													"<br />\n" +
													"<p style='text-align:center;'><a href='#'>" + nameList[0] + "</a></p>\n" +
													"<br />\n" +
													"<p style='text-align:center;'><a href='#'>Price: $" + priceList[0] + "</a></p>\n" +
													"<br />\n" +
												"</div>");
						for(int c=1;c<n;c++) {
							pWriter.println("<div class='item'>\n" +
									"<p style='text-align:center;'><img src='/csj/" + imgList[c] + "' style='width:300px;height:300px' alt='" + imgList[c] + "'></p>\n" +
									"<br />\n" +
									"<p style='text-align:center;'><a href='#'>" + nameList[c] + "</a></p>\n" +
									"<br />\n" +
									"<p style='text-align:center;'><a href='#'>Price: $" + priceList[c] + "</a></p>\n" +
									"<br />\n" +
								"</div>");
						}
												
						pWriter.println("</div>\n" +
											"<a class='left carousel-control' href='#myCarousel1' data-slide='prev'>\n" + 
											"<span class='glyphicon glyphicon-chevron-left'></span>\n" + 
											"<span class='sr-only'>Previous</span>\n" + 
											"</a>\n" + 
											"<a class='right carousel-control' href='#myCarousel1' data-slide='next'>\n" + 
											"<span class='glyphicon glyphicon-chevron-right'></span>\n" + 
											"<span class='sr-only'>Next</span>\n" +
											"</a>\n" +
										"</div>\n" +
									"</td>\n" +
								"</tr>\n" +
							"</table>");
		}
		else {
			pWriter.println("<table id='carouselTable'>\n" +
							"<tr>\n" +
								"<td><p>" + dbStatus.get("msg") + "</p></td>\n" +
							"</tr>\n" +
							"</table>");
		}
	}
	*/
	
	public void printCarousel(PrintWriter pWriter,HttpServletRequest request,String productID,String productType) {

		String lastID = productID;
		String lastType = productType;
		HashMap<String, String> dbStatus = new HashMap<String, String>();
		dbStatus = dbObj.checkDBConnection();
		if(dbStatus.get("status").equals("true")) {
			HashMap<String, Accessories> lastItemMap = new HashMap<String, Accessories>();
			switch(lastType) {
				case "smartwatches":{
					SmartWatch tmp = dbObj.getWatchMapDB().get(lastID);
					if(tmp!=null)
					{
						lastItemMap = tmp.getAccessories();
					}
					break;
				}
				case "speakers":{
					Speakers tmp = dbObj.getSpeakerMapDB().get(lastID);
					if(tmp!=null)
					{
						lastItemMap = tmp.getAccessories();
					}
					break;
				}
				case "headphones":{
					Headphones tmp = dbObj.getHeadphoneMapDB().get(lastID);
					if(tmp!=null)
					{
						lastItemMap = tmp.getAccessories();
					}
					break;
				}
				case "smartphones":{
					Phones tmp = dbObj.getPhoneMapDB().get(lastID);
					if(tmp!=null)
					{
						lastItemMap = tmp.getAccessories();
					}
					break;
				}
				case "laptops":{
					Laptops tmp = dbObj.getLaptopMapDB().get(lastID);
					if(tmp!=null)
					{
						lastItemMap = tmp.getAccessories();
					}
					break;
				}
				case "externalstorage":{
					ExternalStorage tmp = dbObj.getStorageMapDB().get(lastID); 
					if(tmp!=null)
					{
						lastItemMap = tmp.getAccessories();
					}
					break;
				}
				case "accessories":{
					lastItemMap = dbObj.getAccessoryMapDB();
					break;
				}
				default:{
					SmartWatch temp = new SmartWatch();
					for(Map.Entry<String, SmartWatch> k : dbObj.getWatchMapDB().entrySet()) {					
						temp = k.getValue();
						break;
					}
					lastItemMap = temp.getAccessories();
					break;
				}
			}
			
			
			String[] idList = new String[30];
			String[] priceList = new String[30];
			String[] nameList = new String[30];
			String[] imgList = new String[30];
			Accessories accessory= new Accessories();
			Accessories accessoryTmp= new Accessories();
			int n=0;
			for(Map.Entry<String, Accessories> w : lastItemMap.entrySet()) {
				accessoryTmp = w.getValue();
				
				idList[n] = accessoryTmp.getId();
				System.out.println(idList[n] + "\t" + n);
				n++;
			}
			
			for(int k=0;k<n;k++) {
				accessory = dbObj.getAccessoryMapDB().get(idList[k]);
				if(accessory!=null)
				{
					imgList[k] = accessory.getImage();
					priceList[k] = String.valueOf(accessory.getPrice());
					nameList[k] = accessory.getName();
				}
			}
			
			
			
			pWriter.println("<table id='carouselTable'>\n" +
					"<tr>\n" +
						"<td style='width:80%'>\n" +
							"<div id='myCarousel1' class='carousel slide' data-ride='carousel'>\n" +
								"<div class='carousel-inner'>\n" +
									"<div class='item active'>\n" +
										"<table id='carouselTable'>\n" +
											"<tr>\n" +
												"<td style='width:50%;'>\n" +
													"<p style='text-align:center;'><img src='/csj/" + imgList[0] + "' style='width:150px;height:150px' alt='" + imgList[0] + "'></p>\n" +
													"<br />\n" +
													"<p style='text-align:center;'><a href='#'>" + nameList[0] + "</a></p>\n" +
													"<br />\n" +
													"<p style='text-align:center;'><a href='#'>Price: $" + priceList[0] + "</a></p>\n" +
													"<br />\n" +
												"</td>\n" +
												"<td style='width:50%;'>\n" +
													"<p style='text-align:center;'><img src='/csj/" + imgList[1] + "' style='width:150px;height:150px' alt='" + imgList[1] + "'></p>\n" +
													"<br />\n" +
													"<p style='text-align:center;'><a href='#'>" + nameList[1] + "</a></p>\n" +
													"<br />\n" +
													"<p style='text-align:center;'><a href='#'>Price: $" + priceList[1] + "</a></p>\n" +
													"<br />\n" +
												"</td>\n" +
											"</tr>\n" +
										"</table>\n" +
									"</div>");
			for(int c=2;c<n-1;c+=2) {
				pWriter.println("<div class='item'>\n" +
						"<table id='carouselTable'>\n" +
						"<tr>\n" +
							"<td style='width:50%;'>\n" +
								"<p style='text-align:center;'><img src='/csj/" + imgList[c] + "' style='width:150px;height:150px' alt='" + imgList[c] + "'></p>\n" +
								"<br />\n" +
								"<p style='text-align:center;'><a href='#'>" + nameList[c] + "</a></p>\n" +
								"<br />\n" +
								"<p style='text-align:center;'><a href='#'>Price: $" + priceList[c] + "</a></p>\n" +
								"<br />\n" +
							"</td>\n" +
							"<td style='width:50%;'>\n" +
								"<p style='text-align:center;'><img src='/csj/" + imgList[c+1] + "' style='width:150px;height:150px' alt='" + imgList[c+1] + "'></p>\n" +
								"<br />\n" +
								"<p style='text-align:center;'><a href='#'>" + nameList[c+1] + "</a></p>\n" +
								"<br />\n" +
								"<p style='text-align:center;'><a href='#'>Price: $" + priceList[c+1] + "</a></p>\n" +
								"<br />\n" +
							"</td>\n" +
						"</tr>\n" +
					"</table>\n" +
					"</div>");
			}
			
			pWriter.println("<div class='item'>\n" +
							"<table id='carouselTable'>\n" +
							"<tr>\n" +
								"<td>\n" +
									"<p style='text-align:center;'><img src='/csj/" + imgList[n-1] + "' style='width:150px;height:150px' alt='" + imgList[n-1] + "'></p>\n" +
									"<br />\n" +
									"<p style='text-align:center;'><a href='#'>" + nameList[n-1] + "</a></p>\n" +
									"<br />\n" +
									"<p style='text-align:center;'><a href='#'>Price: $" + priceList[n-1] + "</a></p>\n" +
									"<br />\n" +
								"</td>\n" +
							"</tr>\n" +
							"</table>\n" +
							"</div>\n" +				
						"</div>\n" +
								"<a class='left carousel-control' href='#myCarousel1' data-slide='prev'>\n" + 
								"<span class='glyphicon glyphicon-chevron-left'></span>\n" + 
								"<span class='sr-only'>Previous</span>\n" + 
								"</a>\n" + 
								"<a class='right carousel-control' href='#myCarousel1' data-slide='next'>\n" + 
								"<span class='glyphicon glyphicon-chevron-right'></span>\n" + 
								"<span class='sr-only'>Next</span>\n" +
								"</a>\n" +
							"</div>\n" +
						"</td>\n" +
					"</tr>\n" +
				"</table>");
		}
		else {
			pWriter.println("<table id='carouselTable'>\n" +
							"<tr>\n" +
								"<td><p>" + dbStatus.get("msg") + "</p></td>\n" +
							"</tr>\n" +
							"</table>");
		}
	}
	
	@SuppressWarnings({"unchecked"})
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String productType = request.getParameter("type");
		
		HttpSession session = request.getSession(true);
		
		if(id==null) {
			response.setContentType("text/html");
			PrintWriter pWriter = response.getWriter();
			HtmlEngine engine = new HtmlEngine(pWriter);
			engine.generateHtml("Header",request);
			engine.generateHtml("Content",request);
			engine.generateHtml("LeftNavigationBar",request);
			engine.generateHtml("Footer",request);
		}
		else {
			HashMap<String, String> dbStatus = new HashMap<String, String>();
			dbStatus = dbObj.checkDBConnection();
			if(dbStatus.get("status").equals("true")) {
				switch(productType) {
				case "smartwatches":{
					HashMap<String, SmartWatch> watchHashMap = dbObj.getWatchMapDB();
					SmartWatch watch = new SmartWatch();
					SmartWatch watchTmp = new SmartWatch();
					for(Map.Entry<String, SmartWatch> w : watchHashMap.entrySet()) {					
						watchTmp = w.getValue();
						if(id.equals(watchTmp.getId()))
						{
							watch = watchTmp;
							break;
						}
					}
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					engine.generateHtml("Header",request);
					
					pWriter.println("<div id='body' class='width'>\n" +
							"<section id='content'>\n" +
							"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='/csj/Controller/PortableServlet?id=" + watch.getId() + "&type=smartwatches&op=addtocart' method='get'>\n" +
								"<div class='form_container'>\n" +
									"<br />\n" +
									"<table id='ProductDetails'>\n" +
										"<tr>\n" +	
											"<th colspan='2'><div align='center'>Product Details</div></th>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td colspan='2'><p></p></td>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td style='text-align:center;' rowspan='5'>\n" +
												"<img src='/csj/" + watch.getImage() + "' style='width:300px;height:300px' alt='" + watch.getImage() +"'>\n" +
											"</td>\n" +
											"<td style='text-align:left;width:50%;'>\n" +
												"<label for='productNameLbl'>Product Name :</label><input type='text' id='productName' style='width:150px;background-color:#bfbfbf;' name='productName' value='" + watch.getName() + "' readonly/>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td style='text-align:left;width:50%;'>\n" +
												"<label for='productTypeLbl'>Product Type :</label><input type='text' id='productType' style='width:150px;background-color:#bfbfbf;' name='productType' value='" + productType + "' readonly/>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td style='text-align:left;width:50%;'>\n" +
												"<label for='priceLbl'>Price :</label><input type='text' id='productPrice' style='width:150px;background-color:#bfbfbf;' name='productPrice' value='" + String.valueOf(watch.getPrice()) + "' readonly/>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td style='text-align:left;width:50%;'>\n" +
												"<label for='conditionLbl'>Condition :</label><input type='text' id='condition' style='width:150px;background-color:#bfbfbf;' name='condition' value='" + watch.getCondition() + "' readonly/>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td style='text-align:left;width:50%;'>\n" +
												"<label for='manufacturerLbl'>Manufacturer :</label><input type='text' id='manufacturer' style='width:150px;background-color:#bfbfbf;' name='manufacturer' value='" + watch.getManufacturer() + "' readonly/>\n" +
											"</td>\n" +
										"</tr>\n" +
										"</table>");
										
					printCarousel(pWriter,request,id,"smartwatches");
					
					pWriter.println("</div>\n" +
									"</form>\n" +
									"</section>");
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					
					break;
				}
				case "speakers": {
					HashMap<String, Speakers> speakerMap = dbObj.getSpeakerMapDB();
					Speakers speaker = new Speakers();
					Speakers speakerTmp = new Speakers();
					for(Map.Entry<String, Speakers> w : speakerMap.entrySet()) {					
						speakerTmp = w.getValue();
						if(id.equals(speakerTmp.getId()))
						{
							speaker = speakerTmp;
							break;
						}
					}
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					engine.generateHtml("Header",request);
					
					pWriter.println("<div id='body' class='width'>\n" +
							"<section id='content'>\n" +
							"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='/csj/Controller/PortableServlet?id=" + speaker.getId() + "&type=speakers&op=addtocart' method='get'>\n" +
								"<div class='form_container'>\n" +
									"<br />\n" +
									"<table id='ProductDetails'>\n" +
										"<tr>\n" +	
											"<th colspan='2'><div align='center'>Product Details</div></th>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td colspan='2'><p></p></td>\n" +
										"</tr>\n" +
										"<tr>\n" +	
										"<td style='text-align:center;' rowspan='5'>\n" +
											"<img src='/csj/" + speaker.getImage() + "' style='width:300px;height:300px' alt='" + speaker.getImage() +"'>\n" +
										"</td>\n" +
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='productNameLbl'>Product Name :</label><input type='text' id='productName' style='width:150px;background-color:#bfbfbf;' name='productName' value='" + speaker.getName() + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='productTypeLbl'>Product Type :</label><input type='text' id='productType' style='width:150px;background-color:#bfbfbf;' name='productType' value='" + productType + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='priceLbl'>Price :</label><input type='text' id='productPrice' style='width:150px;background-color:#bfbfbf;' name='productPrice' value='" + String.valueOf(speaker.getPrice()) + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='conditionLbl'>Condition :</label><input type='text' id='condition' style='width:150px;background-color:#bfbfbf;' name='condition' value='" + speaker.getCondition() + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='manufacturerLbl'>Manufacturer :</label><input type='text' id='manufacturer' style='width:150px;background-color:#bfbfbf;' name='manufacturer' value='" + speaker.getManufacturer() + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
										"</table>");
										printCarousel(pWriter,request,id,"speakers");
					
					pWriter.println("</div>\n" +
									"</form>\n" +
									"</section>");
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				case "headphones": {
					HashMap<String, Headphones> headphonesMap = dbObj.getHeadphoneMapDB();
					Headphones headphone = new Headphones();
					Headphones headphoneTmp = new Headphones();
					for(Map.Entry<String, Headphones> w : headphonesMap.entrySet()) {					
						headphoneTmp = w.getValue();
						if(id.equals(headphoneTmp.getId()))
						{
							headphone = headphoneTmp;
							break;
						}
					}
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					engine.generateHtml("Header",request);
					
					pWriter.println("<div id='body' class='width'>\n" +
							"<section id='content'>\n" +
							"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='/csj/Controller/PortableServlet?id=" + headphone.getId() + "&type=headphones&op=addtocart' method='get'>\n" +
								"<div class='form_container'>\n" +
									"<br />\n" +
									"<table id='ProductDetails'>\n" +
										"<tr>\n" +	
											"<th colspan='2'><div align='center'>Product Details</div></th>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td colspan='2'><p></p></td>\n" +
										"</tr>\n" +
										"<tr>\n" +	
										"<td style='text-align:center;' rowspan='5'>\n" +
											"<img src='/csj/" + headphone.getImage() + "' style='width:300px;height:300px' alt='" + headphone.getImage() +"'>\n" +
										"</td>\n" +
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='productNameLbl'>Product Name :</label><input type='text' id='productName' style='width:150px;background-color:#bfbfbf;' name='productName' value='" + headphone.getName() + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='productTypeLbl'>Product Type :</label><input type='text' id='productType' style='width:150px;background-color:#bfbfbf;' name='productType' value='" + productType + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='priceLbl'>Price :</label><input type='text' id='productPrice' style='width:150px;background-color:#bfbfbf;' name='productPrice' value='" + String.valueOf(headphone.getPrice()) + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='conditionLbl'>Condition :</label><input type='text' id='condition' style='width:150px;background-color:#bfbfbf;' name='condition' value='" + headphone.getCondition() + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='manufacturerLbl'>Manufacturer :</label><input type='text' id='manufacturer' style='width:150px;background-color:#bfbfbf;' name='manufacturer' value='" + headphone.getManufacturer() + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
										"</table>");
					printCarousel(pWriter,request,id,"headphones");
					
					pWriter.println("</div>\n" +
									"</form>\n" +
									"</section>");
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				case "smartphones": {
					HashMap<String, Phones> phonesMap = dbObj.getPhoneMapDB();
					Phones phone = new Phones();
					Phones phoneTmp = new Phones();
					for(Map.Entry<String, Phones> w : phonesMap.entrySet()) {					
						phoneTmp = w.getValue();
						if(id.equals(phoneTmp.getId()))
						{
							phone = phoneTmp;
							break;
						}
					}
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					engine.generateHtml("Header",request);
					
					pWriter.println("<div id='body' class='width'>\n" +
							"<section id='content'>\n" +
							"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='/csj/Controller/PortableServlet?id=" + phone.getId() + "&type=smartphones&op=addtocart' method='get'>\n" +
								"<div class='form_container'>\n" +
									"<br />\n" +
									"<table id='ProductDetails'>\n" +
										"<tr>\n" +	
											"<th colspan='2'><div align='center'>Product Details</div></th>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td colspan='2'><p></p></td>\n" +
										"</tr>\n" +
										"<tr>\n" +	
										"<td style='text-align:center;' rowspan='5'>\n" +
											"<img src='/csj/" + phone.getImage() + "' style='width:300px;height:300px' alt='" + phone.getImage() +"'>\n" +
										"</td>\n" +
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='productNameLbl'>Product Name :</label><input type='text' id='productName' style='width:150px;background-color:#bfbfbf;' name='productName' value='" + phone.getName() + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='productTypeLbl'>Product Type :</label><input type='text' id='productType' style='width:150px;background-color:#bfbfbf;' name='productType' value='" + productType + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='priceLbl'>Price :</label><input type='text' id='productPrice' style='width:150px;background-color:#bfbfbf;' name='productPrice' value='" + String.valueOf(phone.getPrice()) + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='conditionLbl'>Condition :</label><input type='text' id='condition' style='width:150px;background-color:#bfbfbf;' name='condition' value='" + phone.getCondition() + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='manufacturerLbl'>Manufacturer :</label><input type='text' id='manufacturer' style='width:150px;background-color:#bfbfbf;' name='manufacturer' value='" + phone.getManufacturer() + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
										"</table>");
					printCarousel(pWriter,request,id,"smartphones");
					
					pWriter.println("</div>\n" +
									"</form>\n" +
									"</section>");
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				case "laptops": {
					HashMap<String, Laptops> laptopsMap = dbObj.getLaptopMapDB();
					Laptops laptop = new Laptops();
					Laptops laptopTmp = new Laptops();
					for(Map.Entry<String, Laptops> w : laptopsMap.entrySet()) {					
						laptopTmp = w.getValue();
						if(id.equals(laptopTmp.getId()))
						{
							laptop = laptopTmp;
							break;
						}
					}
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					engine.generateHtml("Header",request);
					
					pWriter.println("<div id='body' class='width'>\n" +
							"<section id='content'>\n" +
							"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='/csj/Controller/PortableServlet?id=" + laptop.getId() + "&type=laptops&op=addtocart' method='get'>\n" +
								"<div class='form_container'>\n" +
									"<br />\n" +
									"<table id='ProductDetails'>\n" +
										"<tr>\n" +	
											"<th colspan='2'><div align='center'>Product Details</div></th>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td colspan='2'><p></p></td>\n" +
										"</tr>\n" +
										"<tr>\n" +	
										"<td style='text-align:center;' rowspan='5'>\n" +
											"<img src='/csj/" + laptop.getImage() + "' style='width:300px;height:300px' alt='" + laptop.getImage() +"'>\n" +
										"</td>\n" +
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='productNameLbl'>Product Name :</label><input type='text' id='productName' style='width:150px;background-color:#bfbfbf;' name='productName' value='" + laptop.getName() + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='productTypeLbl'>Product Type :</label><input type='text' id='productType' style='width:150px;background-color:#bfbfbf;' name='productType' value='" + productType + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='priceLbl'>Price :</label><input type='text' id='productPrice' style='width:150px;background-color:#bfbfbf;' name='productPrice' value='" + String.valueOf(laptop.getPrice()) + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='conditionLbl'>Condition :</label><input type='text' id='condition' style='width:150px;background-color:#bfbfbf;' name='condition' value='" + laptop.getCondition() + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='manufacturerLbl'>Manufacturer :</label><input type='text' id='manufacturer' style='width:150px;background-color:#bfbfbf;' name='manufacturer' value='" + laptop.getManufacturer() + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
										"</table>");
					printCarousel(pWriter,request,id,"laptops");
					
					pWriter.println("</div>\n" +
									"</form>\n" +
									"</section>");
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				case "externalstorage": {
					HashMap<String, ExternalStorage> externalStorageMap = dbObj.getStorageMapDB();
					ExternalStorage storage = new ExternalStorage();
					ExternalStorage storageTmp = new ExternalStorage();
					for(Map.Entry<String, ExternalStorage> w : externalStorageMap.entrySet()) {					
						storageTmp = w.getValue();
						if(id.equals(storageTmp.getId()))
						{
							storage = storageTmp;
							break;
						}
					}
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					engine.generateHtml("Header",request);
					
					pWriter.println("<div id='body' class='width'>\n" +
							"<section id='content'>\n" +
							"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='/csj/Controller/PortableServlet?id=" + storage.getId() + "&type=externalstorage&op=addtocart' method='get'>\n" +
								"<div class='form_container'>\n" +
									"<br />\n" +
									"<table id='ProductDetails'>\n" +
										"<tr>\n" +	
											"<th colspan='2'><div align='center'>Product Details</div></th>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td colspan='2'><p></p></td>\n" +
										"</tr>\n" +
										"<tr>\n" +	
										"<td style='text-align:center;' rowspan='5'>\n" +
											"<img src='/csj/" + storage.getImage() + "' style='width:300px;height:300px' alt='" + storage.getImage() +"'>\n" +
										"</td>\n" +
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='productNameLbl'>Product Name :</label><input type='text' id='productName' style='width:150px;background-color:#bfbfbf;' name='productName' value='" + storage.getName() + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='productTypeLbl'>Product Type :</label><input type='text' id='productType' style='width:150px;background-color:#bfbfbf;' name='productType' value='" + productType + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='priceLbl'>Price :</label><input type='text' id='productPrice' style='width:150px;background-color:#bfbfbf;' name='productPrice' value='" + String.valueOf(storage.getPrice()) + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='conditionLbl'>Condition :</label><input type='text' id='condition' style='width:150px;background-color:#bfbfbf;' name='condition' value='" + storage.getCondition() + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='manufacturerLbl'>Manufacturer :</label><input type='text' id='manufacturer' style='width:150px;background-color:#bfbfbf;' name='manufacturer' value='" + storage.getManufacturer() + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
										"</table>");
					printCarousel(pWriter,request,id,"externalstorage");
								pWriter.println("</div>\n" +
									"</form>\n" +
									"</section>");
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				case "accessories": {
					HashMap<String, Accessories> accessoryMap = dbObj.getAccessoryMapDB();
					Accessories accessory = new Accessories();
					Accessories accessoryTmp = new Accessories();
					for(Map.Entry<String, Accessories> w : accessoryMap.entrySet()) {					
						accessoryTmp = w.getValue();
						if(id.equals(accessoryTmp.getId()))
						{
							accessory = accessoryTmp;
							break;
						}
					}
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					engine.generateHtml("Header",request);
					
					pWriter.println("<div id='body' class='width'>\n" +
							"<section id='content'>\n" +
							"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='/csj/Controller/PortableServlet?id=" + accessory.getId() + "&type=accessories&op=addtocart' method='get'>\n" +
								"<div class='form_container'>\n" +
									"<br />\n" +
									"<table id='ProductDetails'>\n" +
										"<tr>\n" +	
											"<th colspan='2'><div align='center'>Product Details</div></th>\n" +
										"</tr>\n" +
										"<tr>\n" +	
											"<td colspan='2'><p></p></td>\n" +
										"</tr>\n" +
										"<tr>\n" +	
										"<td style='text-align:center;' rowspan='5'>\n" +
											"<img src='/csj/" + accessory.getImage() + "' style='width:300px;height:300px' alt='" + accessory.getImage() +"'>\n" +
										"</td>\n" +
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='productNameLbl'>Product Name :</label><input type='text' id='productName' style='width:150px;background-color:#bfbfbf;' name='productName' value='" + accessory.getName() + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='productTypeLbl'>Product Type :</label><input type='text' id='productType' style='width:150px;background-color:#bfbfbf;' name='productType' value='" + productType + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='priceLbl'>Price :</label><input type='text' id='productPrice' style='width:150px;background-color:#bfbfbf;' name='productPrice' value='" + String.valueOf(accessory.getPrice()) + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='conditionLbl'>Condition :</label><input type='text' id='condition' style='width:150px;background-color:#bfbfbf;' name='condition' value='" + accessory.getCondition() + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td style='text-align:left;width:50%;'>\n" +
											"<label for='manufacturerLbl'>Manufacturer :</label><input type='text' id='manufacturer' style='width:150px;background-color:#bfbfbf;' name='manufacturer' value='" + accessory.getManufacturer() + "' readonly/>\n" +
										"</td>\n" +
									"</tr>\n" +
										"</table>");
					//printCarousel(pWriter,request,id,"accessories");
					pWriter.println("</div>\n" +
									"</form>\n" +
									"</section>");
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
				default: {
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					engine.generateHtml("Header",request);
					engine.generateHtml("Content",request);
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
					break;
				}
			}
			}
			else {
				response.setContentType("text/html");
				PrintWriter pWriter = response.getWriter();
				HtmlEngine engine = new HtmlEngine(pWriter);
				engine.generateHtml("Header",request);
				//System.out.println("msg-" + dbStatus.get("msg"));
				pWriter.println("<div id='body' class='width'>\n" +
						"<section id='content'>\n" +
						"<div style='height:50px;'></div>\n" +
						"<form class='register' action='' method='POST'>\n" +
						"<div class='form_container'>\n" +
							"<table id='ProductListTable'>\n" +
								"<tr>\n" +
									"<td colspan='2' style='text-align:center;'>\n" +
										"<p>" + dbStatus.get("msg") + "</p>\n" +										
									"</td>\n" +
								"</tr>\n" +
							"</table>\n" +
						"</div>\n" +
						"</form>\n" +
						"</section>");
				
				engine.generateHtml("LeftNavigationBar",request);
				engine.generateHtml("Footer",request);
			}
		}
		
	}
	
	
}