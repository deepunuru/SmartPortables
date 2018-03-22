package Controller;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;

import javax.servlet.*;
import javax.servlet.http.*;
import DataAccess.MySQLDataStoreUtilities;
import Controller.HtmlEngine;
import JavaBeans.*;

public class ManagerHomeServlet extends HttpServlet{	
	MySQLDataStoreUtilities dbObj;
	
	public ManagerHomeServlet () {
		dbObj = new MySQLDataStoreUtilities();
	}
	
	public void displayLogoutHeader(PrintWriter pWriter) {
		String docType = "<!doctype html>\n";
		pWriter.println(docType + 
		"<html>\n" +
            "<head>\n" +
            "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />\n" +
            "<title>Smart Portables</title>\n" +
            "<link rel='stylesheet' href='/csj/styles.css' type='text/css' />\n" +
            "<link rel='stylesheet' href='/csj/style.css' type='text/css' />\n" +
            "<meta charset='utf-8'>\n" +
            "<meta name='viewport' content='width=device-width, initial-scale=1'>\n" +
            "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>\n" + 
            "<script type='text/javascript' src='/csj/autoCompleteJS.js'></script>\n" +
            "<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>\n" + 
            "<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'></script>\n" +
            "<script type='text/javascript' src='/csj/accessoryScript.js'></script>\n" +  
            "<!--[if lt IE 9]>\n" +
            "<script src='http://html5shiv.googlecode.com/svn/trunk/html5.js'></script>\n" +
            "<![endif]-->\n" +
            "<meta name='viewport' content='width=device-width, minimum-scale=1.0, maximum-scale=1.0' />\n" +
            "</head>\n" +
            "<body onload='init()'>\n" +
            "<div id='container'>\n" +
            	"<header>\n" + 
            		"<a href='/csj/Controller/HomeServlet?var=home' method='get' class='topic'>Smart Portables</a>\n" +
            		"<h5>You won't find a better deal anywhere else.</h5>\n" +
            		"<a href='/csj/Controller/LogoutServlet' method='get' class='reg'>Logout</a>\n" +
            	"</header>\n" +
            	"<nav>\n" +
            		"<div class='width'>\n" +
            		"<table id='navTable'>\n" +
            		"<tr>\n" +
						"<td style='width:725px'>\n" +
            			"<ul>\n" +
            				"<li class='start selected'><a href='/csj/Controller/HomeServlet?var=home' method='get'>Home</a></li>\n" +
            				"<li><a href='/csj/Controller/HomeServlet?var=products' method='get'>Products</a></li>\n" +
            				"<li><a href='/csj/Controller/HomeServlet?var=accessories' method='get'>Accessories</a></li>\n" +
            				"<li><a href='/csj/Controller/HomeServlet?var=contactus' method='get'>Contact Us</a></li>\n" +
            				"<li><a href='/csj/Controller/DataAnalyticsServlet?op=home' method='get'>Data Analytics</a></li>\n" +
            				"<li><a href='/csj/Controller/DataExplorationServlet' method='get'>Data Exploration</a></li>\n" +
            				"<li><a href='/csj/Controller/DataAnalyticsServlet?op=inventory' method='get'>Inventory</a></li>\n" +
            				"<li class='end'><a href='/csj/Controller/DataAnalyticsServlet?op=salesreports' method='get'>Sales Reports</a></li>\n" +
            			"</ul>\n" +
            			"</td>\n" +
            			"<td style='width:75px;'>\n" +
            			"</td>\n" +
            		"</table>\n" +
            		"</div>\n" +
				"</nav>");
	}
	
