package Controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import JavaBeans.Product;
import JavaBeans.ShoppingCart;

public class HtmlEngine extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter pWriter;
	String path;
	DealMatches dm;
	
	public HtmlEngine(PrintWriter writer) {
		super();
		dm = new DealMatches();
		pWriter = writer;
	}
	
	
	public void getMatchingHeader(HttpServletRequest request,String pageType){
		HttpSession userSession = request.getSession(true);
				String username = (String)userSession.getAttribute("customerKey");
				if(username!=null) {
					ShoppingCart cart = (ShoppingCart) userSession.getAttribute("cart");
					int size = cart.getCartSize();
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
			            "<script type='text/javascript' src='/csj/productListScript.js'></script>\n" +
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
			            			"<ul>");
									if(pageType=="products"){
										pWriter.println("<li class='start'><a href='/csj/Controller/HomeServlet?var=home' method='get'>Home</a></li>\n" +
			            				"<li class='selected'><a href='/csj/Controller/HomeServlet?var=products' method='get'>Products</a></li>\n" +
			            				"<li><a href='/csj/Controller/HomeServlet?var=accessories' method='get'>Accessories</a></li>\n" +
			            				"<li><a href='/csj/Controller/HomeServlet?var=contactus' method='get'>Contact Us</a></li>\n" +
										"<li class='end'><a href='/csj/Controller/ProductReview' method='get'>Product Review</a></li>");
									}
									if(pageType=="accessories"){
										pWriter.println("<li class='start'><a href='/csj/Controller/HomeServlet?var=home' method='get'>Home</a></li>\n" +
			            				"<li><a href='/csj/Controller/HomeServlet?var=products' method='get'>Products</a></li>\n" +
			            				"<li class='selected'><a href='/csj/Controller/HomeServlet?var=accessories' method='get'>Accessories</a></li>\n" +
			            				"<li><a href='/csj/Controller/HomeServlet?var=contactus' method='get'>Contact Us</a></li>\n" +
										"<li class='end'><a href='/csj/Controller/ProductReview' method='get'>Product Review</a></li>");
									}
									if(pageType=="contactus"){
										pWriter.println("<li class='start'><a href='/csj/Controller/HomeServlet?var=home' method='get'>Home</a></li>\n" +
			            				"<li><a href='/csj/Controller/HomeServlet?var=products' method='get'>Products</a></li>\n" +
			            				"<li><a href='/csj/Controller/HomeServlet?var=accessories' method='get'>Accessories</a></li>\n" +
										"<li class='selected'><a href='/csj/Controller/HomeServlet?var=contactus' method='get'>Contact Us</a></li>\n" +
										"<li class='end'><a href='/csj/Controller/ProductReview' method='get'>Product Review</a></li>");
									}
									if(pageType=="cart"){
										pWriter.println("<li class='start'><a href='/csj/Controller/HomeServlet?var=home' method='get'>Home</a></li>\n" +
			            				"<li><a href='/csj/Controller/HomeServlet?var=products' method='get'>Products</a></li>\n" +
			            				"<li><a href='/csj/Controller/HomeServlet?var=accessories' method='get'>Accessories</a></li>\n" +
			            				"<li><a href='/csj/Controller/HomeServlet?var=contactus' method='get'>Contact Us</a></li>\n" +
										"<li class='end'><a href='/csj/Controller/ProductReview' method='get'>Product Review</a></li>");
									}
									if(pageType=="productreview"){
										pWriter.println("<li class='start'><a href='/csj/Controller/HomeServlet?var=home' method='get'>Home</a></li>\n" +
			            				"<li><a href='/csj/Controller/HomeServlet?var=products' method='get'>Products</a></li>\n" +
			            				"<li><a href='/csj/Controller/HomeServlet?var=accessories' method='get'>Accessories</a></li>\n" +
			            				"<li><a href='/csj/Controller/HomeServlet?var=contactus' method='get'>Contact Us</a></li>\n" +
										"<li class='end selected'><a href='/csj/Controller/ProductReview' method='get'>Product Review</a></li>");
									}
			            			pWriter.println("</ul>\n" +
			            			"</td>\n" +
			            			"<td style='width:75px;'>\n" +
			            			"<ul>");
										if(pageType=="cart"){
										pWriter.println("<li class='selected' style='text-align:right;'><a href='/csj/Controller/CartServlet' method='get'>Cart(" + String.valueOf(size) + ")</a></li>");
										}
										else{
											pWriter.println("<li style='text-align:right;'><a href='/csj/Controller/CartServlet' method='get'>Cart(" + String.valueOf(size) + ")</a></li>");
										}	
			            			pWriter.println("</ul>\n" +
			            			"</td>\n" +
									"</tr>\n" +
			            		"</table>\n" +
			            		"</div>\n" +
							"</nav>");
				}
				else {
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
			            "<script type='text/javascript' src='/csj/productListScript.js'></script>\n" +
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
			            		"<a href='/csj/Controller/RegisterServlet' method='get' class='reg'>New Customer? Start here</a>\n" +
			            		"<a href='/csj/Controller/LoginServlet' method='get' class='login'> Login    </a>\n" +
			            	"</header>\n" +
			            	"<nav>\n" +
			            		"<div class='width'>\n" +
			            		"<table id='navTable'>\n" +
			            		"<tr>\n" +
									"<td style='width:725px'>\n" +
			            			"<ul>");
									if(pageType=="products"){
										pWriter.println("<li class='start'><a href='/csj/Controller/HomeServlet?var=home' method='get'>Home</a></li>\n" +
			            				"<li class='selected'><a href='/csj/Controller/HomeServlet?var=products' method='get'>Products</a></li>\n" +
			            				"<li><a href='/csj/Controller/HomeServlet?var=accessories' method='get'>Accessories</a></li>\n" +
			            				"<li class='end'><a href='/csj/Controller/HomeServlet?var=contactus' method='get'>Contact Us</a></li>");
									}
									if(pageType=="accessories"){
										pWriter.println("<li class='start'><a href='/csj/Controller/HomeServlet?var=home' method='get'>Home</a></li>\n" +
			            				"<li><a href='/csj/Controller/HomeServlet?var=products' method='get'>Products</a></li>\n" +
			            				"<li class='selected'><a href='/csj/Controller/HomeServlet?var=accessories' method='get'>Accessories</a></li>\n" +
			            				"<li class='end'><a href='/csj/Controller/HomeServlet?var=contactus' method='get'>Contact Us</a></li>");
									}
									if(pageType=="contactus"){
										pWriter.println("<li class='start'><a href='/csj/Controller/HomeServlet?var=home' method='get'>Home</a></li>\n" +
			            				"<li><a href='/csj/Controller/HomeServlet?var=products' method='get'>Products</a></li>\n" +
			            				"<li><a href='/csj/Controller/HomeServlet?var=accessories' method='get'>Accessories</a></li>\n" +
			            				"<li class='end selected'><a href='/csj/Controller/HomeServlet?var=contactus' method='get'>Contact Us</a></li>");
									}
									if(pageType=="cart"){
										pWriter.println("<li class='start'><a href='/csj/Controller/HomeServlet?var=home' method='get'>Home</a></li>\n" +
			            				"<li><a href='/csj/Controller/HomeServlet?var=products' method='get'>Products</a></li>\n" +
			            				"<li><a href='/csj/Controller/HomeServlet?var=accessories' method='get'>Accessories</a></li>\n" +
			            				"<li class='end'><a href='/csj/Controller/HomeServlet?var=contactus' method='get'>Contact Us</a></li>");
									}
			            			pWriter.println("</ul>\n" +
			            			"</td>\n" +
			            			"<td style='width:75px;'>\n" +
			            			"<ul>");
			            				if(pageType=="cart"){
										pWriter.println("<li class='selected' style='text-align:right;'><a href='/csj/Controller/CartServlet' method='get'>Cart(0)</a></li>");
										}
										else{
											pWriter.println("<li style='text-align:right;'><a href='/csj/Controller/CartServlet' method='get'>Cart(0)</a></li>");
										}	
			            			pWriter.println("</ul>\n" +
			            			"</td>\n" +
									"</tr>\n" +
			            		"</table>\n" +
			            		"</div>\n" +
							"</nav>");
				}
	}
	
	public void printProduct(String[] imgList,String[] priceList,String[] nameList,String[] idList,PrintWriter pWriter,int k,String type){
		pWriter.println("<img src='/csj/" + imgList[k] + "' style='width:150px;height:150px' alt='" + imgList[k] +"'>\n" +
				"<p><a href='/csj/Controller/DisplayProduct?id=" + idList[k] + "&type=" + type + "' method='get'>" + nameList[k] +"</a>\n" +
				"<br />\n" +
				"Price: $" + priceList[k] +"\n" +
				"<br />\n" +
				"<select id='qnty' onchange='getQuantity()'>\n" + 
				"  <option value='1'>1</option>\n" + 
				"  <option value='2'>2</option>\n" + 
				"  <option value='3'>3</option>\n" + 
				"  <option value='4'>4</option>\n" + 
				"  <option value='5'>5</option>\n" + 
				"  <option value='6'>6</option>\n" + 
				"  <option value='7'>7</option>\n" + 
				"  <option value='8'>8</option>\n" + 
				"  <option value='9'>9</option>\n" + 
				"  <option value='10'>10</option>\n" + 
				"</select>\n" +
				"<br />\n" +
				"<a id='buynowLink' href='/csj/Controller/PortableServlet?id=" + idList[k] + "&type=" + type + "&op=buynow&qnty=1' method='get'>Buy Now</a>\n" +
				"<br />\n" +
				"<a href='/csj/Controller/PortableServlet?id=" + idList[k] + "&type=" + type + "&op=addtocart' method='get'>Add To Cart</a>\n" +
				"<br />\n" +
				"<a id='writeReviewLink' href='/csj/Controller/ProductReview?name=" + nameList[k] + "&type=" + type + "&op=writeReview' method='get'>Write Review</a>\n" +
				"<br />\n" +
				"<a id='viewReviewsLink' href='/csj/Controller/ProductReview?name=" + nameList[k] + "&type=" + type + "&op=viewReviews' method='get'>View Review</a></p>\n" +
				"<br />");
	}
	
	public void generateHtml(String pageComponent,HttpServletRequest request)
	{
		try {
			if(pageComponent == "Header")
			{
				HttpSession userSession = request.getSession(true);
				String username = (String)userSession.getAttribute("customerKey");
				if(username!=null) {
					ShoppingCart cart = (ShoppingCart) userSession.getAttribute("cart");
					int size = cart.getCartSize();
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
			            "<script type='text/javascript' src='/csj/getQuantity.js'></script>\n" +  
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
			            				"<li class='end'><a href='/csj/Controller/HomeServlet?var=contactus' method='get'>Contact Us</a></li>\n" +
			            			"</ul>\n" +
			            			"</td>\n" +
			            			"<td style='width:75px;'>\n" +
			            			"<ul>\n" +
			            				"<li style='text-align:right;'><a href='/csj/Controller/CartServlet' method='get'>Cart(" + String.valueOf(size) + ")</a></li>\n" +
			            			"</ul>\n" +
			            			"</td>\n" +
									"</tr>\n" +
			            		"</table>\n" +
			            		"</div>\n" +
							"</nav>");
				}
				else {
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
			            		"<a href='/csj/Controller/RegisterServlet' method='get' class='reg'>New Customer? Start here</a>\n" +
			            		"<a href='/csj/Controller/LoginServlet' method='get' class='login'> Login    </a>\n" +
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
			            				"<li class='end'><a href='/csj/Controller/HomeServlet?var=contactus' method='get'>Contact Us</a></li>\n" +
			            			"</ul>\n" +
			            			"</td>\n" +
			            			"<td style='width:75px;'>\n" +
			            			"<ul>\n" +
			            				"<li style='text-align:right;'><a href='/csj/Controller/CartServlet' method='get'>Cart(0)</a></li>\n" +
			            			"</ul>\n" +
			            			"</td>\n" +
									"</tr>\n" +
			            		"</table>\n" +
			            		"</div>\n" +
							"</nav>");
				}
		            		
			}
			
			if(pageComponent == "Content")
			{
				pWriter.println("<div id='body' class='width'>\n" +
						"<section id='content'>\n" +
							"<table id='CustHomeTable'>\n" +
								"<tr>\n" +
								"<td style='font-size:small;text-align:center;'>\n" +
										"<img src='/csj/images/watchicon.png' style='width:150px;height:150px' alt='watchicon'>\n" +
										"<br />\n" +
										"<p><a href='/csj/Controller/HomeServlet?var=smartwatches' method='get'>Smart Watches</a></p>\n" +
									"</td>\n" +
									"<td style='font-size:small;text-align:center;'>\n" +
										"<img src='/csj/images/speakericon.png' style='width:150px;height:150px' alt='speakericon'>\n" +
										"<br />\n" +
										"<p><a href='/csj/Controller/HomeServlet?var=speakers' method='get'>Speakers</a></p>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +	
									"<td style='font-size:small;text-align:center;'>\n" +
										"<img src='/csj/images/headphonesicon.png' style='width:150px;height:150px' alt='headphonesicon'>\n" +
										"<br />\n" +
										"<p><a href='/csj/Controller/HomeServlet?var=headphones' method='get'>Headphones</a></p>\n" +
									"</td>\n" +
									"<td style='font-size:small;text-align:center;'>\n" +
										"<img src='/csj/images/phoneicon.png' style='width:150px;height:150px' alt='phoneicon'>\n" +
										"<br />\n" +
										"<p><a href='/csj/Controller/HomeServlet?var=smartphones' method='get'>Smart Phones</a></p>\n" +
									"</td>\n" +								
								"</tr>\n" +
								"<tr>\n" +	
									"<td style='font-size:small;text-align:center;'>\n" +
										"<img src='/csj/images/laptopicon.png' style='width:150px;height:150px' alt='laptopicon'>\n" +
										"<br />\n" +
										"<p><a href='/csj/Controller/HomeServlet?var=laptops' method='get'>Laptops</a></p>\n" +
									"</td>\n" +
									"<td style='font-size:small;text-align:center;'>\n" +
										"<img src='/csj/images/storageicon.jpg' style='width:150px;height:150px' alt='storageicon'>\n" +
										"<br />\n" +
										"<p><a href='/csj/Controller/HomeServlet?var=externalstorage' method='get'>External Storage</a></p>\n" +
									"</td>\n" +								
								"</tr>\n" +
							"</table>");
				String absoluteDiskPath="";
				try {
					absoluteDiskPath = request.getServletContext().getRealPath("/DealMatches.txt");
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
				HashMap<String, Product> deals = dm.readDealMatches(absoluteDiskPath);
				Product tempProduct = new Product();
				if(deals!=null) {
					String[] imgList = new String[30];
					String[] priceList = new String[30];
					String[] nameList = new String[30];
					String[] idList = new String[30];
					if(deals.size()==1 || absoluteDiskPath.equals("") || absoluteDiskPath==null) {
						File f = new File(absoluteDiskPath);
						for(Map.Entry<String, Product> p : deals.entrySet()) {
							if(p.getKey().equals("Invalid File Path") || p.getKey().equals("Product List is null") || p.getKey().equals("MySQL server is not up and running") || p.getKey().equals("No Offers Found")) {
								pWriter.println("<table id='CustHomeTable'>\n" +
										"<tr>\n" +
											"<th><div align='center'>We beat our competitors in all aspects\nPrice-Match Guaranteed</div></th>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td style='font-size:small;text-align:center;'>\n" +
												"<p>" + p.getKey() + "</p>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<th><div align='center'>Deal Matches</div></th>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td style='font-size:small;text-align:center;'>\n" +
												"<p>No Deals Found</p>\n" +
											"</td>\n" +
										"</tr>\n" +
									"</table>");
							}
							else{
								tempProduct = p.getValue();
								imgList[0] = tempProduct.getImage();
								priceList[0] = String.valueOf(tempProduct.getPrice());
								nameList[0] = tempProduct.getName();
								idList[0] = tempProduct.getId();
								pWriter.println("<table id='CustHomeTable'>\n" +
										"<tr>\n" +
											"<th><div align='center'>We beat our competitors in all aspects\nPrice-Match Guaranteed</div></th>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td style='font-size:small;text-align:center;'>\n" +
												"<p>" + p.getKey() + "</p>\n" +
											"</td>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<th><div align='center'>Deal Matches</div></th>\n" +
										"</tr>\n" +
										"<tr>\n" +
											"<td style='font-size:small;text-align:center;padding:10px;'>");
												printProduct(imgList,priceList,nameList,idList,pWriter,0,tempProduct.getType());
							pWriter.println("</td>\n" +
										"</tr>\n" +
									"</table>");
							}
						}
					}
					else {
						String[] dealList = new String[2];
						int i=0;
						pWriter.println("<table id='CustHomeTable'>\n" +
								"<tr>\n" +
									"<th colspan='2'><div align='center'>We beat our competitors in all aspects\nPrice-Match Guaranteed</div></th>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td colspan='2' style='font-size:small;text-align:center;'>");
							System.out.println("Deals Size-" + deals.size());
									if(deals.size()==0) {
										pWriter.println("<p>No Offers Found</p>");
									}
									else {
										for(Map.Entry<String, Product> p : deals.entrySet()) {
											pWriter.println("<p>" + p.getKey() + "</p>");
											dealList[i] = p.getKey();
											i++;
										}
									}
									pWriter.println("</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<th colspan='2'><div align='center'>Deal Matches</div></th>\n" +
								"</tr>\n" +
								"<tr>");
										tempProduct = deals.get(dealList[0]);
										if(tempProduct==null) {
											pWriter.println("<td colspan='2' style='font-size:small;text-align:center;padding:10px;'>\n" +
											"<p>No Deals Found</p>");
										}
										else {
											imgList[0] = tempProduct.getImage();
											priceList[0] = String.valueOf(tempProduct.getPrice());
											nameList[0] = tempProduct.getName();
											idList[0] = tempProduct.getId();
											pWriter.println("<td style='font-size:small;text-align:center;padding:10px;'>");
											printProduct(imgList,priceList,nameList,idList,pWriter,0,tempProduct.getType());
						pWriter.println("</td>\n" +
										"<td style='font-size:small;text-align:center;padding:10px;'>");
											tempProduct = deals.get(dealList[1]);
											imgList[1] = tempProduct.getImage();
											priceList[1] = String.valueOf(tempProduct.getPrice());
											nameList[1] = tempProduct.getName();
											idList[1] = tempProduct.getId();
											printProduct(imgList,priceList,nameList,idList,pWriter,1,tempProduct.getType());
										}
					pWriter.println("</td>\n" +
								"</tr>\n" +
							"</table>");
					}
				}
				pWriter.println("</section>");
			}
			
			if(pageComponent == "LeftNavigationBar")
			{
				pWriter.println("<aside class='sidebar'>\n" +
						"<ul>\n" +
							"<li>\n" +
								"<h4>Categories</h4>\n" +
								"<ul>\n" +
									"<li><a href='/csj/Controller/HomeServlet?var=smartwatches' method='get'>Smart Watches</a></li>\n" +
									"<li><a href='/csj/Controller/HomeServlet?var=speakers' method='get'>Speakers</a></li>\n" +
									"<li><a href='/csj/Controller/HomeServlet?var=headphones' method='get'>Headphones</a></li>\n" +
									"<li><a href='/csj/Controller/HomeServlet?var=smartphones' method='get'>Smart Phones</a></li>\n" +
									"<li><a href='/csj/Controller/HomeServlet?var=laptops' method='get'>Laptops</a></li>\n" +
									"<li><a href='/csj/Controller/HomeServlet?var=externalstorage' method='get'>External Storage</a></li>\n" +
									"<li><a href='/csj/Controller/HomeServlet?var=accessories' method='get'>Accessories</a></li>\n" +
									"<li><a href='/csj/Controller/ProductReview?op=trendingProducts' method='get'>Trending Products</a></li>\n" +
								"</ul>\n" +
							"</li>\n" +
							"<li>\n" +
								"<h4>About us</h4>\n" +
								"<ul>\n" +
									"<li class='text'>\n" +
										"<p style='margin: 0;'>Smart Portables is a shopping portal that allows customers to register a free user account and place orders online. A multi-purpose login page allows customers,Salesman and Managers and login from the same page.<a href='#' class='readmore'>Read More &raquo;</a></p>\n" +
									"</li>\n" +
								"</ul>\n" +
							"</li>\n" +
							"<li>\n" +
								"<h4>Search Products</h4>\n" +
								"<ul>\n" +
									"<li class='text'>\n" +
										"<div name='autofillform'>\n" +
											"<input type='text' name='searchId' value='' id='searchId' onkeyup='doCompletion()' placeholder='search here...' style='padding:5px;float: none;font-size:16px;'/>\n" +
											"<div id='auto-row'>\n" +
												"<table id='complete-table' class='gridtable' style='position:absolute;width:315px;'></table>\n" +
											"</div>\n" +
										"</div>\n" +
									"</li>\n" +
								"</ul>\n" +
							"</li>\n" +
							"<li>\n" +
								"<h4>Helpful Links</h4>\n" +
								"<ul>\n" +
									"<li><a href='https://www.bestbuy.com/' title='BestBuy'>BestBuy</a></li>\n" +
									"<li><a href='https://www.amazon.com/' title='Amazon'>Amazon</a></li>\n" +
									"<li><a href='https://www.target.com/' title='Target'>Target</a></li>\n" +
									"<li><a href='https://www.ebay.com/' title='Ebay'>Ebay</a></li>\n" +
								"</ul>\n" +
							"</li>\n" +
						"</ul>\n" +
					"</aside>\n" +
					"<div class='clear'></div>\n" +	
				"</div>");
			}
			
			if(pageComponent == "Footer")
			{
				pWriter.println("<footer>\n"+
						"<div class='footer-content width' style='width:980px;'>\n" +
							"<ul>\n" +
								"<li><h4>Featured Products</h4></li>\n" +
								"<li><a href='/csj/Controller/HomeServlet?var=smartwatches' method='get'>Smart Watches</a></li>\n" +
								"<li><a href='/csj/Controller/HomeServlet?var=laptops' method='get'>Laptops</a></li>\n" +
							"</ul>\n" +
							"<ul>\n" +
								"<li><h4>Cheap Headphones</h4></li>\n" +
								"<li><a href='/csj/Controller/HomeServlet?var=headphones' method='get'>Headphones</a></li>\n" +
								"<li><a href='/csj/Controller/HomeServlet?var=accessories' method='get'>Accessories</a></li>\n" +
							"</ul>\n" +
							"<ul class='endfooter'>\n" +
								"<li><h4>Top Quality Speakers</h4></li>\n" +
								"<li><a href='/csj/Controller/HomeServlet?var=speakers' method='get'>Speakers</a></li>\n" +
								"<li><a href='/csj/Controller/HomeServlet?var=accessories' method='get'>Accessories</a></li>\n" +
							"</ul>\n" +
							"<div class='clear'></div>\n" +
						"</div>\n" +
						"<div class='footer-bottom'>\n" +
							"<div id='copyrightText'>\n" +
							"</div>\n" +
							"<p><a href='#' class='ftItem-1'>Sitemap</a>\n" +
							"<a href='#' class='ftItem-2'>Privacy Policy</a>\n" +
							"<a href='#' class='ftItem-3'>Terms of Use</a>\n" +
							"<a href='#' target='_blank' class='ftItem-4'>Terms &amp; Conditions</a> &copy; 2017 Smart Portables. All Rights Reserved. Site Developed by Sandeep Nurukurthi </p>\n" +
						"</div>\n" +
					"</footer>\n" +
				"</div>\n" +
			"</body>\n" +
			"</html>");
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}	
	}
}