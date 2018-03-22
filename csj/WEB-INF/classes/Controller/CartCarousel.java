package Controller;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import Controller.HtmlEngine;
import JavaBeans.*;
import DataAccess.MySQLDataStoreUtilities;

public class CartCarousel extends HttpServlet {
	
	public CartCarousel() {
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			HttpSession userSession = request.getSession(true);
			String username = (String)userSession.getAttribute("customerKey");
			ShoppingCart cart = (ShoppingCart) userSession.getAttribute("cart");
			if(cart!=null) {
				String id = request.getParameter("id");
				String type= request.getParameter("type");
				
				//Code to addtocart
				Vector<String> prodList = cart.getProductList();				
				int size = cart.getCartSize();
				int newSize = size + 1;
				if(prodList != null) {
					prodList.add(id);
				}
				else {
					prodList = new Vector<String>();
					prodList.add(id);
				}
				
				if(size==0) {
					prodList = null;
				}
				cart.setProductList(prodList);
				cart.setCartSize(newSize);
				cart.setUsername(username);
				userSession.setAttribute("cart", cart);
				//display page
				response.sendRedirect("/csj/Controller/CartServlet");
			}
			else {
				response.sendRedirect("/csj/Controller/LoginServlet");
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
	}
	
	public void displayOrderDetails(PrintWriter pWriter,Orders order) {
		String[] prodID = order.getProductID();
		String[] prodType = order.getProductType();
		int[] qnty = order.getQuantity();
		//content
		pWriter.println("<div id='body' class='width'>\n" +
				"<section id='content'>\n" +
				"<div style='height:50px;'></div>\n" +
				"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;'>\n" +
					"<div class='form_container'>\n" +
						"<br />\n" +
						"<table id='OrderDetails'>\n" +
							"<tr>\n" +	
								"<th colspan='3'><div align='center'>Order Details</div></th>\n" +
							"</tr>\n" +
							"<tr>\n" +	
								"<td colspan='3'><p></p></td>\n" +
							"</tr>\n" +
							"<tr>\n" +
								"<td style='text-align:left;vertical-align: text-top;border: 1px solid #ccc;border-radius: 5px;'>\n" +
									"<p><label for='ShippingAddress' style='color: #fff;background: #799AC0 none repeat-x scroll left top;border-radius: 5px;'>Shipping Address</label>\n" +
									"<br />\n" +
									"<label for='Address1' style='font-size:small'>" + order.getAddress1() + "</label>\n" +
									"<br />\n" +
									"<label for='Address2' style='font-size:small'>" + order.getAddress2() + "</label>\n" +
									"<br />\n" +
									"<label for='City' style='font-size:small'>" + order.getCity() + "</label>\n" +
									"<br />\n" +
									"<label for='State' style='font-size:small'>" + order.getState() + "</label>\n" +
									"<br />\n" +
									"<label for='PostalCode' style='font-size:small'>" + String.valueOf(order.getZipCode()) + "</label>\n" +
									"<br />\n" +
									"<label for='Country' style='font-size:small'>" + order.getCountry() + "</label></p>\n" +
									"<br />\n" +
									"<p><label for='MobileNumberlbl' style='font-size:small'>MobileNumber :</label>\n" +
									"<label for='MobileNumber' style='font-size:small'> " + String.valueOf(order.getMobileNumber()) + "</label></p>\n" +
								"</td>\n" +
								"<td style='text-align:left;vertical-align: text-top;border: 1px solid #ccc;border-radius: 5px;'>\n" +
									"<label for='confirmationNumberlbl' style='font-size:small'>Confirmation Number : " + order.getConfirmationNumber() + "</label>\n" +
									"<br />\n" +
									"<label for='orderDatelbl' style='font-size:small'>Order Date : " + order.getOrderDate() + "</label>\n" +
									"<br />\n" +
									"<label for='deliveryDatelbl' style='font-size:small'>Delivery Date : " + order.getDeliveryDate() + "</label>\n" +
									"<br />\n" +
									"<label for='orderStatuslbl' style='font-size:small'>Order Status : In Progress</label>\n" +
									"<br />\n" +
									"<label for='emailAddresslbl' style='font-size:small'>Email Address : " + order.getEmailAddress() + "</label>\n" +
								"</td>\n" +
								"<td style='text-align:left;vertical-align: text-top;border: 1px solid #ccc;border-radius: 5px;'>\n" +
									"<p><label for='OrderTotals' style='color: #fff;background: #799AC0 none repeat-x scroll left top;border-radius: 5px;'>Order Totals</label></p>\n" +
									"<br />\n" +
									"<label for='Shipping' style='font-size:small'>Shipping Price : " + String.valueOf(order.getShippingPrice()) + "</label>\n" +
									"<br />\n" +
									"<label for='Tax' style='font-size:small'>Tax : </label><label for='Tax' style='font-size:small'>$ 0.00</label>\n" +
									"<br />\n" +
									"<label for='Total' style='font-size:small'>Total Price : " + order.getTotalPrice() + "</label>\n" +
								"</td>\n" +
							"</tr>\n" +
							"<tr>\n" +
								"<td style='text-align:left;vertical-align: text-top;border: 1px solid #ccc;border-radius: 5px;'>");
									for(int i=0;i<prodType.length;i++) {
										if(prodType[i]!=null) {
											pWriter.println("<p>Product Type : " + prodType[i] + "</p>\n" +
													"<br />");
										}
									}
									pWriter.println("</td>\n" +
								"<td style='text-align:left;vertical-align: text-top;border: 1px solid #ccc;border-radius: 5px;'>");
									for(int i=0;i<prodID.length;i++) {
										if(prodID[i]!=null) {
											pWriter.println("<p>Product ID : " + prodID[i] + "</p>\n" +
													"<br />");
										}
									}
									pWriter.println("</td>\n" +
								"<td style='text-align:left;vertical-align: text-top;border: 1px solid #ccc;border-radius: 5px;'>");
									for(int i=0;i<qnty.length;i++) {
										if(qnty[i]!=0) {
											pWriter.println("<p>Quantity : " + String.valueOf(qnty[i]) + "</p>\n" +
													"<br />");
										}
									}
									pWriter.println("</td>\n" +
							"</tr>\n" +
							"<tr>\n" +
								"<td colspan='3' style='text-align:right;'>\n" +
									"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
								"</td>\n" +
							"</tr>\n" +
						"</table>\n" +
					"</div>\n" +
				"</form>\n" +
				"</section>");
	}
	
	public void dbFailureMsg(PrintWriter pWriter,String dbMsg) {
		pWriter.println("<div id='body' class='width'>\n" +
				"<section id='content'>\n" +
				"<div style='height:50px;'></div>\n" +
				"<form style='width: 100%;margin: 0 auto;height: 60%;border: 1px solid #ccc;border-radius: 15px;'>\n" +
					"<div class='form_container'>\n" +
						"<br />\n" +
						"<table id='OrderDetails'>\n" +
							"<tr>\n" +	
								"<th><div align='center'>Order Details</div></th>\n" +
							"</tr>\n" +
							"<tr>\n" +	
								"<td><p>" + dbMsg + "</p></td>\n" +
							"</tr>\n" +
							"<tr>\n" +
								"<td style='text-align:right;'>\n" +
									"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
								"</td>\n" +
							"</tr>\n" +
						"</table>\n" +
					"</div>\n" +
				"</form>\n" +
			"</section>");
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		try {
			HttpSession userSession = request.getSession(true);
			String username = (String)userSession.getAttribute("customerKey");
			ShoppingCart cart = (ShoppingCart) userSession.getAttribute("cart");
			if(cart!=null) {
				//Display and write order details
				double subtotal = Double.parseDouble(request.getParameter("productprice"));
				int quantity = Integer.parseInt(request.getParameter("quantity"));
				
				DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Random rand = new Random();
				Date date = new Date();
				SimpleDateFormat sdfOrderDate = new SimpleDateFormat("yyyy/MM/dd");				
				int  confirmationNumber = rand.nextInt(999999999) + 99999999;
				int noOfDays = 14; //i.e two weeks
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);            
				calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
				Date deliveryDate = calendar.getTime();
				double shippingPrice = 5.60;
				double totalPrice = subtotal + shippingPrice;
				String[] prodList = new String[100];
				String[] prodType = new String[100];
				int[] qnty = new int[100];
				int postalCode = Integer.parseInt(request.getParameter("postalcode"));
				for(int i=0;i<cart.getProductList().size();i++) {
					if(cart.getProductList().get(i)!=null) {
						prodList[i] = cart.getProductList().get(i);
						System.out.println("ProductID-" + prodList[i]);
						prodType[i] = cart.getProductType().get(i);
						System.out.println("ProductType-" + prodType[i]);
						qnty[i] = 1;
					}
				}
				Orders order = new Orders(username,request.getParameter("customerName"),prodType,prodList,qnty,sdfOrderDate.format(date),confirmationNumber,sdf.format(deliveryDate),shippingPrice,totalPrice,request.getParameter("warranty"),request.getParameter("country"),request.getParameter("state"),request.getParameter("address1"),request.getParameter("address2"),request.getParameter("city"),postalCode,request.getParameter("mobilenumber"),request.getParameter("emailaddress"));
				MySQLDataStoreUtilities dbObj = new MySQLDataStoreUtilities();
				HashMap<String, String> dbStatus = new HashMap<String, String>();
				dbStatus = dbObj.checkDBConnection();
				
				if(dbStatus.get("status").equals("true")) {
					dbObj.writeOrders(order);
					cart.setProductList(null);
					cart.setCartSize(0);
					userSession.setAttribute("cart", cart);
					
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					engine.generateHtml("Header",request);
					
					displayOrderDetails(pWriter,order);
					
					
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
				}
				else {
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					engine.generateHtml("Header",request);
					
					dbFailureMsg(pWriter,dbStatus.get("msg"));
					
					
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
				}
				
			}
			else {
				//No cart object
				response.sendRedirect("/csj/Controller/LoginServlet");
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
}