	public void printUpdateForm(PrintWriter pWriter,String type,String productID,String productName,String productImage,String productCondition,String price,String manufacturer,String discount) {
		Accessories accessory = new Accessories();
		HashMap<String, Accessories> accessoryMap = new HashMap<String, Accessories>();
		HashMap<String, String> dbStatus = new HashMap<String, String>();
		dbStatus = dbObj.checkDBConnection();
		if(dbStatus.get("status").equals("true")) {
			accessoryMap = dbObj.getAccessoryMapDB();
			pWriter.println("<div id='body' class='width'>\n" +
					"<section id='content'>\n" +
					"<div style='height:50px;'></div>\n" +
					"<form class='register' action='/csj/Controller/ManagerHomeServlet?op=updateProduct&oldID=" + productID + "' method='POST'>\n" +
						"<div class='form_container'>\n" +
							"<table id='updateProductTable'>\n" +
								"<tr>\n" +	
									"<th colspan='2'><div align='center'>Update Product Form</div></th>\n" +
								"</tr>\n" +
								"<tr>\n" +	
									"<td colspan='2' style='text-align:center;'>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='retailerLbl'>Retailer : </label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='text' id='retailer' style='width:200px;background-color:#bfbfbf;' name='retailer' value='SmartPortables' readonly/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='productTypeLbl'>Product Type :</label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='text' id='productType' style='width:200px;background-color:#bfbfbf;' name='productType' value='" + type + "' readonly/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='Accessory1'>Accessory-1 : </label>\n" +
									"</td>\n" +
									"<td style='text-align:center;'>\n" +
										"<select name='accessory1' id='accessory1' style='width:200px;'>");
											if(type=="accessories") {
												pWriter.println("<option value='Not Applicable'>Not Applicable</option>");
											}
											else {
												for(Map.Entry<String, Accessories> w : accessoryMap.entrySet()) {	
													accessory = w.getValue();
													if(type.equals(accessory.getType())) {
														pWriter.println("<option value='" + accessory.getId() + "'>" + accessory.getId() + "</option>");											
													}
												}
											}
											pWriter.println("</select>\n" +
									"</td>\n" +
								"</tr>\n" +	
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='Accessory2'>Accessory-2 : </label>\n" +
									"</td>\n" +
									"<td style='text-align:center;'>\n" +
										"<select name='accessory2' id='accessory2' style='width:200px;'>");
											if(type=="accessories") {
												pWriter.println("<option value='Not Applicable'>Not Applicable</option>");
											}
											else {
												for(Map.Entry<String, Accessories> w : accessoryMap.entrySet()) {	
													accessory = w.getValue();
													if(type.equals(accessory.getType())) {
														pWriter.println("<option value='" + accessory.getId() + "'>" + accessory.getId() + "</option>");											
													}
												}
											}
											pWriter.println("</select>\n" +
									"</td>\n" +
								"</tr>\n" +	
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='Accessory3'>Accessory-3 : </label>\n" +
									"</td>\n" +
									"<td style='text-align:center;'>\n" +
										"<select name='accessory3' id='accessory3' style='width:200px;'>");
											if(type=="accessories") {
												pWriter.println("<option value='Not Applicable'>Not Applicable</option>");
											}
											else {
												for(Map.Entry<String, Accessories> w : accessoryMap.entrySet()) {	
													accessory = w.getValue();
													if(type.equals(accessory.getType())) {
														pWriter.println("<option value='" + accessory.getId() + "'>" + accessory.getId() + "</option>");											
													}
												}
											}
										pWriter.println("</select>\n" +
									"</td>\n" +
								"</tr>\n" +	
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='Accessory4'>Accessory-4 : </label>\n" +
									"</td>\n" +
									"<td style='text-align:center;'>\n" +
										"<select name='accessory4' id='accessory4' style='width:200px;'>");
										if(type=="accessories") {
											pWriter.println("<option value='Not Applicable'>Not Applicable</option>");
										}
										else {
											for(Map.Entry<String, Accessories> w : accessoryMap.entrySet()) {	
												accessory = w.getValue();
												if(type.equals(accessory.getType())) {
													pWriter.println("<option value='" + accessory.getId() + "'>" + accessory.getId() + "</option>");											
												}
											}
										}
										pWriter.println("</select>\n" +
									"</td>\n" +
								"</tr>\n" +	
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='Accessory5'>Accessory-5 : </label>\n" +
									"</td>\n" +
									"<td style='text-align:center;'>\n" +
										"<select name='accessory5' id='accessory5' style='width:200px;'>");
										if(type=="accessories") {
											pWriter.println("<option value='Not Applicable'>Not Applicable</option>");
										}
										else {
											for(Map.Entry<String, Accessories> w : accessoryMap.entrySet()) {	
												accessory = w.getValue();
												if(type.equals(accessory.getType())) {
													pWriter.println("<option value='" + accessory.getId() + "'>" + accessory.getId() + "</option>");											
												}
											}
										}
										pWriter.println("</select>\n" +
									"</td>\n" +
								"</tr>\n" +	
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='Accessory6'>Accessory-6 : </label>\n" +
									"</td>\n" +
									"<td style='text-align:center;'>\n" +
										"<select name='accessory6' id='accessory6' style='width:200px;'>");
										if(type=="accessories") {
											pWriter.println("<option value='Not Applicable'>Not Applicable</option>");
										}
										else {
											for(Map.Entry<String, Accessories> w : accessoryMap.entrySet()) {	
												accessory = w.getValue();
												if(type.equals(accessory.getType())) {
													pWriter.println("<option value='" + accessory.getId() + "'>" + accessory.getId() + "</option>");											
												}
											}
										}
										pWriter.println("</select>\n" +
									"</td>\n" +
								"</tr>\n" +	
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='Accessory7'>Accessory-7 : </label>\n" +
									"</td>\n" +
									"<td style='text-align:center;'>\n" +
										"<select name='accessory7' id='accessory7' style='width:200px;'>");
										if(type=="accessories") {
											pWriter.println("<option value='Not Applicable'>Not Applicable</option>");
										}
										else {
											for(Map.Entry<String, Accessories> w : accessoryMap.entrySet()) {	
												accessory = w.getValue();
												if(type.equals(accessory.getType())) {
													pWriter.println("<option value='" + accessory.getId() + "'>" + accessory.getId() + "</option>");											
												}
											}
										}
										pWriter.println("</select>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='productID'>Product ID :</label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='text' id='productID' style='width:200px' name='productID' value='" + productID + "' required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='productName'>Product Name :</label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='text' id='productName' style='width:200px' name='productName' value='" + productName + "' required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='productImage'>Product Image :</label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='file' id='productImage' style='width:200px' name='productImage' value='" + productImage + "' required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='productCondition'>Product Condition :</label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='text' id='productCondition' style='width:200px' name='productCondition' value='" + productCondition + "' required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='price'>Product Price :</label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type ='text' id='price' style='width:200px' name='price' value='" + price + "'required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='manufacturer'>Product Manufacturer :</label>\n" +
									"</td>\n" +
									"<td style='text-align:right;'>\n" +
										"<input type='text' id='manufacturer' style='width:200px' name='manufacturer' value='" + manufacturer + "' required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='discount'>Product Discount :</label>\n" +
									"</td>\n" +
									"<td style='text-align:right;'>\n" +
										"<input type='text' id='discount' style='width:200px' name='discount' value='" + discount + "' required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td colspan='2' align='center'>\n" +
										"<button name='sbmtButton' type='submit' style='width:80px;'>Submit</button>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td colspan='2' style='text-align:right;'>\n" +
										"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
									"</td>\n" +
								"</tr>\n" +
							"</table>\n" +
							"</div>\n" +
						"</form>\n" +
					"</section>");
		}
		else {
			pWriter.println("<div id='body' class='width'>\n" +
					"<section id='content'>\n" +
					"<div style='height:50px;'></div>\n" +
					"<form class='register' action='' method='POST'>\n" +
						"<div class='form_container'>\n" +
							"<table id='updateProductTable'>\n" +
							"<tr>\n" +	
								"<th colspan='2'><div align='center'>Update Product Form</div></th>\n" +
							"</tr>\n" +
							"<tr>\n" +	
								"<td colspan='2' style='text-align:center;'>\n" +
									"<p>" + dbStatus.get("msg") + "</p>\n" +	
								"</td>\n" +
							"</tr>\n" +
							"<tr>\n" +
							"<td colspan='2' style='text-align:right;'>\n" +
								"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
							"</td>\n" +
						"</tr>\n" +
					"</table>\n" +
					"</div>\n" +
				"</form>\n" +
			"</section>");
		}
	}
	
