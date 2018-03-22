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

public class PortableServlet extends HttpServlet {
	
	public static MySQLDataStoreUtilities dbObj;
	
	public PortableServlet() {
		dbObj = new MySQLDataStoreUtilities();
	}
	
	public void printCountryField(PrintWriter pWriter) {
		pWriter.println("<select name='country'>\n" + 		
		"<option value='Afghanistan'>Afghanistan</option>\n" + 
		"<option value='Albania'>Albania</option>\n" + 
		"<option value='Algeria'>Algeria</option>\n" + 
		"<option value='American Samoa'>American Samoa</option>\n" + 
		"<option value='Andorra'>Andorra</option>\n" + 
		"<option value='Angola'>Angola</option>\n" + 
		"<option value='Anguilla'>Anguilla</option>\n" + 
		"<option value='Antigua &amp; Barbuda'>Antigua &amp; Barbuda</option>\n" + 
		"<option value='Argentina'>Argentina</option>\n" + 
		"<option value='Armenia'>Armenia</option>\n" + 
		"<option value='Aruba'>Aruba</option>\n" + 
		"<option value='Australia'>Australia</option>\n" + 
		"<option value='Austria'>Austria</option>\n" + 
		"<option value='Azerbaijan'>Azerbaijan</option>\n" + 
		"<option value='Bahamas'>Bahamas</option>\n" + 
		"<option value='Bahrain'>Bahrain</option>\n" + 
		"<option value='Bangladesh'>Bangladesh</option>\n" + 
		"<option value='Barbados'>Barbados</option>\n" + 
		"<option value='Belarus'>Belarus</option>\n" + 
		"<option value='Belgium'>Belgium</option>\n" + 
		"<option value='Belize'>Belize</option>\n" + 
		"<option value='Benin'>Benin</option>\n" + 
		"<option value='Bermuda'>Bermuda</option>\n" + 
		"<option value='Bhutan'>Bhutan</option>\n" + 
		"<option value='Bolivia'>Bolivia</option>\n" + 
		"<option value='Bonaire'>Bonaire</option>\n" + 
		"<option value='Bosnia &amp; Herzegovina'>Bosnia &amp; Herzegovina</option>\n" + 
		"<option value='Botswana'>Botswana</option>\n" + 
		"<option value='Brazil'>Brazil</option>\n" + 
		"<option value='British Indian Ocean Ter'>British Indian Ocean Ter</option>\n" + 
		"<option value='Brunei'>Brunei</option>\n" + 
		"<option value='Bulgaria'>Bulgaria</option>\n" + 
		"<option value='Burkina Faso'>Burkina Faso</option>\n" + 
		"<option value='Burundi'>Burundi</option>\n" + 
		"<option value='Cambodia'>Cambodia</option>\n" + 
		"<option value='Cameroon'>Cameroon</option>\n" + 
		"<option value='Canada'>Canada</option>\n" + 
		"<option value='Canary Islands'>Canary Islands</option>\n" + 
		"<option value='Cape Verde'>Cape Verde</option>\n" + 
		"<option value='Cayman Islands'>Cayman Islands</option>\n" + 
		"<option value='Central African Republic'>Central African Republic</option>\n" + 
		"<option value='Chad'>Chad</option>\n" + 
		"<option value='Channel Islands'>Channel Islands</option>\n" + 
		"<option value='Chile'>Chile</option>\n" + 
		"<option value='China'>China</option>\n" + 
		"<option value='Christmas Island'>Christmas Island</option>\n" + 
		"<option value='Cocos Island'>Cocos Island</option>\n" + 
		"<option value='Colombia'>Colombia</option>\n" + 
		"<option value='Comoros'>Comoros</option>\n" + 
		"<option value='Congo'>Congo</option>\n" + 
		"<option value='Cook Islands'>Cook Islands</option>\n" + 
		"<option value='Costa Rica'>Costa Rica</option>\n" + 
		"<option value='Cote DIvoire'>Cote D'Ivoire</option>\n" + 
		"<option value='Croatia'>Croatia</option>\n" + 
		"<option value='Cuba'>Cuba</option>\n" + 
		"<option value='Curaco'>Curacao</option>\n" + 
		"<option value='Cyprus'>Cyprus</option>\n" + 
		"<option value='Czech Republic'>Czech Republic</option>\n" + 
		"<option value='Denmark'>Denmark</option>\n" + 
		"<option value='Djibouti'>Djibouti</option>\n" + 
		"<option value='Dominica'>Dominica</option>\n" + 
		"<option value='Dominican Republic'>Dominican Republic</option>\n" + 
		"<option value='East Timor'>East Timor</option>\n" + 
		"<option value='Ecuador'>Ecuador</option>\n" + 
		"<option value='Egypt'>Egypt</option>\n" + 
		"<option value='El Salvador'>El Salvador</option>\n" + 
		"<option value='Equatorial Guinea'>Equatorial Guinea</option>\n" + 
		"<option value='Eritrea'>Eritrea</option>\n" + 
		"<option value='Estonia'>Estonia</option>\n" + 
		"<option value='Ethiopia'>Ethiopia</option>\n" + 
		"<option value='Falkland Islands'>Falkland Islands</option>\n" + 
		"<option value='Faroe Islands'>Faroe Islands</option>\n" + 
		"<option value='Fiji'>Fiji</option>\n" + 
		"<option value='Finland'>Finland</option>\n" + 
		"<option value='France'>France</option>\n" + 
		"<option value='French Guiana'>French Guiana</option>\n" + 
		"<option value='French Polynesia'>French Polynesia</option>\n" + 
		"<option value='French Southern Ter'>French Southern Ter</option>\n" + 
		"<option value='Gabon'>Gabon</option>\n" + 
		"<option value='Gambia'>Gambia</option>\n" + 
		"<option value='Georgia'>Georgia</option>\n" + 
		"<option value='Germany'>Germany</option>\n" + 
		"<option value='Ghana'>Ghana</option>\n" + 
		"<option value='Gibraltar'>Gibraltar</option>\n" + 
		"<option value='Great Britain'>Great Britain</option>\n" + 
		"<option value='Greece'>Greece</option>\n" + 
		"<option value='Greenland'>Greenland</option>\n" + 
		"<option value='Grenada'>Grenada</option>\n" + 
		"<option value='Guadeloupe'>Guadeloupe</option>\n" + 
		"<option value='Guam'>Guam</option>\n" + 
		"<option value='Guatemala'>Guatemala</option>\n" + 
		"<option value='Guinea'>Guinea</option>\n" + 
		"<option value='Guyana'>Guyana</option>\n" + 
		"<option value='Haiti'>Haiti</option>\n" + 
		"<option value='Hawaii'>Hawaii</option>\n" + 
		"<option value='Honduras'>Honduras</option>\n" + 
		"<option value='Hong Kong'>Hong Kong</option>\n" + 
		"<option value='Hungary'>Hungary</option>\n" + 
		"<option value='Iceland'>Iceland</option>\n" + 
		"<option value='India'>India</option>\n" + 
		"<option value='Indonesia'>Indonesia</option>\n" + 
		"<option value='Iran'>Iran</option>\n" + 
		"<option value='Iraq'>Iraq</option>\n" + 
		"<option value='Ireland'>Ireland</option>\n" + 
		"<option value='Isle of Man'>Isle of Man</option>\n" + 
		"<option value='Israel'>Israel</option>\n" + 
		"<option value='Italy'>Italy</option>\n" + 
		"<option value='Jamaica'>Jamaica</option>\n" + 
		"<option value='Japan'>Japan</option>\n" + 
		"<option value='Jordan'>Jordan</option>\n" + 
		"<option value='Kazakhstan'>Kazakhstan</option>\n" + 
		"<option value='Kenya'>Kenya</option>\n" + 
		"<option value='Kiribati'>Kiribati</option>\n" + 
		"<option value='Korea North'>Korea North</option>\n" + 
		"<option value='Korea Sout'>Korea South</option>\n" + 
		"<option value='Kuwait'>Kuwait</option>\n" + 
		"<option value='Kyrgyzstan'>Kyrgyzstan</option>\n" + 
		"<option value='Laos'>Laos</option>\n" + 
		"<option value='Latvia'>Latvia</option>\n" + 
		"<option value='Lebanon'>Lebanon</option>\n" + 
		"<option value='Lesotho'>Lesotho</option>\n" + 
		"<option value='Liberia'>Liberia</option>\n" + 
		"<option value='Libya'>Libya</option>\n" + 
		"<option value='Liechtenstein'>Liechtenstein</option>\n" + 
		"<option value='Lithuania'>Lithuania</option>\n" + 
		"<option value='Luxembourg'>Luxembourg</option>\n" + 
		"<option value='Macau'>Macau</option>\n" + 
		"<option value='Macedonia'>Macedonia</option>\n" + 
		"<option value='Madagascar'>Madagascar</option>\n" + 
		"<option value='Malaysia'>Malaysia</option>\n" + 
		"<option value='Malawi'>Malawi</option>\n" + 
		"<option value='Maldives'>Maldives</option>\n" + 
		"<option value='Mali'>Mali</option>\n" + 
		"<option value='Malta'>Malta</option>\n" + 
		"<option value='Marshall Islands'>Marshall Islands</option>\n" + 
		"<option value='Martinique'>Martinique</option>\n" + 
		"<option value='Mauritania'>Mauritania</option>\n" + 
		"<option value='Mauritius'>Mauritius</option>\n" + 
		"<option value='Mayotte'>Mayotte</option>\n" + 
		"<option value='Mexico'>Mexico</option>\n" + 
		"<option value='Midway Islands'>Midway Islands</option>\n" + 
		"<option value='Moldova'>Moldova</option>\n" + 
		"<option value='Monaco'>Monaco</option>\n" + 
		"<option value='Mongolia'>Mongolia</option>\n" + 
		"<option value='Montserrat'>Montserrat</option>\n" + 
		"<option value='Morocco'>Morocco</option>\n" + 
		"<option value='Mozambique'>Mozambique</option>\n" + 
		"<option value='Myanmar'>Myanmar</option>\n" + 
		"<option value='Nambia'>Nambia</option>\n" + 
		"<option value='Nauru'>Nauru</option>\n" + 
		"<option value='Nepal'>Nepal</option>\n" + 
		"<option value='Netherland Antilles'>Netherland Antilles</option>\n" + 
		"<option value='Netherlands'>Netherlands (Holland, Europe)</option>\n" + 
		"<option value='Nevis'>Nevis</option>\n" + 
		"<option value='New Caledonia'>New Caledonia</option>\n" + 
		"<option value='New Zealand'>New Zealand</option>\n" + 
		"<option value='Nicaragua'>Nicaragua</option>\n" + 
		"<option value='Niger'>Niger</option>\n" + 
		"<option value='Nigeria'>Nigeria</option>\n" + 
		"<option value='Niue'>Niue</option>\n" + 
		"<option value='Norfolk Island'>Norfolk Island</option>\n" + 
		"<option value='Norway'>Norway</option>\n" + 
		"<option value='Oman'>Oman</option>\n" + 
		"<option value='Pakistan'>Pakistan</option>\n" + 
		"<option value='Palau Island'>Palau Island</option>\n" + 
		"<option value='Palestine'>Palestine</option>\n" + 
		"<option value='Panama'>Panama</option>\n" + 
		"<option value='Papua New Guinea'>Papua New Guinea</option>\n" + 
		"<option value='Paraguay'>Paraguay</option>\n" + 
		"<option value='Peru'>Peru</option>\n" + 
		"<option value='Phillipines'>Philippines</option>\n" + 
		"<option value='Pitcairn Island'>Pitcairn Island</option>\n" + 
		"<option value='Poland'>Poland</option>\n" + 
		"<option value='Portugal'>Portugal</option>\n" + 
		"<option value='Puerto Rico'>Puerto Rico</option>\n" + 
		"<option value='Qatar'>Qatar</option>\n" + 
		"<option value='Republic of Montenegro'>Republic of Montenegro</option>\n" + 
		"<option value='Republic of Serbia'>Republic of Serbia</option>\n" + 
		"<option value='Reunion'>Reunion</option>\n" + 
		"<option value='Romania'>Romania</option>\n" + 
		"<option value='Russia'>Russia</option>\n" + 
		"<option value='Rwanda'>Rwanda</option>\n" + 
		"<option value='St Barthelemy'>St Barthelemy</option>\n" + 
		"<option value='St Eustatius'>St Eustatius</option>\n" + 
		"<option value='St Helena'>St Helena</option>\n" + 
		"<option value='St Kitts-Nevis'>St Kitts-Nevis</option>\n" + 
		"<option value='St Lucia'>St Lucia</option>\n" + 
		"<option value='St Maarten'>St Maarten</option>\n" + 
		"<option value='St Pierre &amp; Miquelon'>St Pierre &amp; Miquelon</option>\n" + 
		"<option value='St Vincent &amp; Grenadines'>St Vincent &amp; Grenadines</option>\n" + 
		"<option value='Saipan'>Saipan</option>\n" + 
		"<option value='Samoa'>Samoa</option>\n" + 
		"<option value='Samoa American'>Samoa American</option>\n" + 
		"<option value='San Marino'>San Marino</option>\n" + 
		"<option value='Sao Tome &amp; Principe'>Sao Tome &amp; Principe</option>\n" + 
		"<option value='Saudi Arabia'>Saudi Arabia</option>\n" + 
		"<option value='Senegal'>Senegal</option>\n" + 
		"<option value='Serbia'>Serbia</option>\n" + 
		"<option value='Seychelles'>Seychelles</option>\n" + 
		"<option value='Sierra Leone'>Sierra Leone</option>\n" + 
		"<option value='Singapore'>Singapore</option>\n" + 
		"<option value='Slovakia'>Slovakia</option>\n" + 
		"<option value='Slovenia'>Slovenia</option>\n" + 
		"<option value='Solomon Islands'>Solomon Islands</option>\n" + 
		"<option value='Somalia'>Somalia</option>\n" + 
		"<option value='South Africa'>South Africa</option>\n" + 
		"<option value='Spain'>Spain</option>\n" + 
		"<option value='Sri Lanka'>Sri Lanka</option>\n" + 
		"<option value='Sudan'>Sudan</option>\n" + 
		"<option value='Suriname'>Suriname</option>\n" + 
		"<option value='Swaziland'>Swaziland</option>\n" + 
		"<option value='Sweden'>Sweden</option>\n" + 
		"<option value='Switzerland'>Switzerland</option>\n" + 
		"<option value='Syria'>Syria</option>\n" + 
		"<option value='Tahiti'>Tahiti</option>\n" + 
		"<option value='Taiwan'>Taiwan</option>\n" + 
		"<option value='Tajikistan'>Tajikistan</option>\n" + 
		"<option value='Tanzania'>Tanzania</option>\n" + 
		"<option value='Thailand'>Thailand</option>\n" + 
		"<option value='Togo'>Togo</option>\n" + 
		"<option value='Tokelau'>Tokelau</option>\n" + 
		"<option value='Tonga'>Tonga</option>\n" + 
		"<option value='Trinidad &amp; Tobago'>Trinidad &amp; Tobago</option>\n" + 
		"<option value='Tunisia'>Tunisia</option>\n" + 
		"<option value='Turkey'>Turkey</option>\n" + 
		"<option value='Turkmenistan'>Turkmenistan</option>\n" + 
		"<option value='Turks &amp; Caicos Is'>Turks &amp; Caicos Is</option>\n" + 
		"<option value='Tuvalu'>Tuvalu</option>\n" + 
		"<option value='Uganda'>Uganda</option>\n" + 
		"<option value='Ukraine'>Ukraine</option>\n" + 
		"<option value='United Arab Erimates'>United Arab Emirates</option>\n" + 
		"<option value='United Kingdom'>United Kingdom</option>\n" + 
		"<option value='United States of America'>United States of America</option>\n" + 
		"<option value='Uraguay'>Uruguay</option>\n" + 
		"<option value='Uzbekistan'>Uzbekistan</option>\n" + 
		"<option value='Vanuatu'>Vanuatu</option>\n" + 
		"<option value='Vatican City State'>Vatican City State</option>\n" + 
		"<option value='Venezuela'>Venezuela</option>\n" + 
		"<option value='Vietnam'>Vietnam</option>\n" + 
		"<option value='Virgin Islands (Brit)'>Virgin Islands (Brit)</option>\n" + 
		"<option value='Virgin Islands (USA)'>Virgin Islands (USA)</option>\n" + 
		"<option value='Wake Island'>Wake Island</option>\n" + 
		"<option value='Wallis &amp; Futana Is'>Wallis &amp; Futana Is</option>\n" + 
		"<option value='Yemen'>Yemen</option>\n" + 
		"<option value='Zaire'>Zaire</option>\n" + 
		"<option value='Zambia'>Zambia</option>\n" + 
		"<option value='Zimbabwe'>Zimbabwe</option>\n" + 
		"</select>");
	}
	
