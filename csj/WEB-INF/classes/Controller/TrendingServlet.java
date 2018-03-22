package Controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import Controller.HtmlEngine;
import JavaBeans.Review;
import DataAccess.MongoDBDataStoreUtilities;

public class TrendingServlet extends HttpServlet {
	MongoDBDataStoreUtilities mDBObj;
	
	public TrendingServlet() {
		mDBObj = new MongoDBDataStoreUtilities();
	}
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String task = request.getParameter("task");
		try {
			switch(task) {
			case "mostLikedProducts":{
				response.setContentType("text/html");
				PrintWriter pWriter = response.getWriter();
				HtmlEngine engine = new HtmlEngine(pWriter);
				engine.generateHtml("Header",request);
				
				pWriter.println("<div id='body' class='width'>\n" +
						"<section id='content'>\n" +
						"<div style='height:50px;'></div>\n" +
						"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
							"<div class='form_container'>\n" +
								"<table id='ProductListTable'>\n" +
									"<tr>\n" +	
										"<th colspan='4'><div align='center'>Top Five Most Liked Products</div></th>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td colspan='4' style='text-align:center;'>\n" +
										"</td>\n" +
									"</tr>");
				
				//HashMap<String, Review> reviewMap = mDBObj.getMostLikedProducts();
				HashMap<String, String> dbStatus = new HashMap<String, String>();
				dbStatus = mDBObj.checkMongoDB();
				if(dbStatus.get("status").equals("true")) {
					List<Review> reviewsList = mDBObj.getMostLikedProducts();
					if(reviewsList==null || reviewsList.isEmpty()) {
						pWriter.println("<tr>\n" +	
								"<td colspan='4' style='text-align:center;'>\n" +
									"<label for='orderStatusLbl'>No Products to Display</label>\n" +
								"</td>\n" +
							"</tr>");
					}
					else {
						for(Review r: reviewsList) {
							pWriter.println("<tr style='border: 1px solid #ccc;border-radius: 2px;'>\n" +
									"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<p>Product Name: " + r.getProductName() + "</p>\n" +
									"</td>\n" +
									"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<p>Product Type: " + r.getProductType() + "</p>\n" +
									"</td>\n" +
									"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<p>Username: " + r.getUsername() + "</p>\n" +
									"</td>\n" +
									"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<p>ZipCode: " + r.getZipCode() + "</p>\n" +
									"</td>\n" +
									"</tr>\n" +
									"<tr style='border: 1px solid #ccc;border-radius: 2px;'>\n" +
									"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +									
										"<p>Review Rating: " + String.valueOf(r.getReviewRating()) + "</p>\n" +
									"</td>\n" +
									"<td style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<p>Review Date: " + r.getReviewDate() + "</p>\n" +
									"</td>\n" +
									"<td colspan='2' style='text-align:left;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<p>Review Text: " + r.getReviewText() + "</p>\n" +
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
								"<a href='/csj/Controller/HomeServlet?var=home' method='get'>Home Page</a>\n" +
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
			case "mostSoldZipCodes":{
				response.setContentType("text/html");
				PrintWriter pWriter = response.getWriter();
				HtmlEngine engine = new HtmlEngine(pWriter);
				engine.generateHtml("Header",request);
				
				pWriter.println("<div id='body' class='width'>\n" +
						"<section id='content'>\n" +
						"<div style='height:50px;'></div>\n" +
						"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
							"<div class='form_container'>\n" +
								"<table id='ProductListTable'>\n" +
									"<tr>\n" +	
										"<th colspan='2'><div align='center'>Top Five Zip Codes with Maximum number of Products Sold</div></th>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td colspan='2' style='text-align:center;'>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr style='border: 1px solid #ccc;border-radius: 2px;'>\n" +
									"<th style='text-align:center;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<div align='center'>Zip Code</div>\n" +
									"</th>\n" +
									"<th style='text-align:center;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<div align='center'>Products Sold</div>\n" +
									"</th>\n" +
								"</tr>");
				
				HashMap<String, String> dbStatus = new HashMap<String, String>();
				dbStatus = mDBObj.checkMongoDB();
				if(dbStatus.get("status").equals("true")) {
					int[][] zipCodeList = mDBObj.getMaxZipCodes();
					if(zipCodeList==null) {
						pWriter.println("<tr>\n" +	
								"<td colspan='2' style='text-align:center;'>\n" +
									"<label for='orderStatusLbl'>No Zip Codes to Display</label>\n" +
								"</td>\n" +
							"</tr>");
					}
					else {		
						for(int i=0;i<5;i++) {
							pWriter.println("<tr style='border: 1px solid #ccc;border-radius: 2px;'>\n" +
									"<td style='text-align:center;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<p>" + String.valueOf(zipCodeList[i][0]) + "</p>\n" +
									"</td>\n" +
									"<td style='text-align:center;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<p>" + String.valueOf(zipCodeList[i][1]) + "</p>\n" +
									"</td>\n" +
									"</tr>");
						}
					}
				}
				else {
					pWriter.println("<tr>\n" +	
							"<td colspan='2' style='text-align:center;'>\n" +
								"<label for='orderStatusLbl'>" + dbStatus.get("msg") + "</label>\n" +
							"</td>\n" +
						"</tr>");
				}
				
				
				pWriter.println("<tr>\n" +
							"<td colspan='2' style='text-align:right;'>\n" +
								"<a href='/csj/Controller/HomeServlet?var=home' method='get'>Home Page</a>\n" +
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
			case "mostSoldProducts":{
				response.setContentType("text/html");
				PrintWriter pWriter = response.getWriter();
				HtmlEngine engine = new HtmlEngine(pWriter);
				engine.generateHtml("Header",request);
				
				pWriter.println("<div id='body' class='width'>\n" +
						"<section id='content'>\n" +
						"<div style='height:50px;'></div>\n" +
						"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;' action='' method='get'>\n" +
							"<div class='form_container'>\n" +
								"<table id='ProductListTable'>\n" +
									"<tr>\n" +	
										"<th colspan='2'><div align='center'>Top Five Most Products Sold Regardless of Rating</div></th>\n" +
									"</tr>\n" +
									"<tr>\n" +	
										"<td colspan='2' style='text-align:center;'>\n" +
										"</td>\n" +
									"</tr>\n" +
									"<tr style='border: 1px solid #ccc;border-radius: 2px;'>\n" +
									"<th style='text-align:center;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<div align='center'>Product Name</div>\n" +
									"</th>\n" +
									"<th style='text-align:center;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<div align='center'>Products Sold</div>\n" +
									"</th>\n" +
								"</tr>");
				
				HashMap<String, String> dbStatus = new HashMap<String, String>();
				dbStatus = mDBObj.checkMongoDB();
				if(dbStatus.get("status").equals("true")) {
					String[][] mostSoldList = mDBObj.getMostSoldProducts();
					if(mostSoldList==null) {
						pWriter.println("<tr>\n" +	
								"<td colspan='2' style='text-align:center;'>\n" +
									"<label for='orderStatusLbl'>No Products to Display</label>\n" +
								"</td>\n" +
							"</tr>");
					}
					else {	
						for(int i=0;i<5;i++) {
							pWriter.println("<tr style='border: 1px solid #ccc;border-radius: 2px;'>\n" +
									"<td style='text-align:center;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<p>" + mostSoldList[i][0] + "</p>\n" +
									"</td>\n" +
									"<td style='text-align:center;border: 1px solid #ccc;border-radius: 2px;'>\n" +
										"<p>" + mostSoldList[i][1] + "</p>\n" +
									"</td>\n" +
									"</tr>");
						}
					}
				}
				else {
					pWriter.println("<tr>\n" +	
							"<td colspan='2' style='text-align:center;'>\n" +
								"<label for='orderStatusLbl'>" + dbStatus.get("msg") + "</label>\n" +
							"</td>\n" +
						"</tr>");
				}
				pWriter.println("<tr>\n" +
							"<td colspan='2' style='text-align:right;'>\n" +
								"<a href='/csj/Controller/HomeServlet?var=home' method='get'>Home Page</a>\n" +
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