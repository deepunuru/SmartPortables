package Controller;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import javax.servlet.*;
import javax.servlet.http.*;
import Controller.HtmlEngine;
import JavaBeans.Orders;
import JavaBeans.Review;
import DataAccess.MongoDBDataStoreUtilities;
import DataAccess.MySQLDataStoreUtilities;

public class ProductReview extends HttpServlet {
	MongoDBDataStoreUtilities mDBObj;
	
	public ProductReview() {
		mDBObj = new MongoDBDataStoreUtilities();
		/*for(int i=0;i<30;i++) {
			mDBObj.loadSampleReviews();
		}*/		
		//mDBObj.getMaxZipCodes();
	}
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		try {
			String name = request.getParameter("name");
			String type = request.getParameter("type");
			String op = request.getParameter("op");
			if(op.equals("writeReview")) {
				response.setContentType("text/html");
				PrintWriter pWriter = response.getWriter();
				HtmlEngine engine = new HtmlEngine(pWriter);
				engine.getMatchingHeader(request,"productreview");
				
				pWriter.println("<div id='body' class='width'>\n" +
						"<section id='content'>\n" +
						"<form style='width: 80%;margin: 0 auto;height: 50%;border: 1px solid #ccc;border-radius: 15px;' action='/csj/Controller/ProductReview' method='POST'>\n" +
							"<div class='form_container'>\n" +
							"<br />\n" +
							"<table id='productReviewTable'>\n" +
								"<tr>\n" +	
									"<th colspan='2'><div align='center'>Product Review Form</div></th>\n" +
								"</tr>\n" +
								"<tr>\n" +	
									"<td colspan='2'><p></p></td>\n" +
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
										"<label for='ProductName' style='font-size:small'>Product Model Name : </label>\n" +
									"</td>\n" +
									"<td style='text-align:center;'>\n" +
										"<select name='productName' id='productName' style='width:200px;'>\n" +
											"<option value='selectProduct'>select Product</option>\n" +
										"</select>\n" +
									"</td>\n" +
								"</tr>\n" +	
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='ProductPrice' style='font-size:small'>Product Price : </label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='text' id='productPrice' style='width:300px' name='productPrice' required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='Retailer' style='font-size:small'>Retailer Name : </label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='text' id='retailer' style='width:300px' name='retailer' required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='ZipCode' style='font-size:small'>Retailer ZipCode : </label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='text' id='zipCode' style='width:300px' name='zipCode' required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='City' style='font-size:small'>Retailer City : </label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='text' id='city' style='width:300px' name='city' required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='State' style='font-size:small'>Retailer State : </label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='text' id='state' style='width:300px' name='state' required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='ProductOnSale' style='font-size:small'>Product On Sale : </label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='radio' name='productOnSale' value='Yes'/> Yes<br>\n" + 
										"<input type='radio' name='productOnSale' value='No'/> No<br>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='Manufacturer' style='font-size:small'>Manufacturer : </label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='text' id='manufacturer' style='width:300px' name='manufacturer' required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='Rebate' style='font-size:small'>Manufacturer Rebate : </label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='radio' name='rebate' value='Yes'/> Yes<br>\n" + 
										"<input type='radio' name='rebate' value='No'/> No<br>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='Username' style='font-size:small'>UserID : </label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='text' id='username' style='width:300px;' name='username' required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='Age' style='font-size:small'>UserAge : </label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='text' id='age' style='width:300px' name='age' required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='Gender' style='font-size:small'>UserGender : </label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='text' id='gender' style='width:300px' name='gender' required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='Occupation' style='font-size:small'>UserOccupation : </label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='text' id='occupation' style='width:300px' name='occupation' required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='ReviewRating' style='font-size:small'>Review Rating : </label>\n" +
									"</td>\n" +
									"<td style='text-align:left;'>\n" +
										"<input type='text' id='reviewRating' style='width:300px' name='reviewRating' required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='ReviewDate' style='font-size:small'>Review Date : </label>\n" +
									"</td>\n" +
									"<td style='text-align:right;'>\n" +
										"<input type='date' id='reviewDate' style='width:300px' name='reviewDate' required/>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td style='text-align:right;'>\n" +
										"<label for='ReviewText' style='font-size:small'>Review Text : </label>\n" +
									"</td>\n" +
									"<td style='text-align:center;'>\n" +
										"<textarea cols='40' rows='6' name='reviewText' id='reviewText' required></textarea>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td colspan='2' style='text-align:center;'>\n" +
										"<button name='sbmtButton' type='submit' style='width:80px;'>Submit</button>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
								"<td colspan='2' style='text-align:right;'>\n" +
									"<a href='/csj/Controller/HomeServlet?var=products' method='get'>Products</a>\n" +
								"</td>\n" +
							"</tr>\n" +
							"</table>\n" +
							"</div>\n" +
						"</form>\n" +
						"</section>");
				
				engine.generateHtml("LeftNavigationBar",request);
				engine.generateHtml("Footer",request);
			}
			else if(op.equals("viewReviews")) {
				response.setContentType("text/html");
				PrintWriter pWriter = response.getWriter();
				HtmlEngine engine = new HtmlEngine(pWriter);
				engine.getMatchingHeader(request,"productreview");
				
				pWriter.println("<div id='body' class='width'>\n" +
						"<section id='content'>\n" +
						"<div style='height:50px;'></div>\n" +
						"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
							"<div class='form_container'>\n" +
								"<table id='ReviewListTable'>\n" +
									"<tr>\n" +	
										"<th colspan='4'><div align='center'>Review List</div></th>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td colspan='4' style='text-align:center;'>\n" +
										"</td>\n" +
									"</tr>");
				
				HashMap<String, String> dbStatus = new HashMap<String, String>();
				dbStatus = mDBObj.checkMongoDB();
				if(dbStatus.get("status").equals("true")) {
					HashMap<String, Review> reviewMap = mDBObj.getReviews(name);
					if(reviewMap==null || reviewMap.isEmpty()) {
						pWriter.println("<tr>\n" +	
								"<td colspan='4' style='text-align:center;'>\n" +
									"<label for='orderStatusLbl'>No Reviews to Display</label>\n" +
								"</td>\n" +
							"</tr>");
					}
					else {
						Review rev = new Review();
						for(Map.Entry<String, Review> r : reviewMap.entrySet()) {
							rev = r.getValue();
							pWriter.println("<tr style='border: 1px solid #ccc;border-radius: 2px;'>\n" +
									"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<p>Product Name: " + rev.getProductName() + "</p>\n" +
									"</td>\n" +
									"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<p>Product Type: " + rev.getProductType() + "</p>\n" +
									"</td>\n" +
									"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<p>Username: " + rev.getUsername() + "</p>\n" +
									"</td>\n" +
									"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<p>ZipCode: " + rev.getZipCode() + "</p>\n" +
									"</td>\n" +
									"</tr>\n" +
									"<tr style='border: 1px solid #ccc;border-radius: 2px;'>\n" +
									"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +									
										"<p>Review Rating: " + String.valueOf(rev.getReviewRating()) + "</p>\n" +
									"</td>\n" +
									"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<p>Review Date: " + rev.getReviewDate() + "</p>\n" +
									"</td>\n" +
									"<td colspan='4' style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<p>Review Text: " + rev.getReviewText() + "</p>\n" +
									"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +
										"<td colspan='4'>\n" +
											"<p></p>\n" +
										"</td>\n" +
									"</tr>");		
						}
					}
				}
				else {
					pWriter.println("<tr>\n" +	
							"<td colspan='4' style='text-align:center;'>\n" +
								"<label for='orderStatusLbl'>" + dbStatus.get("msg") + "</label>\n" +
							"</td>\n" +
						"</tr>");
				}
				
				
				pWriter.println("<tr>\n" +
							"<td colspan='4' style='text-align:right;'>\n" +
								"<a href='/csj/Controller/HomeServlet?var=products' method='get'>Products</a>\n" +
							"</td>\n" +
					"</tr>\n" +
				"</table>\n" +
				"</div>\n" +
			"</form>\n" +
		"</section>");
				
				engine.generateHtml("LeftNavigationBar",request);
				engine.generateHtml("Footer",request);
			}
			else if(op.equals("trendingProducts")) {
				response.setContentType("text/html");
				PrintWriter pWriter = response.getWriter();
				HtmlEngine engine = new HtmlEngine(pWriter);
				engine.generateHtml("Header",request);
				
				pWriter.println("<div id='body' class='width'>\n" +
						"<section id='content'>\n" +
						"<div style='height:50px;'></div>\n" +
						"<form class='register' action='' method='POST'>\n" +
							"<div class='form_container'>\n" +
								"<table id='TrendingProductsTable'>\n" +
									"<tr>\n" +
										"<td colspan='2' style='text-align:center;'>\n" +
											"<p><h2>Trending Products</h2></p>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td colspan='2' style='text-align:center;'>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +
									"<td colspan='2' style='text-align:center;'>\n" +
										"<p><a href='/csj/Controller/TrendingServlet?task=mostLikedProducts' method='GET'>Top 5 Most Liked Products</a></p>\n" +
										"<br />\n" +
										"<p><a href='/csj/Controller/TrendingServlet?task=mostSoldZipCodes' method='GET'>Top 5 Zip-Codes with Maximum number of Sold Products</a></p>\n" +
										"<br />\n" +
										"<p><a href='/csj/Controller/TrendingServlet?task=mostSoldProducts' method='GET'>Top 5 Most Sold Products Irrespective of Rating</a></p>\n" +
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
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		try {
			if(checkDouble(request.getParameter("productPrice")) && isInteger(request.getParameter("zipCode")) && isInteger(request.getParameter("age")) && isInteger(request.getParameter("reviewRating"))) {
				double price = Double.parseDouble(request.getParameter("productPrice"));
				//Store review details
				boolean bSale = true;
				boolean bRebate = true;
				String productOnSale = request.getParameter("productOnSale");
				String rebate = request.getParameter("rebate");
				if(productOnSale.equals("Yes")) {
					bSale = true;
				}
				else if(productOnSale.equals("No")) {
					bSale = false;
				}
				if(rebate.equals("Yes")) {
					bRebate = true;
				}
				else if(rebate.equals("No")) {
					bRebate = false;
				}
				Review review = new Review(request.getParameter("productName"),request.getParameter("productType"),price,request.getParameter("retailer"),Integer.parseInt(request.getParameter("zipCode")),request.getParameter("city"),request.getParameter("state"),bSale,request.getParameter("manufacturer"),bRebate,request.getParameter("username"),Integer.parseInt(request.getParameter("age")),request.getParameter("gender"),request.getParameter("occupation"),Integer.parseInt(request.getParameter("reviewRating")),request.getParameter("reviewDate"),request.getParameter("reviewText"));
				
				response.setContentType("text/html");
				PrintWriter pWriter = response.getWriter();
				HtmlEngine engine = new HtmlEngine(pWriter);
				engine.getMatchingHeader(request,"productreview");
				
				HashMap<String, String> dbStatus = new HashMap<String, String>();
				dbStatus = mDBObj.checkMongoDB();
				if(dbStatus.get("status").equals("true")) {
					mDBObj.writeReview(review);
					pWriter.println("<div id='body' class='width'>\n" +
							"<section id='content'>\n" +
							"<div style='height:50px;'></div>\n" +
							"<form style='width: 70%;margin: 0 auto;height: 50%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
							"<div class='form_container'>\n" +
								"<table id='ProductReviewTable'>\n" +
									"<tr>\n" +
										"<td colspan='2' style='text-align:center;'>\n" +
											"<p>Review for Product - " + request.getParameter("productName") +" submitted successfully.</p>\n" +
											"<p>Please use the link below to go back to Home Page.</p>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +
										"<td colspan='2' style='text-align:right;'>\n" +
											"<a href='/csj/Controller/HomeServlet?var=products' method='get'>Products</a>\n" +
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
							"<form style='width: 70%;margin: 0 auto;height: 50%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
							"<div class='form_container'>\n" +
								"<table id='ProductReviewTable'>\n" +
									"<tr>\n" +
										"<td colspan='2' style='text-align:center;'>\n" +
											"<p>" + dbStatus.get("msg") + "</p>\n" +											
										"</td>\n" +
									"</tr>\n" +
									"<tr>\n" +
										"<td colspan='2' style='text-align:right;'>\n" +
											"<a href='/csj/Controller/HomeServlet?var=products' method='get'>Products</a>\n" +
										"</td>\n" +
									"</tr>\n" +
								"</table>\n" +
							"</div>\n" +
							"</form>\n" +
							"</section>");
				}
				engine.generateHtml("LeftNavigationBar",request);
				engine.generateHtml("Footer",request);
			}
			else {
				response.setContentType("text/html");
				PrintWriter pWriter = response.getWriter();
				HtmlEngine engine = new HtmlEngine(pWriter);
				engine.getMatchingHeader(request,"productreview");
				
				pWriter.println("<div id='body' class='width'>\n" +
						"<section id='content'>\n" +
						"<div style='height:50px;'></div>\n" +
						"<form style='width: 70%;margin: 0 auto;height: 50%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
						"<div class='form_container'>\n" +
							"<table id='ProductReviewTable'>\n" +
								"<tr>\n" +
									"<td colspan='2' style='text-align:center;'>\n" +
										"<p>Invalid numeric input received. Please enter valid values and try again.</p>\n" +
										"<p>Please use the link below to go back to Home Page.</p>\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td colspan='2' style='text-align:right;'>\n" +
										"<a href='/csj/Controller/HomeServlet?var=products' method='get'>Products</a>\n" +
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
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}