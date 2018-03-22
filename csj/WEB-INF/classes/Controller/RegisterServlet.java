package Controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import Controller.HtmlEngine;
import DataAccess.MySQLDataStoreUtilities;
import JavaBeans.Users;

public class RegisterServlet extends HttpServlet {
	
	public RegisterServlet() {
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pWriter = response.getWriter();
		HtmlEngine engine = new HtmlEngine(pWriter);
		engine.generateHtml("Header",request);
		
		pWriter.println("<div id='body' class='width'>\n" +
				"<section id='content'>\n" +
					"<div style='height:50px;'></div>\n" +
						"<form class='register' action='/csj/Controller/RegisterServlet' method='POST'>\n" +
							"<input type='hidden' id='form' name='form' value='register'/>\n" +
							"<div class='form_container'>\n" +
							"<br />\n" +
							"<table id='regTable'>\n" +
								"<tr>\n" +	
									"<th colspan='2'><div align='center'>Customer Registration From</div></th>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td colspan='2'></td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td align='left' colspan='2' style='font-size:small'>\n" +
										"<label for='usertypelbl' style='font-size:small'>User Type : </label>\n" +
										"<br />\n" +
										"<select name='userType' style='width:150px'>\n" + 
										"  <option value='Customer'>Customer</option>\n" + 
										"  <option value='Salesman'>Salesman</option>\n" + 
										"  <option value='Manager'>Manager</option>\n" + 
										"</select>\n" +
										"<br />\n" +
										"<label class='username' for='username' style='font-size:small'>Username : </label>\n" +									
										"<br />\n" +
										"<input type='text' id='username' style='width:343px' name='username' required/>\n" +
										"<br />\n" +
										"<label class='firstName' for='firstName' style='font-size:small'>First Name : </label>\n" +
										"<br />\n" +
										"<input type='text' id='firstName' style='width:343px' name='firstName' required/>\n" +
										"<br />\n" +
										"<label class='lastName' for='lastName' style='font-size:small'>Last Name : </label>\n" +
										"<br />\n" +
										"<input type='text' id='lastName' style='width:343px' name='lastName' required/>\n" +
										"<br />\n" +
										"<label class='pwdLabel' for='password' style='font-size:small'>Password : </label>\n" +
										"<br />\n" +
										"<input type='password' id='pwd' style='width:343px' name='password' required/>\n" +
										"<br />\n" +
										"<label class='emailIDLabel' for='emailID' style='font-size:small'>E-mail : </label>\n" +
										"<br />\n" +
										"<input type ='text' id='emailID' style='width:343px' name='email' required/>\n" +
										"<br />\n" +
										"<label class='dateOfBirthLabel' for='dateOfBirth' style='font-size:small'>Date of Birth : </label>\n" +
										"<br />\n" +
										"<input type='date' id='DOB' name='bday'>\n" +
										"<br />\n" +
										"<label class='addressLabel' for='address' style='font-size:small'>Address : </label>\n" +
										"<br />\n" +
										"<textarea name='address' id='address' rows='6' cols='52'></textarea>\n" +
										"<br />\n" +
										"<label class='zipCodeLabel' for='zipCode' style='font-size:small'>Zip Code : </label>\n" +
										"<br />\n" +
										"<input type='text' id='zipCode' style='width:343px' name='zipcode' required/>\n" +
										"<br />\n" +
									"</td>\n" +
								"</tr>\n" +
								"<tr>\n" +
									"<td colspan='2' align='center'>\n" +
										"<button name='sbmtButton' type='submit' style='width:80px;'>Submit</button>\n" +
									"</td>\n" +
								"</tr>\n" +
							"</table>\n" +
							"</div>\n" +
						"</form>\n" +		
				"</section>");
		
		engine.generateHtml("LeftNavigationBar",request);
		engine.generateHtml("Footer",request);
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		//Logic to put data onto Users bean
		try {
			MySQLDataStoreUtilities dbObj = new MySQLDataStoreUtilities();
			String username = request.getParameter("username");
			String userType = request.getParameter("userType");
			HashMap<String, String> dbStatus = new HashMap<String, String>();
			dbStatus = dbObj.checkDBConnection();
			if(dbStatus.get("status").equals("true")) {
				Users user = dbObj.getUserProfile(username);
				if(user == null)
				{
					user = new Users(userType,username,request.getParameter("firstName"),request.getParameter("lastName"),request.getParameter("address"),Integer.parseInt(request.getParameter("zipcode")),request.getParameter("email"),request.getParameter("bday"),request.getParameter("password"));
					dbObj.writeUserProfile(user);
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					engine.generateHtml("Header",request);
					
					pWriter.println("<div id='body' class='width'>\n" +
							"<section id='content'>\n" +
							"<form class='outline' action='' method='POST'>\n" +
								"<article>\n" +
									"<p>User - " + username +" Registered Successfully.</p>\n" +
									"<p>Please proceed to Login page.</p>\n" +
								"</article>\n" +
							"</form>\n" +
							"</section>");
					
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
				}
				else if((user.getUsername()).equals(username))
				{
					response.setContentType("text/html");
					PrintWriter pWriter = response.getWriter();
					HtmlEngine engine = new HtmlEngine(pWriter);
					engine.generateHtml("Header",request);
					
					pWriter.println("<div id='body' class='width'>\n" +
							"<section id='content'>\n" +
							"<form class='outline' action='' method='POST'>\n" +
								"<article>\n" +
									"<p>User - " + username +" already registered.</p>\n" +
									"<p>Please proceed to Login page.</p>\n" +
								"</article>\n" +
								"</form>\n" +
							"</section>");
					
					engine.generateHtml("LeftNavigationBar",request);
					engine.generateHtml("Footer",request);
				}
			}
			else {
				response.setContentType("text/html");
				PrintWriter pWriter = response.getWriter();
				HtmlEngine engine = new HtmlEngine(pWriter);
				engine.generateHtml("Header",request);
				
				pWriter.println("<div id='body' class='width'>\n" +
						"<section id='content'>\n" +
						"<form class='outline' action='' method='POST'>\n" +
							"<article>\n" +
								"<p>" + dbStatus.get("msg") + "</p>\n" +	
							"</article>\n" +
						"</form>\n" +
						"</section>");
				
				engine.generateHtml("LeftNavigationBar",request);
				engine.generateHtml("Footer",request);
			}
		}
		catch(EOFException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}
}