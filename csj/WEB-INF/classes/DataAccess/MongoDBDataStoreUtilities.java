package DataAccess;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import com.mongodb.client.*;
import com.mongodb.MongoClientOptions.Builder;

import java.io.*;
import java.util.*;
import java.text.*;
import java.net.*;
import JavaBeans.*;
import org.bson.*;
import DataAccess.MySQLDataStoreUtilities;

public class MongoDBDataStoreUtilities{
	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document>  reviewCol;
	
	public MongoDBDataStoreUtilities() {

	}
	
	public List<Review> getMostLikedProducts(){
		try {
			connectMongoDB();
			if(reviewCol.count()>0){
				MongoCursor<Document> cursor = reviewCol.find().sort(Filters.eq("reviewRating", -1)).limit(5).iterator();
				//reviewCol.find().sort({reviewRating:-1}).limit(5);
				
				//HashMap<String, Review> reviewsMap = new HashMap<String, Review>();
				List<Review> reviewsList = new Vector<Review>();
				Review rev = new Review();
				try {
					//int count=0;
					while (cursor.hasNext()) {
						Document doc = cursor.next();
						rev = new Review(doc.getString("productName"),doc.getString("productType"),doc.getDouble("productPrice"),doc.getString("retailer"),doc.getInteger("zipCode"),doc.getString("city"),doc.getString("state"),doc.getBoolean("productOnSale"),doc.getString("manufacturer"),doc.getBoolean("rebate"),doc.getString("username"),doc.getInteger("age"),doc.getString("gender"),doc.getString("occupation"),doc.getInteger("reviewRating"),doc.getString("reviewDate"),doc.getString("reviewText"));
						//reviewsMap.put(doc.getString("productName") + String.valueOf(count), rev);
						reviewsList.add(rev);
						//count++;
					}
				} 
				finally {
					cursor.close();
				}
				disconnectMongoDB();
				return reviewsList;
			}
			else {
				disconnectMongoDB();
				return null;
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public int[][] getMaxZipCodes(){
		try {
			connectMongoDB();
			int[][] res = new int[10][2];
			int i=0;
			if(reviewCol.count()>0){
				AggregateIterable<Document> iterable = reviewCol.aggregate(Arrays.asList(Aggregates.group("$zipCode", Accumulators.sum("ProductsSold", 1)),Aggregates.sort(com.mongodb.client.model.Sorts.orderBy(com.mongodb.client.model.Sorts.descending("ProductsSold"))),Aggregates.limit(5)));
				//HashMap<Integer, Integer> result = new HashMap<Integer, Integer>();
				for(Document row : iterable) {
					//System.out.println("ZipCode - " + row.getInteger("_id") + "ProductsSold - " + row.getInteger("ProductsSold"));
					//result.put(row.getInteger("_id"), row.getInteger("ProductsSold"));
					res[i][0] = row.getInteger("_id");
					res[i][1] = row.getInteger("ProductsSold");
					i++;
				}
				disconnectMongoDB();
				return res;
			}
			else {
				disconnectMongoDB();
				return null;
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public String[][] getMostSoldProducts(){
		try {
			connectMongoDB();
			String[][] res = new String[10][2];
			int i=0;
			if(reviewCol.count()>0){
				String productsSold = "productsSold";
				AggregateIterable<Document> iterable = reviewCol.aggregate(Arrays.asList(Aggregates.group("$productName", Accumulators.sum("ProductsSold", 1)),Aggregates.sort(com.mongodb.client.model.Sorts.orderBy(com.mongodb.client.model.Sorts.descending("ProductsSold"))),Aggregates.limit(5)));
				//HashMap<String, Integer> result = new HashMap<String, Integer>();
				for(Document row : iterable) {
					//System.out.println("ZipCode - " + row.getInteger("_id") + "ProductsSold - " + row.getInteger("ProductsSold"));
					//result.put(row.getString("_id"), row.getInteger("ProductsSold"));
					res[i][0] = row.getString("_id");
					res[i][1] = String.valueOf(row.getInteger("ProductsSold"));
					i++;
				}
				disconnectMongoDB();
				return res;
			}
			else {
				disconnectMongoDB();
				return null;
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	public void connectMongoDB(){
		try {
			mongoClient = new MongoClient( "localhost" , 11111 );
			database = mongoClient.getDatabase("ReviewsDatabase");
			reviewCol = database.getCollection("Reviews");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public MongoCollection<Document> getCollection(){
		try {
			mongoClient = new MongoClient( "localhost" , 11111 );
			database = mongoClient.getDatabase("ReviewsDatabase");
			reviewCol = database.getCollection("Reviews");			
			return reviewCol;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public void disconnectMongoDB(){
		try {
			mongoClient.close();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public HashMap<String, Review> getReviews(String productName){
		try {
			connectMongoDB();
			if(reviewCol.count()>0){
				MongoCursor<Document> cursor = reviewCol.find(Filters.eq("productName",productName)).iterator();
				HashMap<String, Review> reviewsMap = new HashMap<String, Review>();
				Review rev = new Review();
				DBObject resultElement = null;
				try {
					int count=0;
					while (cursor.hasNext()) {
						Document doc = cursor.next();
						//System.out.println("ProductName-" + productName);
						//System.out.println("doc-Prod-name -" + doc.getString("productName"));
						//NumberFormat formatter = new DecimalFormat("#0.00");  
				        //double Rating = Double.parseDouble(formatter.format(doc.getDouble("reviewRating")));
				        
						rev = new Review(doc.getString("productName"),doc.getString("productType"),doc.getDouble("productPrice"),doc.getString("retailer"),doc.getInteger("zipCode"),doc.getString("city"),doc.getString("state"),doc.getBoolean("productOnSale"),doc.getString("manufacturer"),doc.getBoolean("rebate"),doc.getString("username"),doc.getInteger("age"),doc.getString("gender"),doc.getString("occupation"),doc.getInteger("reviewRating"),doc.getString("reviewDate"),doc.getString("reviewText"));
						reviewsMap.put(doc.getString("productName") + String.valueOf(count), rev);
						count++;
					}
				} 
				finally {
					cursor.close();
				}
				disconnectMongoDB();
				return reviewsMap;
			}
			else {
				disconnectMongoDB();
				return null;
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public HashMap<String, String> checkMongoDB(){
		HashMap<String, String> dbStatus = new HashMap<String, String>();
		try {
			Builder builder = MongoClientOptions.builder().connectTimeout(3000);  
			builder.connectTimeout(300);		    
		    builder.socketTimeout(300);		    
		    builder.serverSelectionTimeout(300);
			//MongoClient mongo = new MongoClient(new ServerAddress("localhost", 27017), builder.build());
			
			//mongoClient = new MongoClient( "localhost" , 27017 );
			mongoClient = new MongoClient(new ServerAddress("localhost", 27017), builder.build());
			try {
				mongoClient.getAddress();
			}
			catch(Exception ex) {
				dbStatus.put("status","false");
		        dbStatus.put("msg", "MongoDB server is not up and running");	
		        mongoClient.close();
				return dbStatus;
			}
			mongoClient.close();
			dbStatus.put("status","true");
	        dbStatus.put("msg", "Connected");
	        return dbStatus;
		}
		catch(com.mongodb.MongoSocketOpenException ex) {
			dbStatus.put("status","false");
	        dbStatus.put("msg", "MongoDB server is not up and running");	
	        mongoClient.close();
			return dbStatus;
		}
		catch(MongoException ex) {
			dbStatus.put("status","false");
	        dbStatus.put("msg", "MongoDB server is not up and running");		
	        mongoClient.close();
			return dbStatus;
		}
		catch(Exception ex) {
			dbStatus.put("status","false");
	        dbStatus.put("msg", ex.getMessage());	
	        mongoClient.close();
			return dbStatus;
		}
	}
	
	public boolean writeReview(Review review) {
		try {
			connectMongoDB();
			//database.createColMongoCollection<TDocument>sCollection");
			Document doc = new Document("productName", review.getProductName())
	                .append("productType", review.getProductType())
	                .append("productPrice", review.getprice())
	                .append("retailer", review.getRetailer())
	                .append("zipCode", review.getZipCode())
	                .append("city", review.getCity())
	                .append("state", review.getState())
	                .append("productOnSale", review.getProductOnSale())
	                .append("manufacturer", review.getManufacturer())
	                .append("rebate", review.getRebate())
	                .append("username", review.getUsername())
	                .append("age", review.getAge())
	                .append("gender", review.getGender())
	                .append("occupation", review.getOccupation())
	                .append("reviewRating", review.getReviewRating())
	                .append("reviewDate", review.getReviewDate())
	                .append("reviewText", review.getReviewText());
			reviewCol.insertOne(doc);
			disconnectMongoDB();
			return true;
		}
		
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	public void loadSampleReviews() {
		MySQLDataStoreUtilities dbObj = new MySQLDataStoreUtilities();
		SmartWatch watchTmp = new SmartWatch();
		Speakers speaker = new Speakers();
		Headphones headphone = new Headphones();
		Phones phone = new Phones();
		Laptops laptop = new Laptops();
		ExternalStorage storage = new ExternalStorage();
		Accessories accessory = new Accessories();
		
		connectMongoDB();
		int count=0;
		for(Map.Entry<String, SmartWatch> w : dbObj.getWatchMapDB().entrySet()) {	
			watchTmp = w.getValue();
			Random rand = new Random();
			int min = 60601;
			int max = 60616;
			int randomZip = rand.nextInt((max - min) + 1) + min;
			
			String[] cities = {"New York","Washington D.C","San Francisco","Chicago","Boston","Log Angeles","Seattle","San Diego","Austin","Philadelphia","New Orleans","Portland","Nashville","Houston","Denver","Miami","Detroit","Dallas","Las Vegas","Phoenix","San Jose","Minneapolis","Atlanta","Indianapolis","Orlando","Pittsburgh","Madison"};
	        List<String> names = Arrays.asList(cities);
	        int index = new Random().nextInt(names.size());
	        String cityName = names.get(index);
	        
	        String[] states = {"California","Texas","Georgia","Tennessee","Ohio","Indiana","Arizona","Virginia","Florida","Alabama","Missouri","Massachusetts","Pennsylvania","Wisconsin","Arkansas","Alaska","Utah","Illinois","Minnesota","New Jersey","South Carolina","West Virginia","Hawaii","Michigan"};
	        List<String> stateNames = Arrays.asList(states);
	        int stateIndex = new Random().nextInt(stateNames.size());
	        String stateName = stateNames.get(stateIndex);
	        
	        int userIndex = new Random().nextInt(100);
	        
	        rand = new Random();
	        int minAge = 12;
			int maxAge = 80;
			int age = rand.nextInt((maxAge - minAge) + 1) + minAge;
			
			String[] genders = {"Male","Female"};
	        List<String> gendersList = Arrays.asList(genders);
	        int genderIndex = new Random().nextInt(gendersList.size());
	        String gender = gendersList.get(genderIndex);
	        
	        String[] occupations = {"Financial Adviser","Therapist","Dental Hygienist","Lawyer","Software Developer","Web Developer","Mechanical Engineer","Pharmacist","Dentist","Physician","Civil Engineer","DBA","Accountant"};
	        List<String> OccupationList = Arrays.asList(occupations);
	        int occupationIndex = new Random().nextInt(OccupationList.size());
	        String occupation = OccupationList.get(occupationIndex);
	        
	        rand = new Random();
	        int minRating = 1;
	        int maxRating = 5;
	        int randomRating = rand.nextInt((maxRating - minRating) + 1) + minRating;
	        
	        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	        Date curdate = new Date();
	        dateFormat.format(curdate);
	        
			Document doc = new Document("productName", watchTmp.getName())
	                .append("productType", "SmartWatch")
	                .append("productPrice", watchTmp.getPrice())
	                .append("retailer", watchTmp.getRetailer())
	                .append("zipCode", randomZip)
	                .append("city", cityName)
	                .append("state", stateName)
	                .append("productOnSale", true)
	                .append("manufacturer", watchTmp.getManufacturer())
	                .append("rebate", false)
	                .append("username", "user" + String.valueOf(userIndex))
	                .append("age", age)
	                .append("gender", gender)
	                .append("occupation", occupation)
	                .append("reviewRating", randomRating)
	                .append("reviewDate", dateFormat.format(curdate))
	                .append("reviewText", "SmartWatch - " + watchTmp.getName() + " works great. Very nice product.");
			reviewCol.insertOne(doc);
		}
		
		for(Map.Entry<String, Speakers> w : dbObj.getSpeakerMapDB().entrySet()) {					
			speaker = w.getValue();
			Random rand = new Random();
			int min = 60601;
			int max = 60616;
			int randomZip = rand.nextInt((max - min) + 1) + min;
			
			String[] cities = {"New York","Washington D.C","San Francisco","Chicago","Boston","Log Angeles","Seattle","San Diego","Austin","Philadelphia","New Orleans","Portland","Nashville","Houston","Denver","Miami","Detroit","Dallas","Las Vegas","Phoenix","San Jose","Minneapolis","Atlanta","Indianapolis","Orlando","Pittsburgh","Madison"};
	        List<String> names = Arrays.asList(cities);
	        int index = new Random().nextInt(names.size());
	        String cityName = names.get(index);
	        
	        String[] states = {"California","Texas","Georgia","Tennessee","Ohio","Indiana","Arizona","Virginia","Florida","Alabama","Missouri","Massachusetts","Pennsylvania","Wisconsin","Arkansas","Alaska","Utah","Illinois","Minnesota","New Jersey","South Carolina","West Virginia","Hawaii","Michigan"};
	        List<String> stateNames = Arrays.asList(states);
	        int stateIndex = new Random().nextInt(stateNames.size());
	        String stateName = stateNames.get(stateIndex);
	        
	        int userIndex = new Random().nextInt(100);
	        
	        rand = new Random();
	        int minAge = 12;
			int maxAge = 80;
			int age = rand.nextInt((maxAge - minAge) + 1) + minAge;
			
			String[] genders = {"Male","Female"};
	        List<String> gendersList = Arrays.asList(genders);
	        int genderIndex = new Random().nextInt(gendersList.size());
	        String gender = gendersList.get(genderIndex);
	        
	        String[] occupations = {"Financial Adviser","Therapist","Dental Hygienist","Lawyer","Software Developer","Web Developer","Mechanical Engineer","Pharmacist","Dentist","Physician","Civil Engineer","DBA","Accountant"};
	        List<String> OccupationList = Arrays.asList(occupations);
	        int occupationIndex = new Random().nextInt(OccupationList.size());
	        String occupation = OccupationList.get(occupationIndex);
	        
	        rand = new Random();
	        int minRating = 1;
	        int maxRating = 5;
	        int randomRating = rand.nextInt((maxRating - minRating) + 1) + minRating;
	        
	        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	        Date curdate = new Date();
	        dateFormat.format(curdate);
	        
			Document doc = new Document("productName", speaker.getName())
	                .append("productType", "Speakers")
	                .append("productPrice", speaker.getPrice())
	                .append("retailer", speaker.getRetailer())
	                .append("zipCode", randomZip)
	                .append("city", cityName)
	                .append("state", stateName)
	                .append("productOnSale", true)
	                .append("manufacturer", speaker.getManufacturer())
	                .append("rebate", false)
	                .append("username", "user" + String.valueOf(userIndex))
	                .append("age", age)
	                .append("gender", gender)
	                .append("occupation", occupation)
	                .append("reviewRating", randomRating)
	                .append("reviewDate", dateFormat.format(curdate))
	                .append("reviewText", "Speaker - " + speaker.getName() + " works great. Very nice product.");
			reviewCol.insertOne(doc);			
		}
		
		for(Map.Entry<String, Headphones> w : dbObj.getHeadphoneMapDB().entrySet()) {					
			headphone = w.getValue();
			Random rand = new Random();
			int min = 60601;
			int max = 60616;
			int randomZip = rand.nextInt((max - min) + 1) + min;
			
			String[] cities = {"New York","Washington D.C","San Francisco","Chicago","Boston","Log Angeles","Seattle","San Diego","Austin","Philadelphia","New Orleans","Portland","Nashville","Houston","Denver","Miami","Detroit","Dallas","Las Vegas","Phoenix","San Jose","Minneapolis","Atlanta","Indianapolis","Orlando","Pittsburgh","Madison"};
	        List<String> names = Arrays.asList(cities);
	        int index = new Random().nextInt(names.size());
	        String cityName = names.get(index);
	        
	        String[] states = {"California","Texas","Georgia","Tennessee","Ohio","Indiana","Arizona","Virginia","Florida","Alabama","Missouri","Massachusetts","Pennsylvania","Wisconsin","Arkansas","Alaska","Utah","Illinois","Minnesota","New Jersey","South Carolina","West Virginia","Hawaii","Michigan"};
	        List<String> stateNames = Arrays.asList(states);
	        int stateIndex = new Random().nextInt(stateNames.size());
	        String stateName = stateNames.get(stateIndex);
	        
	        int userIndex = new Random().nextInt(100);
	        
	        rand = new Random();
	        int minAge = 12;
			int maxAge = 80;
			int age = rand.nextInt((maxAge - minAge) + 1) + minAge;
			
			String[] genders = {"Male","Female"};
	        List<String> gendersList = Arrays.asList(genders);
	        int genderIndex = new Random().nextInt(gendersList.size());
	        String gender = gendersList.get(genderIndex);
	        
	        String[] occupations = {"Financial Adviser","Therapist","Dental Hygienist","Lawyer","Software Developer","Web Developer","Mechanical Engineer","Pharmacist","Dentist","Physician","Civil Engineer","DBA","Accountant"};
	        List<String> OccupationList = Arrays.asList(occupations);
	        int occupationIndex = new Random().nextInt(OccupationList.size());
	        String occupation = OccupationList.get(occupationIndex);
	        
	        rand = new Random();
	        int minRating = 1;
	        int maxRating = 5;
	        int randomRating = rand.nextInt((maxRating - minRating) + 1) + minRating;
	        
	        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	        Date curdate = new Date();
	        dateFormat.format(curdate);
	        
			Document doc = new Document("productName", headphone.getName())
	                .append("productType", "Headphones")
	                .append("productPrice", headphone.getPrice())
	                .append("retailer", headphone.getRetailer())
	                .append("zipCode", randomZip)
	                .append("city", cityName)
	                .append("state", stateName)
	                .append("productOnSale", true)
	                .append("manufacturer", headphone.getManufacturer())
	                .append("rebate", false)
	                .append("username", "user" + String.valueOf(userIndex))
	                .append("age", age)
	                .append("gender", gender)
	                .append("occupation", occupation)
	                .append("reviewRating", randomRating)
	                .append("reviewDate", dateFormat.format(curdate))
	                .append("reviewText", "Headphone - " + headphone.getName() + " works great. Very nice product.");
			reviewCol.insertOne(doc);			
		}
		
		for(Map.Entry<String, Phones> w : dbObj.getPhoneMapDB().entrySet()) {					
			phone = w.getValue();
			Random rand = new Random();
			int min = 60601;
			int max = 60616;
			int randomZip = rand.nextInt((max - min) + 1) + min;
			
			String[] cities = {"New York","Washington D.C","San Francisco","Chicago","Boston","Log Angeles","Seattle","San Diego","Austin","Philadelphia","New Orleans","Portland","Nashville","Houston","Denver","Miami","Detroit","Dallas","Las Vegas","Phoenix","San Jose","Minneapolis","Atlanta","Indianapolis","Orlando","Pittsburgh","Madison"};
	        List<String> names = Arrays.asList(cities);
	        int index = new Random().nextInt(names.size());
	        String cityName = names.get(index);
	        
	        String[] states = {"California","Texas","Georgia","Tennessee","Ohio","Indiana","Arizona","Virginia","Florida","Alabama","Missouri","Massachusetts","Pennsylvania","Wisconsin","Arkansas","Alaska","Utah","Illinois","Minnesota","New Jersey","South Carolina","West Virginia","Hawaii","Michigan"};
	        List<String> stateNames = Arrays.asList(states);
	        int stateIndex = new Random().nextInt(stateNames.size());
	        String stateName = stateNames.get(stateIndex);
	        
	        int userIndex = new Random().nextInt(100);
	        
	        rand = new Random();
	        int minAge = 12;
			int maxAge = 80;
			int age = rand.nextInt((maxAge - minAge) + 1) + minAge;
			
			String[] genders = {"Male","Female"};
	        List<String> gendersList = Arrays.asList(genders);
	        int genderIndex = new Random().nextInt(gendersList.size());
	        String gender = gendersList.get(genderIndex);
	        
	        String[] occupations = {"Financial Adviser","Therapist","Dental Hygienist","Lawyer","Software Developer","Web Developer","Mechanical Engineer","Pharmacist","Dentist","Physician","Civil Engineer","DBA","Accountant"};
	        List<String> OccupationList = Arrays.asList(occupations);
	        int occupationIndex = new Random().nextInt(OccupationList.size());
	        String occupation = OccupationList.get(occupationIndex);
	        
	        rand = new Random();
	        int minRating = 1;
	        int maxRating = 5;
	        int randomRating = rand.nextInt((maxRating - minRating) + 1) + minRating;
	        
	        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	        Date curdate = new Date();
	        dateFormat.format(curdate);
	        
			Document doc = new Document("productName", phone.getName())
	                .append("productType", "Smartphones")
	                .append("productPrice", phone.getPrice())
	                .append("retailer", phone.getRetailer())
	                .append("zipCode", randomZip)
	                .append("city", cityName)
	                .append("state", stateName)
	                .append("productOnSale", true)
	                .append("manufacturer", phone.getManufacturer())
	                .append("rebate", false)
	                .append("username", "user" + String.valueOf(userIndex))
	                .append("age", age)
	                .append("gender", gender)
	                .append("occupation", occupation)
	                .append("reviewRating", randomRating)
	                .append("reviewDate", dateFormat.format(curdate))
	                .append("reviewText", "Smartphone - " + phone.getName() + " works great. Very nice product.");
			reviewCol.insertOne(doc);			
		}
		
		for(Map.Entry<String, Laptops> w : dbObj.getLaptopMapDB().entrySet()) {					
			laptop = w.getValue();
			Random rand = new Random();
			int min = 60601;
			int max = 60616;
			int randomZip = rand.nextInt((max - min) + 1) + min;
			
			String[] cities = {"New York","Washington D.C","San Francisco","Chicago","Boston","Log Angeles","Seattle","San Diego","Austin","Philadelphia","New Orleans","Portland","Nashville","Houston","Denver","Miami","Detroit","Dallas","Las Vegas","Phoenix","San Jose","Minneapolis","Atlanta","Indianapolis","Orlando","Pittsburgh","Madison"};
	        List<String> names = Arrays.asList(cities);
	        int index = new Random().nextInt(names.size());
	        String cityName = names.get(index);
	        
	        String[] states = {"California","Texas","Georgia","Tennessee","Ohio","Indiana","Arizona","Virginia","Florida","Alabama","Missouri","Massachusetts","Pennsylvania","Wisconsin","Arkansas","Alaska","Utah","Illinois","Minnesota","New Jersey","South Carolina","West Virginia","Hawaii","Michigan"};
	        List<String> stateNames = Arrays.asList(states);
	        int stateIndex = new Random().nextInt(stateNames.size());
	        String stateName = stateNames.get(stateIndex);
	        
	        int userIndex = new Random().nextInt(100);
	        
	        rand = new Random();
	        int minAge = 12;
			int maxAge = 80;
			int age = rand.nextInt((maxAge - minAge) + 1) + minAge;
			
			String[] genders = {"Male","Female"};
	        List<String> gendersList = Arrays.asList(genders);
	        int genderIndex = new Random().nextInt(gendersList.size());
	        String gender = gendersList.get(genderIndex);
	        
	        String[] occupations = {"Financial Adviser","Therapist","Dental Hygienist","Lawyer","Software Developer","Web Developer","Mechanical Engineer","Pharmacist","Dentist","Physician","Civil Engineer","DBA","Accountant"};
	        List<String> OccupationList = Arrays.asList(occupations);
	        int occupationIndex = new Random().nextInt(OccupationList.size());
	        String occupation = OccupationList.get(occupationIndex);
	        
	        rand = new Random();
	        int minRating = 1;
	        int maxRating = 5;
	        int randomRating = rand.nextInt((maxRating - minRating) + 1) + minRating;
	        
	        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	        Date curdate = new Date();
	        dateFormat.format(curdate);
	        
			Document doc = new Document("productName", laptop.getName())
	                .append("productType", "Laptops")
	                .append("productPrice", laptop.getPrice())
	                .append("retailer", laptop.getRetailer())
	                .append("zipCode", randomZip)
	                .append("city", cityName)
	                .append("state", stateName)
	                .append("productOnSale", true)
	                .append("manufacturer", laptop.getManufacturer())
	                .append("rebate", false)
	                .append("username", "user" + String.valueOf(userIndex))
	                .append("age", age)
	                .append("gender", gender)
	                .append("occupation", occupation)
	                .append("reviewRating", randomRating)
	                .append("reviewDate", dateFormat.format(curdate))
	                .append("reviewText", "Laptop - " + laptop.getName() + " works great. Very nice product.");
			reviewCol.insertOne(doc);
		}
		
		for(Map.Entry<String, ExternalStorage> w : dbObj.getStorageMapDB().entrySet()) {					
			storage = w.getValue();
			Random rand = new Random();
			int min = 60601;
			int max = 60616;
			int randomZip = rand.nextInt((max - min) + 1) + min;
			
			String[] cities = {"New York","Washington D.C","San Francisco","Chicago","Boston","Log Angeles","Seattle","San Diego","Austin","Philadelphia","New Orleans","Portland","Nashville","Houston","Denver","Miami","Detroit","Dallas","Las Vegas","Phoenix","San Jose","Minneapolis","Atlanta","Indianapolis","Orlando","Pittsburgh","Madison"};
	        List<String> names = Arrays.asList(cities);
	        int index = new Random().nextInt(names.size());
	        String cityName = names.get(index);
	        
	        String[] states = {"California","Texas","Georgia","Tennessee","Ohio","Indiana","Arizona","Virginia","Florida","Alabama","Missouri","Massachusetts","Pennsylvania","Wisconsin","Arkansas","Alaska","Utah","Illinois","Minnesota","New Jersey","South Carolina","West Virginia","Hawaii","Michigan"};
	        List<String> stateNames = Arrays.asList(states);
	        int stateIndex = new Random().nextInt(stateNames.size());
	        String stateName = stateNames.get(stateIndex);
	        
	        int userIndex = new Random().nextInt(100);
	        
	        rand = new Random();
	        int minAge = 12;
			int maxAge = 80;
			int age = rand.nextInt((maxAge - minAge) + 1) + minAge;
			
			String[] genders = {"Male","Female"};
	        List<String> gendersList = Arrays.asList(genders);
	        int genderIndex = new Random().nextInt(gendersList.size());
	        String gender = gendersList.get(genderIndex);
	        
	        String[] occupations = {"Financial Adviser","Therapist","Dental Hygienist","Lawyer","Software Developer","Web Developer","Mechanical Engineer","Pharmacist","Dentist","Physician","Civil Engineer","DBA","Accountant"};
	        List<String> OccupationList = Arrays.asList(occupations);
	        int occupationIndex = new Random().nextInt(OccupationList.size());
	        String occupation = OccupationList.get(occupationIndex);
	        
	        rand = new Random();
	        int minRating = 1;
	        int maxRating = 5;
	        int randomRating = rand.nextInt((maxRating - minRating) + 1) + minRating;
	        
	        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	        Date curdate = new Date();
	        dateFormat.format(curdate);
	        
			Document doc = new Document("productName", storage.getName())
	                .append("productType", "ExternalStorage")
	                .append("productPrice", storage.getPrice())
	                .append("retailer", storage.getRetailer())
	                .append("zipCode", randomZip)
	                .append("city", cityName)
	                .append("state", stateName)
	                .append("productOnSale", true)
	                .append("manufacturer", storage.getManufacturer())
	                .append("rebate", false)
	                .append("username", "user" + String.valueOf(userIndex))
	                .append("age", age)
	                .append("gender", gender)
	                .append("occupation", occupation)
	                .append("reviewRating", randomRating)
	                .append("reviewDate", dateFormat.format(curdate))
	                .append("reviewText", "ExternalStorage - " + storage.getName() + " works great. Very nice product.");
			reviewCol.insertOne(doc);
		}
		
		for(Map.Entry<String, Accessories> w : dbObj.getAccessoryMapDB().entrySet()) {					
			accessory = w.getValue();
			Random rand = new Random();
			int min = 60601;
			int max = 60616;
			int randomZip = rand.nextInt((max - min) + 1) + min;
			
			String[] cities = {"New York","Washington D.C","San Francisco","Chicago","Boston","Log Angeles","Seattle","San Diego","Austin","Philadelphia","New Orleans","Portland","Nashville","Houston","Denver","Miami","Detroit","Dallas","Las Vegas","Phoenix","San Jose","Minneapolis","Atlanta","Indianapolis","Orlando","Pittsburgh","Madison"};
	        List<String> names = Arrays.asList(cities);
	        int index = new Random().nextInt(names.size());
	        String cityName = names.get(index);
	        
	        String[] states = {"California","Texas","Georgia","Tennessee","Ohio","Indiana","Arizona","Virginia","Florida","Alabama","Missouri","Massachusetts","Pennsylvania","Wisconsin","Arkansas","Alaska","Utah","Illinois","Minnesota","New Jersey","South Carolina","West Virginia","Hawaii","Michigan"};
	        List<String> stateNames = Arrays.asList(states);
	        int stateIndex = new Random().nextInt(stateNames.size());
	        String stateName = stateNames.get(stateIndex);
	        
	        int userIndex = new Random().nextInt(100);
	        
	        rand = new Random();
	        int minAge = 12;
			int maxAge = 80;
			int age = rand.nextInt((maxAge - minAge) + 1) + minAge;
			
			String[] genders = {"Male","Female"};
	        List<String> gendersList = Arrays.asList(genders);
	        int genderIndex = new Random().nextInt(gendersList.size());
	        String gender = gendersList.get(genderIndex);
	        
	        String[] occupations = {"Financial Adviser","Therapist","Dental Hygienist","Lawyer","Software Developer","Web Developer","Mechanical Engineer","Pharmacist","Dentist","Physician","Civil Engineer","DBA","Accountant"};
	        List<String> OccupationList = Arrays.asList(occupations);
	        int occupationIndex = new Random().nextInt(OccupationList.size());
	        String occupation = OccupationList.get(occupationIndex);
	        
	        rand = new Random();
	        int minRating = 1;
	        int maxRating = 5;
	        int randomRating = rand.nextInt((maxRating - minRating) + 1) + minRating;
	        
	        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	        Date curdate = new Date();
	        dateFormat.format(curdate);
	        
			Document doc = new Document("productName", accessory.getName())
	                .append("productType", "Accessories")
	                .append("productPrice", accessory.getPrice())
	                .append("retailer", accessory.getRetailer())
	                .append("zipCode", randomZip)
	                .append("city", cityName)
	                .append("state", stateName)
	                .append("productOnSale", true)
	                .append("manufacturer", accessory.getManufacturer())
	                .append("rebate", false)
	                .append("username", "user" + String.valueOf(userIndex))
	                .append("age", age)
	                .append("gender", gender)
	                .append("occupation", occupation)
	                .append("reviewRating", randomRating)
	                .append("reviewDate", dateFormat.format(curdate))
	                .append("reviewText", "Accessory - " + storage.getName() + " works great. Very nice product.");
			reviewCol.insertOne(doc);
		}
		
		disconnectMongoDB();
	}
	
}