	public void printProduct(String[] imgList,String[] priceList,String[] nameList,String[] idList,PrintWriter pWriter,int k,String type){
		pWriter.println("<img src='/csj/" + imgList[k] + "' style='width:150px;height:150px' alt='" + imgList[k] +"'>\n" +
				"<p><a href='/csj/Controller/DisplayProduct?id=" + idList[k] + "&type=" + type + "' method='get'>" + nameList[k] +"</a>\n" +
				"<br />\n" +
				"Price: $" + priceList[k] +"\n" +
				"<br />\n" +
				"<a id='editProductLink' href='/csj/Controller/ManagerHomeServlet?op=updateForm&id=" + idList[k] + "&type=" + type + "' method='get'>Edit Product</a></p>\n" +
				"<br />");
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession userSession = request.getSession(true);
			String manager = (String)userSession.getAttribute("managerKey");
			if(manager!=null) {
				String op = request.getParameter("op");
				if(op == null) {
					response.sendRedirect("/csj/Controller/HomeServlet?var=home");
				}
				else {
					HashMap<String, Accessories> accessoryMap = dbObj.getAccessoryMapDB();
					Accessories accessory = new Accessories();
					String[] aList = new String[200];
					int i=0;
					for(Map.Entry<String, Accessories> w : dbObj.getAccessoryMapDB().entrySet()) {	
						accessory = w.getValue();
						aList[i] = accessory.getId();
						i++;
					}
					accessory = null;
					int count = i;
					switch(op) {
					case "addProduct":{
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						displayLogoutHeader(pWriter);
						
						//Body Content
						pWriter.println("<div id='body' class='width'>\n" +
								"<section id='content'>\n" +
								"<div style='height:50px;'></div>\n" +
								"<form class='register' action='/csj/Controller/ManagerHomeServlet?op=addProduct' method='POST'>\n" +
									"<div class='form_container'>\n" +
										"<table id='addProductTable'>\n" +
											"<tr>\n" +	
												"<th colspan='2'><div align='center'>Add Product Form</div></th>\n" +
											"</tr>\n" +
											"<tr>\n" +	
												"<td colspan='2' style='text-align:center;'>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='ProductType'>Product Type : </label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<select name='productType' id='productType' style='width:200px;'>\n" + 
													"  <option value='selectType'>select type</option>\n" + 
													"  <option value='smartwatches'>smartwatches</option>\n" + 
													"  <option value='speakers'>speakers</option>\n" + 
													"  <option value='headphones'>headphones</option>\n" +
													"  <option value='smartphones'>smartphones</option>\n" +
													"  <option value='laptops'>laptops</option>\n" + 
													"  <option value='externalstorage'>externalstorage</option>\n" + 
													"  <option value='accessories'>accessories</option>\n" + 
													"</select>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='Accessory1'>Accessory-1 : </label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
												"<select name='accessory1' id='accessory1' style='width:200px;'>\n" +
												"<option value='selectAccessory'>select accessory</option>\n" +
												"</select>\n" +
												"</td>\n" +
											"</tr>\n" +	
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='Accessory2'>Accessory-2 : </label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
												"<select name='accessory2' id='accessory2' style='width:200px;'>\n" +
												"<option value='selectAccessory'>select accessory</option>\n" +
												"</select>\n" +
												"</td>\n" +
											"</tr>\n" +	
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='Accessory3'>Accessory-3 : </label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
												"<select name='accessory3' id='accessory3' style='width:200px;'>\n" +
												"<option value='selectAccessory'>select accessory</option>\n" +
												"</select>\n" +
												"</td>\n" +
											"</tr>\n" +	
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='Accessory4'>Accessory-4 : </label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<select name='accessory4' id='accessory4' style='width:200px;'>\n" +
												"<option value='selectAccessory'>select accessory</option>\n" +
												"</select>\n" +
												"</td>\n" +
											"</tr>\n" +	
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='Accessory5'>Accessory-5 : </label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<select name='accessory5' id='accessory5' style='width:200px;'>\n" +
												"<option value='selectAccessory'>select accessory</option>\n" +
												"</select>\n" +
												"</td>\n" +
											"</tr>\n" +	
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='Accessory6'>Accessory-6 : </label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<select name='accessory6' id='accessory6' style='width:200px;'>\n" +
												"<option value='selectAccessory'>select accessory</option>\n" +
												"</select>\n" +	
												"</td>\n" +
											"</tr>\n" +	
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='Accessory7'>Accessory-7 : </label>\n" +
												"</td>\n" +
												"<td style='text-align:center;'>\n" +
													"<select name='accessory7' id='accessory7' style='width:200px;'>\n" +
												"<option value='selectAccessory'>select accessory</option>\n" +
												"</select>\n" +	
											"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='retailerLbl'>Retailer : </label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<input type='text' id='retailer' style='width:200px;background-color:#bfbfbf;' name='retailer' value='SmartPortables' readonly/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='productID'>Product ID :</label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<input type='text' id='productID' style='width:200px' name='productID' required/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='productName'>Product Name :</label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<input type='text' id='productName' style='width:200px' name='productName' required/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='productImage'>Product Image :</label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<input type='file' id='productImage' style='width:200px' name='productImage' required/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='productCondition'>Product Condition :</label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<input type='text' id='productCondition' style='width:200px' name='productCondition' required/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='price'>Product Price :</label>\n" +
												"</td>\n" +
												"<td style='text-align:left;'>\n" +
													"<input type ='text' id='price' style='width:200px' name='price' required/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='manufacturer'>Product Manufacturer :</label>\n" +
												"</td>\n" +
												"<td style='text-align:right;'>\n" +
													"<input type='text' id='manufacturer' style='width:200px' name='manufacturer' required/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td style='text-align:right;'>\n" +
													"<label for='discount'>Product Discount :</label>\n" +
												"</td>\n" +
												"<td style='text-align:right;'>\n" +
													"<input type='text' id='discount' style='width:200px' name='discount' required/>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td colspan='2' align='center'>\n" +
													"<button name='sbmtButton' type='submit' style='width:80px;'>Submit</button>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
												"</td>\n" +
											"</tr>\n" +
										"</table>\n" +
										"</div>\n" +
									"</form>\n" +
								"</section>");
						
						engine.generateHtml("LeftNavigationBar",request);
						engine.generateHtml("Footer",request);
						break;
					}
					
					case "updateForm": {
						String id = request.getParameter("id");
						String type = request.getParameter("type");
						
						switch(type) {
							case "smartwatches":{
								//display update for product with id
								response.setContentType("text/html");
								PrintWriter pWriter = response.getWriter();
								HtmlEngine engine = new HtmlEngine(pWriter);
								displayLogoutHeader(pWriter);
								
								SmartWatch watch = new SmartWatch();
								HashMap<String, String> dbStatus = new HashMap<String, String>();
								dbStatus = dbObj.checkDBConnection();
								if(dbStatus.get("status").equals("true")) {
									for(Map.Entry<String, SmartWatch> w : dbObj.getWatchMapDB().entrySet()) {	
										watch = w.getValue();
										if(id.equals(watch.getId())) {
											double d = watch.getPrice();
											String price = String.valueOf(d);
											double dis = watch.getDiscount();
											String discount = String.valueOf(dis);
											printUpdateForm(pWriter,type,watch.getId(),watch.getName(),watch.getImage(),watch.getCondition(),price,watch.getManufacturer(),discount);
											break;
										}
									}
								}
								else {
									printUpdateForm(pWriter,type,"","","","","","","");
								}
								
								engine.generateHtml("LeftNavigationBar",request);
								engine.generateHtml("Footer",request);
								break;
							}
							case "speakers":{
								//display update for product with id
								response.setContentType("text/html");
								PrintWriter pWriter = response.getWriter();
								HtmlEngine engine = new HtmlEngine(pWriter);
								displayLogoutHeader(pWriter);
								
								Speakers speaker = new Speakers();
								HashMap<String, String> dbStatus = new HashMap<String, String>();
								dbStatus = dbObj.checkDBConnection();
								if(dbStatus.get("status").equals("true")) {
									for(Map.Entry<String, Speakers> w : dbObj.getSpeakerMapDB().entrySet()) {	
										speaker = w.getValue();
										if(id.equals(speaker.getId())) {
											double d = speaker.getPrice();
											String price = String.valueOf(d);
											double dis = speaker.getDiscount();
											String discount = String.valueOf(dis);
											printUpdateForm(pWriter,type,speaker.getId(),speaker.getName(),speaker.getImage(),speaker.getCondition(),price,speaker.getManufacturer(),discount);
											break;
										}
									}
								}
								else {
									printUpdateForm(pWriter,type,"","","","","","","");
								}
								engine.generateHtml("LeftNavigationBar",request);
								engine.generateHtml("Footer",request);
								break;
							}
							case "headphones":{
								//display update for product with id
								response.setContentType("text/html");
								PrintWriter pWriter = response.getWriter();
								HtmlEngine engine = new HtmlEngine(pWriter);
								displayLogoutHeader(pWriter);
								
								Headphones headphone = new Headphones();
								HashMap<String, String> dbStatus = new HashMap<String, String>();
								dbStatus = dbObj.checkDBConnection();
								if(dbStatus.get("status").equals("true")) {
									for(Map.Entry<String, Headphones> w : dbObj.getHeadphoneMapDB().entrySet()) {	
										headphone = w.getValue();
										if(id.equals(headphone.getId())) {
											double d = headphone.getPrice();
											String price = String.valueOf(d);
											double dis = headphone.getDiscount();
											String discount = String.valueOf(dis);
											printUpdateForm(pWriter,type,headphone.getId(),headphone.getName(),headphone.getImage(),headphone.getCondition(),price,headphone.getManufacturer(),discount);
											break;
										}
									}
								}
								else {
									printUpdateForm(pWriter,type,"","","","","","","");
								}
								engine.generateHtml("LeftNavigationBar",request);
								engine.generateHtml("Footer",request);
								break;
							}
							case "smartphones":{
								//display update for product with id
								response.setContentType("text/html");
								PrintWriter pWriter = response.getWriter();
								HtmlEngine engine = new HtmlEngine(pWriter);
								displayLogoutHeader(pWriter);
								
								Phones phone = new Phones();
								HashMap<String, String> dbStatus = new HashMap<String, String>();
								dbStatus = dbObj.checkDBConnection();
								if(dbStatus.get("status").equals("true")) {
									for(Map.Entry<String, Phones> w : dbObj.getPhoneMapDB().entrySet()) {	
										phone = w.getValue();
										if(id.equals(phone.getId())) {
											double d = phone.getPrice();
											String price = String.valueOf(d);
											double dis = phone.getDiscount();
											String discount = String.valueOf(dis);
											printUpdateForm(pWriter,type,phone.getId(),phone.getName(),phone.getImage(),phone.getCondition(),price,phone.getManufacturer(),discount);
											break;
										}
									}
								}
								else {
									printUpdateForm(pWriter,type,"","","","","","","");
								}
								engine.generateHtml("LeftNavigationBar",request);
								engine.generateHtml("Footer",request);
								break;
							}
							case "laptops":{
								//display update for product with id
								response.setContentType("text/html");
								PrintWriter pWriter = response.getWriter();
								HtmlEngine engine = new HtmlEngine(pWriter);
								displayLogoutHeader(pWriter);
								
								Laptops laptop = new Laptops();
								HashMap<String, String> dbStatus = new HashMap<String, String>();
								dbStatus = dbObj.checkDBConnection();
								if(dbStatus.get("status").equals("true")) {
									for(Map.Entry<String, Laptops> w : dbObj.getLaptopMapDB().entrySet()) {	
										laptop = w.getValue();
										if(id.equals(laptop.getId())) {
											double d = laptop.getPrice();
											String price = String.valueOf(d);
											double dis = laptop.getDiscount();
											String discount = String.valueOf(dis);
											printUpdateForm(pWriter,type,laptop.getId(),laptop.getName(),laptop.getImage(),laptop.getCondition(),price,laptop.getManufacturer(),discount);
											break;
										}
									}
								}
								else {
									printUpdateForm(pWriter,type,"","","","","","","");
								}
								engine.generateHtml("LeftNavigationBar",request);
								engine.generateHtml("Footer",request);
								break;
							}
							case "externalstorage":{
								//display update for product with id
								response.setContentType("text/html");
								PrintWriter pWriter = response.getWriter();
								HtmlEngine engine = new HtmlEngine(pWriter);
								displayLogoutHeader(pWriter);
								
								ExternalStorage storage = new ExternalStorage();
								HashMap<String, String> dbStatus = new HashMap<String, String>();
								dbStatus = dbObj.checkDBConnection();
								if(dbStatus.get("status").equals("true")) {
									for(Map.Entry<String, ExternalStorage> w : dbObj.getStorageMapDB().entrySet()) {	
										storage = w.getValue();
										if(id.equals(storage.getId())) {
											double d = storage.getPrice();
											String price = String.valueOf(d);
											double dis = storage.getDiscount();
											String discount = String.valueOf(dis);
											printUpdateForm(pWriter,type,storage.getId(),storage.getName(),storage.getImage(),storage.getCondition(),price,storage.getManufacturer(),discount);
											break;
										}
									}
								}
								else {
									printUpdateForm(pWriter,type,"","","","","","","");
								}
								engine.generateHtml("LeftNavigationBar",request);
								engine.generateHtml("Footer",request);
								break;
							}
							case "accessories":{
								//display update for product with id
								response.setContentType("text/html");
								PrintWriter pWriter = response.getWriter();
								HtmlEngine engine = new HtmlEngine(pWriter);
								displayLogoutHeader(pWriter);
								
								accessory = new Accessories();
								HashMap<String, String> dbStatus = new HashMap<String, String>();
								dbStatus = dbObj.checkDBConnection();
								if(dbStatus.get("status").equals("true")) {
									for(Map.Entry<String, Accessories> w : dbObj.getAccessoryMapDB().entrySet()) {	
										accessory = w.getValue();
										if(id.equals(accessory.getId())) {
											double d = accessory.getPrice();
											String price = String.valueOf(d);
											double dis = accessory.getDiscount();
											String discount = String.valueOf(dis);
											printUpdateForm(pWriter,type,accessory.getId(),accessory.getName(),accessory.getImage(),accessory.getCondition(),price,accessory.getManufacturer(),discount);
											break;
										}
									}
								}
								else {
									printUpdateForm(pWriter,type,"","","","","","","");
								}
								engine.generateHtml("LeftNavigationBar",request);
								engine.generateHtml("Footer",request);
								break;
							}
							
							default:{
								break;
							}
							
						}
						break;
					}
					
					case "deleteProduct":{
						String id = request.getParameter("id");
						String type = request.getParameter("type");
						//String relativeWebPath = "/WEB-INF/classes/ProductCatalog.xml";
						//String absoluteDiskPath = getServletContext().getRealPath(relativeWebPath);						
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						
						HtmlEngine engine = new HtmlEngine(pWriter);
						displayLogoutHeader(pWriter);
						HashMap<String, String> dbStatus = new HashMap<String, String>();
						dbStatus = dbObj.checkDBConnection();
						if(dbStatus.get("status").equals("true")) {
							dbObj.deleteProduct(id);
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form class='register' action='' method='POST'>\n" +
									"<div class='form_container'>\n" +
										"<table id='prodDeleteTable'>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:center;'>\n" +
													"<p>Product - " + id +" successfully deleted.</p>\n" +
													"<p>Please use the link below to go back to Home Page.</p>\n" +
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +	
												"<td colspan='2' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
												"</td>\n" +
											"</tr>\n" +
										"</table>\n" +
									"</div>\n" +
									"</form>\n" +
									"</section>");
						}
						else {
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form class='register' action='' method='POST'>\n" +
									"<div class='form_container'>\n" +
										"<table id='prodDeleteTable'>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:center;'>\n" +
													"<p>" + dbStatus.get("msg") + "</p>\n" +	
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +	
												"<td colspan='2' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
												"</td>\n" +
											"</tr>\n" +
										"</table>\n" +
									"</div>\n" +
									"</form>\n" +
									"</section>");
						}
						
						
						engine.generateHtml("LeftNavigationBar",request);
						engine.generateHtml("Footer",request);
						break;
					}
					
					default:{
						break;
					}
				}
					
				}
			}
			else {
				response.sendRedirect("/csj/Controller/LoginServlet");
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean checkDouble(String var) {
		try {
			final String Digits     = "(\\p{Digit}+)";
			final String HexDigits  = "(\\p{XDigit}+)";
			// an exponent is 'e' or 'E' followed by an optionally 
			// signed decimal integer.
			final String Exp        = "[eE][+-]?"+Digits;
			final String fpRegex    =
			    ("[\\x00-\\x20]*"+ // Optional leading "whitespace"
			    "[+-]?(" +         // Optional sign character
			    "NaN|" +           // "NaN" string
			    "Infinity|" +      // "Infinity" string

			    // A decimal floating-point string representing a finite positive
			    // number without a leading sign has at most five basic pieces:
			    // Digits . Digits ExponentPart FloatTypeSuffix
			    // 
			    // Since this method allows integer-only strings as input
			    // in addition to strings of floating-point literals, the
			    // two sub-patterns below are simplifications of the grammar
			    // productions from the Java Language Specification, 2nd 
			    // edition, section 3.10.2.

			    // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
			    "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

			    // . Digits ExponentPart_opt FloatTypeSuffix_opt
			    "(\\.("+Digits+")("+Exp+")?)|"+

			    // Hexadecimal strings
			    "((" +
			    // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
			    "(0[xX]" + HexDigits + "(\\.)?)|" +

			    // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
			    "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

			    ")[pP][+-]?" + Digits + "))" +
			    "[fFdD]?))" +
			    "[\\x00-\\x20]*");// Optional trailing "whitespace"

			if (Pattern.matches(fpRegex, var)) {
			    //Double.valueOf(myString); Will not throw NumberFormatException
			    return true;
			} 
			else {
			    return false;
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public void displayInvalidPage(PrintWriter pWriter,HttpServletRequest request) {
		try {
			HtmlEngine engine = new HtmlEngine(pWriter);
			displayLogoutHeader(pWriter);
			
			pWriter.println("<div id='body' class='width'>\n" +
					"<section id='content'>\n" +
					"<div style='height:50px;'></div>\n" +
					"<form class='register' action='' method='POST'>\n" +
					"<div class='form_container'>\n" +
						"<table id='prodUpdateTable'>\n" +
							"<tr>\n" +
								"<td colspan='2' style='text-align:center;'>\n" +
									"<p>Invalid input values. Please check the values and try again</p>\n" +
									"<p>Please use the link below to go back to Home Page.</p>\n" +
								"</td>\n" +
							"</tr>\n" +
							"<tr>\n" +	
								"<td colspan='2' style='text-align:right;'>\n" +
									"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
								"</td>\n" +
							"</tr>\n" +
						"</table>\n" +
					"</div>\n" +
					"</form>\n" +
					"</section>");
			
			engine.generateHtml("LeftNavigationBar",request);
			engine.generateHtml("Footer",request);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		try {
			String op = request.getParameter("op");
			
			String relativeWebPath = "/WEB-INF/classes/ProductCatalog.xml";
			String absoluteDiskPath = getServletContext().getRealPath(relativeWebPath);
			
			switch(op) {
				case "addProduct":{
					String productType = request.getParameter("productType");
					String retailer = request.getParameter("retailer");
					String id = request.getParameter("productID");
					String name = request.getParameter("productName");
					String image = request.getParameter("productImage");
					File f = new File(image);
					image = "/images/" + f.getName();
					String condition = request.getParameter("productCondition");
					String price = request.getParameter("price");
					String manufacturer = request.getParameter("manufacturer");
					String discount = request.getParameter("discount");
					String[] accessoryList = new String[10];
					if(productType!="accessories") {
						accessoryList[0] = request.getParameter("accessory1");
						accessoryList[1] = request.getParameter("accessory2");
						accessoryList[2] = request.getParameter("accessory3");
						accessoryList[3] = request.getParameter("accessory4");
						accessoryList[4] = request.getParameter("accessory5");
						accessoryList[5] = request.getParameter("accessory6");
						accessoryList[6] = request.getParameter("accessory7");
					}
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					
					if(productType!=null && productType!="selectType" && retailer!=null && id!=null && name !=null && image!=null && condition!=null && price!=null && manufacturer!=null && discount!=null) {
						if((checkDouble(price)==true) && (checkDouble(discount)==true))
						{
							HashMap<String, String> dbStatus = new HashMap<String, String>();
							dbStatus = dbObj.checkDBConnection();
							if(dbStatus.get("status").equals("true")) {
								if(productType=="accessories") {
									dbObj.addProduct(id, productType, retailer, name, image, condition, price, manufacturer, discount, null);
								}
								else {								
									dbObj.addProduct(id, productType, retailer, name, image, condition, price, manufacturer, discount, accessoryList);
								}
								
								HtmlEngine engine = new HtmlEngine(pWriter);
								displayLogoutHeader(pWriter);
								
								pWriter.println("<div id='body' class='width'>\n" +
										"<section id='content'>\n" +
										"<div style='height:50px;'></div>\n" +
										"<form class='register' action='' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='prodAddTable'>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:center;'>\n" +
														"<p>Product - " + id +" successfully added.</p>\n" +
														"<p>Please use the link below to go back to Home Page.</p>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='2' style='text-align:right;'>\n" +
														"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
													"</td>\n" +
												"</tr>\n" +
											"</table>\n" +
										"</div>\n" +
										"</form>\n" +
										"</section>");
								
								engine.generateHtml("LeftNavigationBar",request);
								engine.generateHtml("Footer",request);
							}
							else {
								HtmlEngine engine = new HtmlEngine(pWriter);
								displayLogoutHeader(pWriter);
								
								pWriter.println("<div id='body' class='width'>\n" +
										"<section id='content'>\n" +
										"<div style='height:50px;'></div>\n" +
										"<form class='register' action='' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='prodAddTable'>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:center;'>\n" +
														"<p>" + dbStatus.get("msg") + "</p>\n" +															
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='2' style='text-align:right;'>\n" +
														"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
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
						else {
							//Print Invalid values for price or discount
							displayInvalidPage(pWriter,request);
						}
					}
					else
					{
						//Print Invalid values for price or discount
						displayInvalidPage(pWriter,request);
					}
					break;
				}
				case "updateProduct": {
					String productType = request.getParameter("productType");
					String retailer = request.getParameter("retailer");
					String oldID = request.getParameter("oldID");
					String newID = request.getParameter("productID");
					String name = request.getParameter("productName");
					String image = request.getParameter("productImage");
					File f = new File(image);
					image = "/images/" + f.getName();
					String condition = request.getParameter("productCondition");
					String price = request.getParameter("price");
					String manufacturer = request.getParameter("manufacturer");
					String discount = request.getParameter("discount");
					
					String[] accessoryList = new String[10];
					if(productType!="accessories") {
						accessoryList[0] = request.getParameter("accessory1");
						accessoryList[1] = request.getParameter("accessory2");
						accessoryList[2] = request.getParameter("accessory3");
						accessoryList[3] = request.getParameter("accessory4");
						accessoryList[4] = request.getParameter("accessory5");
						accessoryList[5] = request.getParameter("accessory6");
						accessoryList[6] = request.getParameter("accessory7");
					}
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					
					if(productType!=null && retailer!=null && oldID!=null && newID!=null && name !=null && image!=null && condition!=null && price!=null && manufacturer!=null && discount!=null) {
						if((checkDouble(price)==true) && (checkDouble(discount)==true)) {
							HashMap<String, String> dbStatus = new HashMap<String, String>();
							dbStatus = dbObj.checkDBConnection();
							if(dbStatus.get("status").equals("true")) {
								if(productType=="accessories") {
									dbObj.updateProduct(oldID, newID, productType, retailer, name, image, condition, price, manufacturer, discount, null);
								}
								else {					
									dbObj.updateProduct(oldID, newID, productType, retailer, name, image, condition, price, manufacturer, discount, accessoryList);
								}
								//dom.ModifyXML(absoluteDiskPath,productType,oldID, newID, retailer, name, image, condition, price, manufacturer, discount,accessoryList);

								HtmlEngine engine = new HtmlEngine(pWriter);
								displayLogoutHeader(pWriter);
								
								pWriter.println("<div id='body' class='width'>\n" +
										"<section id='content'>\n" +
										"<div style='height:50px;'></div>\n" +
										"<form class='register' action='' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='prodUpdateTable'>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:center;'>\n" +
														"<p>Product successfully updated.</p>\n" +
														"<p>Please use the link below to go back to Home Page.</p>\n" +
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='2' style='text-align:right;'>\n" +
														"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
													"</td>\n" +
												"</tr>\n" +
											"</table>\n" +
										"</div>\n" +
										"</form>\n" +
										"</section>");
								
								engine.generateHtml("LeftNavigationBar",request);
								engine.generateHtml("Footer",request);
							}
							else {
								HtmlEngine engine = new HtmlEngine(pWriter);
								displayLogoutHeader(pWriter);
								
								pWriter.println("<div id='body' class='width'>\n" +
										"<section id='content'>\n" +
										"<div style='height:50px;'></div>\n" +
										"<form class='register' action='' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='prodUpdateTable'>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:center;'>\n" +
														"<p>" + dbStatus.get("msg") + "</p>\n" +															
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +	
													"<td colspan='2' style='text-align:right;'>\n" +
														"<a href='/csj/Controller/LoginServlet?op=ManagerHome' method='get'>Manager Home</a>\n" +
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
						else {
							//Print Invalid values for price or discount
							displayInvalidPage(pWriter,request);
						}
					}
					else
					{
						//Print Invalid values for price or discount
						displayInvalidPage(pWriter,request);
					}
					break;
				}
				default:{
					break;
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}