	public void printPaymentDetails(PrintWriter pWriter,String id,String qnty,String type,double totalPrice) {
		pWriter.println("<div id='body' class='width'>\n" +
				"<section id='content'>\n" +
				"<form style='width: 70%;margin: 0 auto;height: 50%;border: 1px solid #ccc;border-radius: 15px;' action='/csj/Controller/PortableServlet?id=" + id + "&qnty=" + qnty + "' method='POST'>\n" +
					"<div class='form_container'>\n" +
					"<br />\n" +
					"<table id='SmartWatchPayment'>\n" +
						"<tr>\n" +	
							"<th colspan='2'><div align='center'>Payment Details</div></th>\n" +
						"</tr>\n" +
						"<tr>\n" +	
							"<td colspan='2'><p></p></td>\n" +
						"</tr>\n" +
						"<tr>\n" +
							"<td style='text-align:center;'>\n" +
								"<label for='ProductType' style='font-size:small'>Product Type : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='producttype' style='width:300px;background-color:#bfbfbf;' name='producttype' value='" + type + "' readonly/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
							"<td style='text-align:center;'>\n" +
								"<label for='ProductID' style='font-size:small'>Product ID : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='productID' style='width:300px;background-color:#bfbfbf;' name='productID' value='" + id + "' readonly/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
							"<td style='text-align:center;'>\n" +
								"<label for='Quantity' style='font-size:small'>Quantity : </label>\n" +
							"</td>\n" +
							"<td style='text-align:left;'>\n" +
								"<input type='text' id='quantity' style='width:20px;background-color:#bfbfbf;' name='quantity' value='" + qnty + "' readonly/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
							"<td style='text-align:center;'>\n" +
								"<label for='ProductPrice' style='font-size:small'>Product Price : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='productprice' style='width:300px;background-color:#bfbfbf;' name='productprice' value='$" + String.valueOf(totalPrice) + "' readonly/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
							"<td style='text-align:center;'>\n" +
								"<label for='customerName' style='font-size:small'>Customer Name : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='customerName' style='width:300px' name='customerName' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
							"<td style='text-align:right;'>\n" +
								"<label for='warranty'>Warranty : </label>\n" +
							"</td>\n" +
							"<td style='text-align:left;vertical-align: text-top;'>\n" +
								"  <input type='radio' name='warranty' value='NoWarranty' checked>NoWarranty<br>\n" + 
								"  <input type='radio' name='warranty' value='OneYearWarranty'>OneYearWarranty - $24.99<br>\n" + 
								"  <input type='radio' name='warranty' value='TwoYearWarranty'>TwoYearWarranty - $49.99<br>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
							"<td style='font-size:small;text-align:center;vertical-align: text-top;'>\n" +
								"<label for='CardType' style='font-size:small'>Card Type : </label>\n" +
							"</td>\n" +
							"<td style='font-size:small;text-align:left;vertical-align: text-top;'>\n" +
								"<input type='radio' name='cardType' value='VISA'>&nbsp;&nbsp;<img src='/csj/images/VISALogo.jpg' style='width:40px;height:30px' alt='VISALogo'>&nbsp;&nbsp;\n" +  
								"<input type='radio' name='cardType' value='MasterCard'>&nbsp;&nbsp;<img src='/csj/images/MasterCardLogo.jpg' style='width:40px;height:30px' alt='MasterCardLogo'>&nbsp;&nbsp;\n" +
							  	"<input type='radio' name='cardType' value='AmericanExpress'>&nbsp;&nbsp;<img src='/csj/images/AmericanExpress.jpg' style='width:40px;height:30px' alt='AmericanExpress'>&nbsp;&nbsp;\n" +
							  	"<input type='radio' name='cardType' value='Discover'>&nbsp;&nbsp;<img src='/csj/images/Discover.jpg' style='width:40px;height:30px' alt='Discover'>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
							"<td style='text-align:center;'>\n" +
								"<label for='CardNumber' style='font-size:small'>Card Number : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='cardnumber' style='width:300px' name='cardnumber' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='CVV' style='font-size:small'>CVV : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='cvv' style='width:300px' name='cvv' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='CardholderName' style='font-size:small'>Cardholder Name : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='cvv' style='width:300px' name='cardholdername' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='Country' style='font-size:small'>Country : </label>\n" +
							"</td>\n" +
							"<td style='text-align:right;'>");
		
					printCountryField(pWriter);
							
					pWriter.println("</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='State' style='font-size:small'>State : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='state' style='width:300px' name='state' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='Address1' style='font-size:small'>Address 1 : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='address1' style='width:300px' name='address1' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='Address2' style='font-size:small'>Address 2 : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='address2' style='width:300px' name='address2'/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='City' style='font-size:small'>City : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='city' style='width:300px' name='city' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='PostalCode' style='font-size:small'>Postal Code : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='postalcode' style='width:300px' name='postalcode' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='MobileNumber' style='font-size:small'>Mobile Number : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='mobilenumber' style='width:300px' name='mobilenumber' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td style='text-align:center;'>\n" +
								"<label for='EmailAddress' style='font-size:small'>Email Address : </label>\n" +
							"</td>\n" +
							"<td style='text-align:center;'>\n" +
								"<input type='text' id='emailaddress' style='width:300px' name='emailaddress' required/>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
							"<td colspan='2' style='text-align:center;'>\n" +
								"<button name='sbmtButton' type='submit' style='width:80px;'>Submit</button>\n" +
							"</td>\n" +
						"</tr>\n" +
						"<tr>\n" +
						"<td colspan='2' style='text-align:right;'>\n" +
							"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
						"</td>\n" +
					"</tr>\n" +
					"</table>\n" +
					"</div>\n" +
				"</form>\n" +
				"</section>");
	}
	
