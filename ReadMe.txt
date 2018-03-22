Smart Portables Web Application ReadMe v5.0
--------------------------------------
As part of this version update, Below mentioned servlet and python script files have been added to the system.

Deal Matches Feature:
---------------------
The deal match component uses DealMatches.java servlet and the provided python script to extract deals from twitter, cross check them with products in the MySQL server and write them into a text file - DealMatches.txt.
The DealMatches.java servlet will be used to check and display deal offers and relevant product deals on the home page of Smart Portables Web Application.

/csj/DealMatches.txt
/csj/Assignment5BestBuyDeals.ipynb
/csj/WEB-INF/classes/Controller/DealMatches.java

Auto-Complete Feature:
----------------------
autoCompleteJS.js javascript file along with AjaxUtility.java servlet have been used to implement the auto complete feature in this system.
File Locations:
/csj/autoCompleteJS.js
/csj/WEB-INF/classes/Controller/AjaxUtility.java

ChoroPleth Maps Implementation:
-------------------------------
Below mentioned javascript files and servlets have been used to implement the choropleth maps in this system under Data Exploration tab of Store Manager role.

/csj/choroPleth.js
/csj/choroPlethAvgPrices.js
/csj/choroPlethTotalSales.js
/csj/choroPlethTotPrices.js
/csj/choroPlethTotRev5.js

/csj/WEB-INF/classes/Controller/DataExplorationUtility.java
/csj/WEB-INF/classes/Controller/DataExplorationServlet.java

All class files within this project has been divided across 3 vital tiers.
Tier-1 : Controller
-------------------
All classes essential for displaying HTML UI of the web application are within this tier.
Controller directory is as follows.
Directory : /csj/WEB-INF/classes/Controller/

Tier-2 : DataAccess
-------------------
All classes essential for writing/reading data to or from the database are included within this tier.
Directory: /csj/WEB-INF/classes/DataAccess/

Tier-3 : JavaBeans
-------------------
All JavaBeans essential for storing and retrieving data objects are located within this tier.
Directory: /csj/WEB-INF/classes/JavaBeans/

Other Files:
------------
All necessary css and javascript files are located within the root csj directory.
This web application is configured to use css/javascript and ProductCatalog.xml files from below
application directories. The application may get errors if any of these files are missing.

CSS Directory: /csj/
ProductCatalog Directory: /csj/WEB-INF/classes/ProductCatalog.xml

-------------
Database:
-------------
Please edit the below mentioned variables within csj/WEB-INF/classes/DataAccess/MySQLDataStoreUtilities.java in order to connect to the database.

String conString = "jdbc:mysql://localhost:1111/SmartPortablesDB?useSSL=false";
String username = "";
String pwd = "";

A customer account with below mentioned credentials will be created automatically to insert sample orders into the database.
	username - user1
	pwd - user1

All DDL statements used within this application are provided in "DDL Statements.txt".
All 3 necessary libraries are included within csj/WEB-INF/lib directory.

The application is designed to detect and create database and tables if they do not exist within mysql database.
Path must be set for the below mentioned jar files in order for the application to run properly.

gson-2.8.2.jar
mongo-java-driver-3.2.2.jar
mysql-connector-java-5.1.39-bin.jar

----------------
Compilation:
----------------
Browse to the directory - /csj/WEB-INF/classes/ and use the following command to compile any of the class files included within this application.
Example: 
/csj/WEB-INF/classes/>javac Controller/HomeServlet.java