	public void displayLogoutHeader(PrintWriter pWriter,HttpServletRequest request){
		try {
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
		            		"</table>\n" +
		            		"</div>\n" +
						"</nav>");
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@SuppressWarnings({"unchecked"})
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession userSession = request.getSession(true);
		String username = (String)userSession.getAttribute("customerKey");
		if(username!=null) {
			String id = request.getParameter("id");
			String op = request.getParameter("op");
			String type = request.getParameter("type");
			if(id == null || op == null || type == null)
			{
				response.sendRedirect("/csj/Controller/LoginServlet");
			}
			else {
				if(op.equals("addtocart"))
				{
					//Code to addtocart
					ShoppingCart cart = (ShoppingCart) userSession.getAttribute("cart");
					Vector<String> prodList;
					Vector<String> prodType;
					prodList = cart.getProductList();
					prodType = cart.getProductType();
					int size = cart.getCartSize();
					int newSize = size + 1;
					if(prodList != null) {
						//prodList.put(id, id);	
						prodList.add(id);
						prodType.add(type);
					}
					else {
						prodList = new Vector<String>();
						//prodList.put(id, id);	
						prodList.add(id);
						prodType = new Vector<String>();
						prodType.add(type);
					}
									
					cart.setProductList(prodList);
					cart.setProductType(prodType);
					cart.setCartSize(newSize);
					cart.setUsername(username);
					userSession.setAttribute("cart", cart);
					//display page
					response.sendRedirect("/csj/Controller/HomeServlet?var=" + type);
				}
				else {
					//Code for Buy Now link
					switch(type) {
						case "smartwatches": {
							SmartWatch watch = new SmartWatch();
							SmartWatch watchTmp = new SmartWatch();
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							HashMap<String, String> dbStatus = new HashMap<String, String>();
							dbStatus = dbObj.checkDBConnection();
							if(dbStatus.get("status").equals("true")) {
								for(Map.Entry<String, SmartWatch> w : dbObj.getWatchMapDB().entrySet()) {					
									watchTmp = w.getValue();
									if(id.equals(watchTmp.getId()))
									{
										watch = watchTmp;
										break;
									}
								}
								//Code to buynow
								String qnty = request.getParameter("qnty");
								System.out.println("qnty :" + qnty);
								double totalPrice = Double.parseDouble(qnty) * (watch.getPrice());
								//display payment details page
								engine.generateHtml("Header",request);
								
								printPaymentDetails(pWriter,watch.getId(),qnty,type,totalPrice);
							}
							else {
								engine.generateHtml("Header",request);
								pWriter.println("<div id='body' class='width'>\n" +
										"<section id='content'>\n" +
										"<div style='height:50px;'></div>\n" +
										"<form class='register' action='' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='ListTable'>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:center;'>\n" +
														"<p>" + dbStatus.get("msg") + "</p>\n" +										
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:right;'>\n" +
														"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
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
						
						case "speakers": {
							
							//Code for Buy Now link
							Speakers speaker = new Speakers();
							Speakers speakerTmp = new Speakers();
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							HashMap<String, String> dbStatus = new HashMap<String, String>();
							dbStatus = dbObj.checkDBConnection();
							if(dbStatus.get("status").equals("true")) {
								for(Map.Entry<String, Speakers> w : dbObj.getSpeakerMapDB().entrySet()) {					
									speakerTmp = w.getValue();
									if(id.equals(speakerTmp.getId()))
									{
										speaker = speakerTmp;
										break;
									}
								}
								//Code to buynow
								String qnty = request.getParameter("qnty");
								System.out.println("qnty :" + qnty);
								double totalPrice = Double.parseDouble(qnty) * (speaker.getPrice());
								//display payment details page
								engine.generateHtml("Header",request);
								
								printPaymentDetails(pWriter,speaker.getId(),qnty,type,totalPrice);
							}
							else {
								engine.generateHtml("Header",request);
								pWriter.println("<div id='body' class='width'>\n" +
										"<section id='content'>\n" +
										"<div style='height:50px;'></div>\n" +
										"<form class='register' action='' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='ListTable'>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:center;'>\n" +
														"<p>" + dbStatus.get("msg") + "</p>\n" +										
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:right;'>\n" +
														"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
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
						case "headphones": {
							
							//Code for Buy Now link
							Headphones headphone = new Headphones();
							Headphones headphoneTmp = new Headphones();
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							HashMap<String, String> dbStatus = new HashMap<String, String>();
							dbStatus = dbObj.checkDBConnection();
							if(dbStatus.get("status").equals("true")) {
								for(Map.Entry<String, Headphones> w : dbObj.getHeadphoneMapDB().entrySet()) {					
									headphoneTmp = w.getValue();
									if(id.equals(headphoneTmp.getId()))
									{
										headphone = headphoneTmp;
										break;
									}
								}
								//Code to buynow
								String qnty = request.getParameter("qnty");
								System.out.println("qnty :" + qnty);
								double totalPrice = Double.parseDouble(qnty) * (headphone.getPrice());
								//display payment details page
								engine.generateHtml("Header",request);
								
								printPaymentDetails(pWriter,headphone.getId(),qnty,type,totalPrice);
							}
							else {
								engine.generateHtml("Header",request);
								pWriter.println("<div id='body' class='width'>\n" +
										"<section id='content'>\n" +
										"<div style='height:50px;'></div>\n" +
										"<form class='register' action='' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='ListTable'>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:center;'>\n" +
														"<p>" + dbStatus.get("msg") + "</p>\n" +										
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:right;'>\n" +
														"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
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
						case "smartphones": {
							
							//Code for Buy Now link
							Phones phone = new Phones();
							Phones phoneTmp = new Phones();
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							HashMap<String, String> dbStatus = new HashMap<String, String>();
							dbStatus = dbObj.checkDBConnection();
							if(dbStatus.get("status").equals("true")) {
								for(Map.Entry<String, Phones> w : dbObj.getPhoneMapDB().entrySet()) {					
									phoneTmp = w.getValue();
									if(id.equals(phoneTmp.getId()))
									{
										phone = phoneTmp;
										break;
									}
								}
								//Code to buynow
								String qnty = request.getParameter("qnty");
								System.out.println("qnty :" + qnty);
								double totalPrice = Double.parseDouble(qnty) * (phone.getPrice());
								//display payment details page
								engine.generateHtml("Header",request);
								
								printPaymentDetails(pWriter,phone.getId(),qnty,type,totalPrice);
							}
							else {
								engine.generateHtml("Header",request);
								pWriter.println("<div id='body' class='width'>\n" +
										"<section id='content'>\n" +
										"<div style='height:50px;'></div>\n" +
										"<form class='register' action='' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='ListTable'>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:center;'>\n" +
														"<p>" + dbStatus.get("msg") + "</p>\n" +										
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:right;'>\n" +
														"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
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
						case "laptops": {
							
							//Code for Buy Now link
							Laptops laptop = new Laptops();
							Laptops laptopTmp = new Laptops();
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							HashMap<String, String> dbStatus = new HashMap<String, String>();
							dbStatus = dbObj.checkDBConnection();
							if(dbStatus.get("status").equals("true")) {
								for(Map.Entry<String, Laptops> w : dbObj.getLaptopMapDB().entrySet()) {					
									laptopTmp = w.getValue();
									if(id.equals(laptopTmp.getId()))
									{
										laptop = laptopTmp;
										break;
									}
								}
								//Code to buynow
								String qnty = request.getParameter("qnty");
								System.out.println("qnty :" + qnty);
								double totalPrice = Double.parseDouble(qnty) * (laptop.getPrice());
								//display payment details page
								engine.generateHtml("Header",request);
								
								printPaymentDetails(pWriter,laptop.getId(),qnty,type,totalPrice);
							}
							else {
								engine.generateHtml("Header",request);
								pWriter.println("<div id='body' class='width'>\n" +
										"<section id='content'>\n" +
										"<div style='height:50px;'></div>\n" +
										"<form class='register' action='' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='ListTable'>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:center;'>\n" +
														"<p>" + dbStatus.get("msg") + "</p>\n" +										
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:right;'>\n" +
														"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
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
						case "externalstorage": {
							//Code for Buy Now link
							ExternalStorage storage = new ExternalStorage();
							ExternalStorage storageTmp = new ExternalStorage();
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							HashMap<String, String> dbStatus = new HashMap<String, String>();
							dbStatus = dbObj.checkDBConnection();
							if(dbStatus.get("status").equals("true")) {
								for(Map.Entry<String, ExternalStorage> w : dbObj.getStorageMapDB().entrySet()) {					
									storageTmp = w.getValue();
									if(id.equals(storageTmp.getId()))
									{
										storage = storageTmp;
										break;
									}
								}
								//Code to buynow
								String qnty = request.getParameter("qnty");
								System.out.println("qnty :" + qnty);
								double totalPrice = Double.parseDouble(qnty) * (storage.getPrice());
								//display payment details page
								engine.generateHtml("Header",request);
								
								printPaymentDetails(pWriter,storage.getId(),qnty,type,totalPrice);
							}
							else {
								engine.generateHtml("Header",request);
								pWriter.println("<div id='body' class='width'>\n" +
										"<section id='content'>\n" +
										"<div style='height:50px;'></div>\n" +
										"<form class='register' action='' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='ListTable'>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:center;'>\n" +
														"<p>" + dbStatus.get("msg") + "</p>\n" +										
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:right;'>\n" +
														"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
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
						case "accessories": {
							
							//Code for Buy Now link
							Accessories accessory = new Accessories();
							Accessories accessoryTmp = new Accessories();
							response.setContentType("text/html");
							PrintWriter pWriter = response.getWriter();
							HtmlEngine engine = new HtmlEngine(pWriter);
							HashMap<String, String> dbStatus = new HashMap<String, String>();
							dbStatus = dbObj.checkDBConnection();
							if(dbStatus.get("status").equals("true")) {
								for(Map.Entry<String, Accessories> w : dbObj.getAccessoryMapDB().entrySet()) {					
									accessoryTmp = w.getValue();
									if(id.equals(accessoryTmp.getId()))
									{
										accessory = accessoryTmp;
										break;
									}
								}
								//Code to buynow
								String qnty = request.getParameter("qnty");
								System.out.println("qnty :" + qnty);
								double totalPrice = Double.parseDouble(qnty) * (accessory.getPrice());
								//display payment details page
								engine.generateHtml("Header",request);
								
								printPaymentDetails(pWriter,accessory.getId(),qnty,type,totalPrice);
							}
							else {
								engine.generateHtml("Header",request);
								pWriter.println("<div id='body' class='width'>\n" +
										"<section id='content'>\n" +
										"<div style='height:50px;'></div>\n" +
										"<form class='register' action='' method='POST'>\n" +
										"<div class='form_container'>\n" +
											"<table id='ListTable'>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:center;'>\n" +
														"<p>" + dbStatus.get("msg") + "</p>\n" +										
													"</td>\n" +
												"</tr>\n" +
												"<tr>\n" +
													"<td colspan='2' style='text-align:right;'>\n" +
														"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
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
						default: {
							break;
						}
					}
				}	
			}
		}
		else {
			response.sendRedirect("/csj/Controller/LoginServlet");
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
									"<label for='confirmationNumberlbl' style='font-size:small'>Confirmation Number : </label><label for='confirmationNumber' style='font-size:small'>" + order.getConfirmationNumber() + "</label>\n" +
									"<br />\n" +
									"<label for='orderDatelbl' style='font-size:small'>Order Date : </label><label for='orderDate' style='font-size:small'>" + order.getOrderDate() + "</label>\n" +
									"<br />\n" +
									"<label for='deliveryDatelbl' style='font-size:small'>Delivery Date : </label><label for='deliveryDate' style='font-size:small'>" + order.getDeliveryDate() + "</label>\n" +
									"<br />\n" +
									"<label for='orderStatuslbl' style='font-size:small'>Order Status : </label><label for='orderStatus' style='font-size:small'>In Progress</label>\n" +
									"<br />\n" +
									"<label for='emailAddresslbl' style='font-size:small'>Email Address : </label><label for='emailAddress' style='font-size:small'>" + order.getEmailAddress() + "</label>\n" +
								"</td>\n" +
								"<td style='text-align:left;vertical-align: text-top;border: 1px solid #ccc;border-radius: 5px;'>\n" +
									"<p><label for='OrderTotals' style='color: #fff;background: #799AC0 none repeat-x scroll left top;border-radius: 5px;'>Order Totals</label></p>\n" +
									"<br />\n" +
									"<label for='Shipping' style='font-size:small'>Shipping : </label><label for='Shipping' style='font-size:small'>$ " + String.valueOf(order.getShippingPrice()) + "</label>\n" +
									"<br />\n" +
									"<label for='Tax' style='font-size:small'>Tax : </label><label for='Tax' style='font-size:small'>$ 0.00</label>\n" +
									"<br />\n" +
									"<label for='Total' style='font-size:small'>Total : </label><label for='Total' style='font-size:small'>$ " + order.getTotalPrice() + "</label>\n" +
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
	
	
	@SuppressWarnings({"unchecked"})
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		//order submission
		HttpSession userSession = request.getSession(true);
		String username = (String)userSession.getAttribute("customerKey");
		if(username != null) {
			String id = request.getParameter("id");
			int qnty = Integer.parseInt(request.getParameter("qnty"));
				
			
			String type = request.getParameter("producttype");
			if(id == null || type == null) {
				response.sendRedirect("/csj/Controller/HomeServlet?var=home");
			}
			else {
				switch(type) {
					case "smartwatches": {
						System.out.println("Portable Servlet Customer Username - " + username);
						DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Random rand = new Random();
						Date date = new Date();
						SmartWatch watch = new SmartWatch();
						SmartWatch watchTmp = new SmartWatch();
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						HashMap<String, String> dbStatus = new HashMap<String, String>();
						dbStatus = dbObj.checkDBConnection();
						if(dbStatus.get("status").equals("true")) {
							for(Map.Entry<String, SmartWatch> w : dbObj.getWatchMapDB().entrySet()) {					
								watchTmp = w.getValue();
								if(id.equals(watchTmp.getId()))
								{
									watch = watchTmp;
									break;
								}
							}
							int  confirmationNumber = rand.nextInt(999999999) + 99999999;
							SimpleDateFormat sdfOrderDate = new SimpleDateFormat("yyyy/MM/dd");
							int noOfDays = 14; //i.e two weeks
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(date);            
							calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
							Date deliveryDate = calendar.getTime();
							double shippingPrice = 5.60;
							double subTotal = (double)qnty * (watch.getPrice());
							double totalPrice = subTotal + shippingPrice;
							
							int postalCode = Integer.parseInt(request.getParameter("postalcode"));
							String[] prodTypeList = new String[] {type};
							String[] prodIDList = new String[] {watch.getId()};
							int[] qntyList = new int[] {qnty};
							Orders order = new Orders(username,request.getParameter("customerName"),prodTypeList,prodIDList,qntyList,sdfOrderDate.format(date),confirmationNumber,sdf.format(deliveryDate),shippingPrice,totalPrice,request.getParameter("warranty"),request.getParameter("country"),request.getParameter("state"),request.getParameter("address1"),request.getParameter("address2"),request.getParameter("city"),postalCode,request.getParameter("mobilenumber"),request.getParameter("emailaddress"));
							dbObj.writeOrders(order);
							engine.generateHtml("Header",request);
							
							displayOrderDetails(pWriter,order);
						}
						else {
							engine.generateHtml("Header",request);
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form class='register' action='' method='POST'>\n" +
									"<div class='form_container'>\n" +
										"<table id='ListTable'>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:center;'>\n" +
													"<p>" + dbStatus.get("msg") + "</p>\n" +										
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
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
					case "speakers": {
						DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Random rand = new Random();
						Date date = new Date();

						Speakers speaker = new Speakers();
						Speakers speakerTmp = new Speakers();
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						HashMap<String, String> dbStatus = new HashMap<String, String>();
						dbStatus = dbObj.checkDBConnection();
						if(dbStatus.get("status").equals("true")) {
							for(Map.Entry<String, Speakers> w : dbObj.getSpeakerMapDB().entrySet()) {					
								speakerTmp = w.getValue();
								if(id.equals(speakerTmp.getId()))
								{
									speaker = speakerTmp;
									break;
								}
							}
							int  confirmationNumber = rand.nextInt(999999999) + 99999999;
							int noOfDays = 14; //i.e two weeks
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(date);            
							calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
							Date deliveryDate = calendar.getTime();
							double shippingPrice = 5.60;
							double subTotal = (double)qnty * (speaker.getPrice());
							double totalPrice = subTotal + shippingPrice;
							
							int postalCode = Integer.parseInt(request.getParameter("postalcode"));
							String[] prodTypeList = new String[] {type};
							String[] prodIDList = new String[] {speaker.getId()};
							int[] qntyList = new int[] {qnty};
							Orders order = new Orders(username,request.getParameter("customerName"),prodTypeList,prodIDList,qntyList,sdf.format(date),confirmationNumber,sdf.format(deliveryDate),shippingPrice,totalPrice,request.getParameter("warranty"),request.getParameter("country"),request.getParameter("state"),request.getParameter("address1"),request.getParameter("address2"),request.getParameter("city"),postalCode,request.getParameter("mobilenumber"),request.getParameter("emailaddress"));
							dbObj.writeOrders(order);

							engine.generateHtml("Header",request);
							
							displayOrderDetails(pWriter,order);
						}
						else {
							engine.generateHtml("Header",request);
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form class='register' action='' method='POST'>\n" +
									"<div class='form_container'>\n" +
										"<table id='ListTable'>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:center;'>\n" +
													"<p>" + dbStatus.get("msg") + "</p>\n" +										
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
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
					case "headphones": {
						DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Random rand = new Random();
						Date date = new Date();

						Headphones headphone = new Headphones();
						Headphones headphoneTmp = new Headphones();
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						HashMap<String, String> dbStatus = new HashMap<String, String>();
						dbStatus = dbObj.checkDBConnection();
						if(dbStatus.get("status").equals("true")) {
							for(Map.Entry<String, Headphones> w : dbObj.getHeadphoneMapDB().entrySet()) {					
								headphoneTmp = w.getValue();
								if(id.equals(headphoneTmp.getId()))
								{
									headphone = headphoneTmp;
									break;
								}
							}
							
							int  confirmationNumber = rand.nextInt(999999999) + 99999999;
							int noOfDays = 14; //i.e two weeks
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(date);            
							calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
							Date deliveryDate = calendar.getTime();
							double shippingPrice = 5.60;
							double subTotal = (double)qnty * (headphone.getPrice());
							double totalPrice = subTotal + shippingPrice;
							
							int postalCode = Integer.parseInt(request.getParameter("postalcode"));
							String[] prodTypeList = new String[] {type};
							String[] prodIDList = new String[] {headphone.getId()};
							int[] qntyList = new int[] {qnty};
							Orders order = new Orders(username,request.getParameter("customerName"),prodTypeList,prodIDList,qntyList,sdf.format(date),confirmationNumber,sdf.format(deliveryDate),shippingPrice,totalPrice,request.getParameter("warranty"),request.getParameter("country"),request.getParameter("state"),request.getParameter("address1"),request.getParameter("address2"),request.getParameter("city"),postalCode,request.getParameter("mobilenumber"),request.getParameter("emailaddress"));
							dbObj.writeOrders(order);

							engine.generateHtml("Header",request);
							
							displayOrderDetails(pWriter,order);
						}
						else {
							engine.generateHtml("Header",request);
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form class='register' action='' method='POST'>\n" +
									"<div class='form_container'>\n" +
										"<table id='ListTable'>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:center;'>\n" +
													"<p>" + dbStatus.get("msg") + "</p>\n" +										
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
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
					case "smartphones": {
						DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Random rand = new Random();
						Date date = new Date();

						Phones phone = new Phones();
						Phones phoneTmp = new Phones();
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						HashMap<String, String> dbStatus = new HashMap<String, String>();
						dbStatus = dbObj.checkDBConnection();
						if(dbStatus.get("status").equals("true")) {
							for(Map.Entry<String, Phones> w : dbObj.getPhoneMapDB().entrySet()) {					
								phoneTmp = w.getValue();
								if(id.equals(phoneTmp.getId()))
								{
									phone = phoneTmp;
									break;
								}
							}
							
							int  confirmationNumber = rand.nextInt(999999999) + 99999999;
							int noOfDays = 14; //i.e two weeks
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(date);            
							calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
							Date deliveryDate = calendar.getTime();
							double shippingPrice = 5.60;
							double subTotal = (double)qnty * (phone.getPrice());
							double totalPrice = subTotal + shippingPrice;
							
							int postalCode = Integer.parseInt(request.getParameter("postalcode"));
							String[] prodTypeList = new String[] {type};
							String[] prodIDList = new String[] {phone.getId()};
							int[] qntyList = new int[] {qnty};
							Orders order = new Orders(username,request.getParameter("customerName"),prodTypeList,prodIDList,qntyList,sdf.format(date),confirmationNumber,sdf.format(deliveryDate),shippingPrice,totalPrice,request.getParameter("warranty"),request.getParameter("country"),request.getParameter("state"),request.getParameter("address1"),request.getParameter("address2"),request.getParameter("city"),postalCode,request.getParameter("mobilenumber"),request.getParameter("emailaddress"));
							dbObj.writeOrders(order);
							engine.generateHtml("Header",request);
							
							displayOrderDetails(pWriter,order);
						}
						else {
							engine.generateHtml("Header",request);
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form class='register' action='' method='POST'>\n" +
									"<div class='form_container'>\n" +
										"<table id='ListTable'>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:center;'>\n" +
													"<p>" + dbStatus.get("msg") + "</p>\n" +										
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
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
					case "laptops": {
						DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Random rand = new Random();
						Date date = new Date();

						Laptops laptop = new Laptops();
						Laptops laptopTmp = new Laptops();
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						HashMap<String, String> dbStatus = new HashMap<String, String>();
						dbStatus = dbObj.checkDBConnection();
						if(dbStatus.get("status").equals("true")) {
							for(Map.Entry<String, Laptops> w : dbObj.getLaptopMapDB().entrySet()) {					
								laptopTmp = w.getValue();
								if(id.equals(laptopTmp.getId()))
								{
									laptop = laptopTmp;
									break;
								}
							}
							
							int  confirmationNumber = rand.nextInt(999999999) + 99999999;
							int noOfDays = 14; //i.e two weeks
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(date);            
							calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
							Date deliveryDate = calendar.getTime();
							double shippingPrice = 5.60;
							double subTotal = (double)qnty * (laptop.getPrice());
							double totalPrice = subTotal + shippingPrice;
							
							int postalCode = Integer.parseInt(request.getParameter("postalcode"));
							String[] prodTypeList = new String[] {type};
							String[] prodIDList = new String[] {laptop.getId()};
							int[] qntyList = new int[] {qnty};
							Orders order = new Orders(username,request.getParameter("customerName"),prodTypeList,prodIDList,qntyList,sdf.format(date),confirmationNumber,sdf.format(deliveryDate),shippingPrice,totalPrice,request.getParameter("warranty"),request.getParameter("country"),request.getParameter("state"),request.getParameter("address1"),request.getParameter("address2"),request.getParameter("city"),postalCode,request.getParameter("mobilenumber"),request.getParameter("emailaddress"));
							dbObj.writeOrders(order);
							engine.generateHtml("Header",request);
							
							displayOrderDetails(pWriter,order);
						}
						else {
							engine.generateHtml("Header",request);
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form class='register' action='' method='POST'>\n" +
									"<div class='form_container'>\n" +
										"<table id='ListTable'>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:center;'>\n" +
													"<p>" + dbStatus.get("msg") + "</p>\n" +										
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
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
					case "externalstorage": {
						DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Random rand = new Random();
						Date date = new Date();

						ExternalStorage storage = new ExternalStorage();
						ExternalStorage storageTmp = new ExternalStorage();
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						HashMap<String, String> dbStatus = new HashMap<String, String>();
						dbStatus = dbObj.checkDBConnection();
						if(dbStatus.get("status").equals("true")) {
							for(Map.Entry<String, ExternalStorage> w : dbObj.getStorageMapDB().entrySet()) {					
								storageTmp = w.getValue();
								if(id.equals(storageTmp.getId()))
								{
									storage = storageTmp;
									break;
								}
							}
							
							int  confirmationNumber = rand.nextInt(999999999) + 99999999;
							int noOfDays = 14; //i.e two weeks
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(date);            
							calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
							Date deliveryDate = calendar.getTime();
							double shippingPrice = 5.60;
							double subTotal = (double)qnty * (storage.getPrice());
							double totalPrice = subTotal + shippingPrice;
							
							int postalCode = Integer.parseInt(request.getParameter("postalcode"));
							String[] prodTypeList = new String[] {type};
							String[] prodIDList = new String[] {storage.getId()};
							int[] qntyList = new int[] {qnty};
							Orders order = new Orders(username,request.getParameter("customerName"),prodTypeList,prodIDList,qntyList,sdf.format(date),confirmationNumber,sdf.format(deliveryDate),shippingPrice,totalPrice,request.getParameter("warranty"),request.getParameter("country"),request.getParameter("state"),request.getParameter("address1"),request.getParameter("address2"),request.getParameter("city"),postalCode,request.getParameter("mobilenumber"),request.getParameter("emailaddress"));
							dbObj.writeOrders(order);
							engine.generateHtml("Header",request);
							
							displayOrderDetails(pWriter,order);
						}
						else {
							engine.generateHtml("Header",request);
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form class='register' action='' method='POST'>\n" +
									"<div class='form_container'>\n" +
										"<table id='ListTable'>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:center;'>\n" +
													"<p>" + dbStatus.get("msg") + "</p>\n" +										
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
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
					case "accessories": {
						DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Random rand = new Random();
						Date date = new Date();
						
						Accessories accessory = new Accessories();
						Accessories accessoryTmp = new Accessories();
						response.setContentType("text/html");
						PrintWriter pWriter = response.getWriter();
						HtmlEngine engine = new HtmlEngine(pWriter);
						HashMap<String, String> dbStatus = new HashMap<String, String>();
						dbStatus = dbObj.checkDBConnection();
						if(dbStatus.get("status").equals("true")) {
							for(Map.Entry<String, Accessories> w : dbObj.getAccessoryMapDB().entrySet()) {					
								accessoryTmp = w.getValue();
								if(id.equals(accessoryTmp.getId()))
								{
									accessory = accessoryTmp;
									break;
								}
							}
							
							int  confirmationNumber = rand.nextInt(999999999) + 99999999;
							int noOfDays = 14; //i.e two weeks
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(date);            
							calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
							Date deliveryDate = calendar.getTime();
							double shippingPrice = 5.60;
							double subTotal = (double)qnty * (accessory.getPrice());
							double totalPrice = subTotal + shippingPrice;
							
							int postalCode = Integer.parseInt(request.getParameter("postalcode"));
							String[] prodTypeList = new String[] {type};
							String[] prodIDList = new String[] {accessory.getId()};
							int[] qntyList = new int[] {qnty};
							Orders order = new Orders(username,request.getParameter("customerName"),prodTypeList,prodIDList,qntyList,sdf.format(date),confirmationNumber,sdf.format(deliveryDate),shippingPrice,totalPrice,request.getParameter("warranty"),request.getParameter("country"),request.getParameter("state"),request.getParameter("address1"),request.getParameter("address2"),request.getParameter("city"),postalCode,request.getParameter("mobilenumber"),request.getParameter("emailaddress"));
							dbObj.writeOrders(order);
							engine.generateHtml("Header",request);
							
							displayOrderDetails(pWriter,order);
						}
						else {
							engine.generateHtml("Header",request);
							pWriter.println("<div id='body' class='width'>\n" +
									"<section id='content'>\n" +
									"<div style='height:50px;'></div>\n" +
									"<form class='register' action='' method='POST'>\n" +
									"<div class='form_container'>\n" +
										"<table id='ListTable'>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:center;'>\n" +
													"<p>" + dbStatus.get("msg") + "</p>\n" +										
												"</td>\n" +
											"</tr>\n" +
											"<tr>\n" +
												"<td colspan='2' style='text-align:right;'>\n" +
													"<a href='/csj/Controller/LoginServlet?op=CustomerHome' method='get'>Customer Home</a>\n" +
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
					default: {
						
						break;
					}
				}
			}
		}
		else {
			response.sendRedirect("/csj/Controller/LoginServlet");
		}
	}
}