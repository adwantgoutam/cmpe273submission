package hello;


import java.net.UnknownHostException;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.validation.Valid;
import javax.xml.ws.Response;

import org.bson.BasicBSONObject;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;

@RestController
public class DigitalWalletController  {
	
	
	// 1st API
	@RequestMapping(value="/api/v1/users",method=RequestMethod.POST)
	@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
	public User getUser(@Valid @RequestBody User user) throws UnknownHostException
	 {
		
		User newUser = new User( user.getEmail(),user.getPassword(),new Date().toGMTString());
		//user1.add(newUser);	
		newUser.setUser_id("u-"+String.valueOf(User.getI()));
		//user1.add(newUser);
		
		 MongoClientURI uri  = new MongoClientURI("mongodb://goutam:1215@ds045970.mongolab.com:45970/adwant"); 
		 MongoClient client = new MongoClient(uri);
		DB db = client.getDB("adwant");
			
		DBCollection coll = db.getCollection("customer");
		
		BasicDBObject doc = new BasicDBObject();
		doc.put("_id",newUser.getUser_id());
		doc.put("email",user.getEmail());
		doc.put("password",user.getPassword());
		doc.put("name",user.getName());
		doc.put("created_at",new Date().toGMTString() );
		coll.insert(doc);
		
		
		return newUser;
	 }
	
	//2nd API	
	@RequestMapping(value="/api/v1/users/{user_id}",method=RequestMethod.GET)	
	@ResponseStatus(org.springframework.http.HttpStatus.OK)
	public User viewUser(@PathVariable(value="user_id") String user_id) throws UnknownHostException{	
		
		    	User user = new User();
		    	MongoClientURI uri  = new MongoClientURI("mongodb://goutam:1215@ds045970.mongolab.com:45970/adwant"); 
				 MongoClient client = new MongoClient(uri);
				DB db = client.getDB("adwant");
					
				DBCollection coll = db.getCollection("customer");
				BasicDBObject query = new BasicDBObject("_id", user_id);
				DBCursor cursor = coll.find(query);
				try {
				   while(cursor.hasNext() ) {
					   DBObject obj = cursor.next();
					   user.setUser_id((String) obj.get("_id"));
					   user.setEmail((String) obj.get("email"));
					   user.setPassword((String) obj.get("password"));
					   user.setName((String) obj.get("name"));
					   user.setCreated_at((String) obj.get("created_at"));
					   
				   }
				} finally {
				   cursor.close();
				}
		   return user;
	}
	
	
	
//3rd API	 
	 
	@RequestMapping(value="/api/v1/users/{user_id}",method=RequestMethod.PUT) 
	@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
	public User updateUser(@Valid @RequestBody User user,@PathVariable(value="user_id") String user_id) throws UnknownHostException
	{
		  MongoClientURI uri  = new MongoClientURI("mongodb://goutam:1215@ds045970.mongolab.com:45970/adwant"); 
		  MongoClient client = new MongoClient(uri);
		  DB db = client.getDB("adwant");
			DBCollection coll = db.getCollection("customer");
			   
			BasicDBObject query = new BasicDBObject("_id", user_id);
			DBCursor cursor = coll.find(query);
			try {
				   while(cursor.hasNext() ) {
					   DBObject obj = cursor.next();
					   //obj.put("_id", user.getUser_id());
					   obj.put("email",user.getEmail());
					   obj.put("password",user.getPassword());
					   //obj.put("name",user.getName());
					   //obj.put("created_at",user.getCreated_at());
					   user.setUser_id((String) obj.get("_id"));
					   user.setEmail((String) obj.get("email"));
					   user.setPassword((String) obj.get("password"));
					   user.setName((String) obj.get("name"));
					   user.setCreated_at((String) obj.get("created_at"));
					   coll.save(obj);
				   }
				} finally {
				   cursor.close();
				}
     	
		return user;
	}
	
//4TH API	


@RequestMapping(value="/api/v1/users/{user_id}/idcards",method=RequestMethod.POST)
@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
	
	public IDCard getCard(@PathVariable(value="user_id") String user_id,@Valid @RequestBody IDCard idcard) throws UnknownHostException
	 {
		User user = new User();
		IDCard newUser = new IDCard( idcard.getCard_name(),idcard.getCard_number(),new Date().toLocaleString());
		newUser.setCard_id("c-"+String.valueOf(IDCard.getI()));
		
		
		  MongoClientURI uri  = new MongoClientURI("mongodb://goutam:1215@ds045970.mongolab.com:45970/adwant"); 
		  MongoClient client = new MongoClient(uri);
		  DB db = client.getDB("adwant");
			
		DBCollection coll = db.getCollection("customer");
		BasicDBObject query = new BasicDBObject("_id", user_id);
		DBCursor cursor = coll.find(query);
		//System.out.println(cursor.next());
		while(cursor.hasNext())
		{
			BasicDBObject idoc = new BasicDBObject();
			idoc.put("_id",newUser.getCard_id());
			idoc.put("card_name",newUser.getCard_name());
			idoc.put("card_number",newUser.getCard_number());
			idoc.put("expiration_date",newUser.getExpiration_date());
			DBObject obj=cursor.next();
			if(obj.get("idcards")==null)
			{
				ArrayList list = new ArrayList();
				list.add(idoc);
				obj.put("idcards",list);
			}
			else
			{
				ArrayList list=(ArrayList) obj.get("idcards");
				list.add(idoc);
				obj.put("idcards",list);
			}
			coll.save(obj);
		}
		
		return newUser;
		
				    }
//5THAPI

@RequestMapping(value="/api/v1/users/{user_id}/idcards",method=RequestMethod.GET)
@ResponseStatus(org.springframework.http.HttpStatus.OK)

public ArrayList retrieveCard(@PathVariable(value="user_id") String user_id) throws UnknownHostException
 {
	User user = new User();
	  MongoClientURI uri  = new MongoClientURI("mongodb://goutam:1215@ds045970.mongolab.com:45970/adwant"); 
	  MongoClient client = new MongoClient(uri);
	  DB db = client.getDB("adwant");
	ArrayList list= new ArrayList();
	DBCollection coll = db.getCollection("customer");
	BasicDBObject query = new BasicDBObject("_id", user_id);
	DBCursor cursor = coll.find(query);
	try {
	   while(cursor.hasNext() ) {
		   DBObject obj = cursor.next();
		   list= (ArrayList) obj.get("idcards");
	   }
	} finally {
	   cursor.close();
	}
	return list;
}	

//6THAPI

@RequestMapping(value="/api/v1/users/{user_id}/idcards/{card_id}",method=RequestMethod.DELETE)
@ResponseStatus(org.springframework.http.HttpStatus.OK)

public IDCard deleteCard(@PathVariable(value="user_id") String user_id,@PathVariable(value="card_id") String card_id) throws UnknownHostException
 {
	
	IDCard idcard = new IDCard();
	  MongoClientURI uri  = new MongoClientURI("mongodb://goutam:1215@ds045970.mongolab.com:45970/adwant"); 
	  MongoClient client = new MongoClient(uri);
	  DB db = client.getDB("adwant");
	DBCollection coll = db.getCollection("customer");
	BasicDBObject query = new BasicDBObject("_id", user_id);
	ArrayList query2 = new ArrayList();
	DBCursor cursor = coll.find(query);
	try {
		   while(cursor.hasNext() ) {
			   BasicDBObject idoc = new BasicDBObject();
			   DBObject obj = cursor.next();
			   query2 = (ArrayList) obj.get("idcards");
			   System.out.println(query2);
			   for(int i=0;i<=query2.size()-1;i++)
			   {
				   //System.out.println(((BasicBSONObject) query2.get(i)).get("_id"));
				 if(((BasicBSONObject) query2.get(i)).get("_id").equals(card_id))
				   {
					   query2.remove(i);
				   }   
				 coll.save(obj);
			   }
		   }
		} finally {
		   cursor.close();
		}
	return idcard;
 }


//7th API


@RequestMapping(value="/api/v1/users/{user_id}/weblogins",method=RequestMethod.POST)
@ResponseStatus(org.springframework.http.HttpStatus.CREATED)


public WebLogin getWebLogin(@PathVariable(value="user_id") String user_id,@Valid @RequestBody WebLogin weblogin) throws UnknownHostException
 {
	
	WebLogin newUser = new WebLogin( weblogin.getUrl(),weblogin.getLogin(),weblogin.getPassword());

	newUser.setLogin_id("i-"+String.valueOf(WebLogin.getI()));
	  MongoClientURI uri  = new MongoClientURI("mongodb://goutam:1215@ds045970.mongolab.com:45970/adwant"); 
	  MongoClient client = new MongoClient(uri);
	  DB db = client.getDB("adwant");
		
	DBCollection coll = db.getCollection("customer");
	BasicDBObject query = new BasicDBObject("_id", user_id);
	DBCursor cursor = coll.find(query);
	//System.out.println(cursor.next());
	while(cursor.hasNext())
	{
		BasicDBObject idoc = new BasicDBObject();
		idoc.put("_id",newUser.getLogin_id());
		idoc.put("url",newUser.getUrl());
		idoc.put("login",newUser.getLogin());
		idoc.put("password",newUser.getPassword());
		DBObject obj=cursor.next();
		if(obj.get("weblogin")==null)
		{
			ArrayList list = new ArrayList();
			list.add(idoc);
			obj.put("weblogin",list);
		}
		else
		{
			ArrayList list=(ArrayList) obj.get("weblogin");
			list.add(idoc);
			obj.put("weblogin",list);
		}
		coll.save(obj);
	}
	
	return newUser;
 }

//8th API

@RequestMapping(value="/api/v1/users/{user_id}/weblogins",method=RequestMethod.GET)
@ResponseStatus(org.springframework.http.HttpStatus.OK)
public ArrayList retrieveWebLogin(@PathVariable(value="user_id") String user_id) throws UnknownHostException
 {
	User user = new User();
	  MongoClientURI uri  = new MongoClientURI("mongodb://goutam:1215@ds045970.mongolab.com:45970/adwant"); 
	  MongoClient client = new MongoClient(uri);
	  DB db = client.getDB("adwant");
	ArrayList list= new ArrayList();
	DBCollection coll = db.getCollection("customer");
	BasicDBObject query = new BasicDBObject("_id", user_id);
	DBCursor cursor = coll.find(query);
	try {
	   while(cursor.hasNext() ) {
		   DBObject obj = cursor.next();
		   list= (ArrayList) obj.get("weblogin");
	   }
	} finally {
	   cursor.close();
	}
	return list;
}

//9th API

@RequestMapping(value="/api/v1/users/{user_id}/weblogins/{login_id}",method=RequestMethod.DELETE)
@ResponseStatus(org.springframework.http.HttpStatus.OK)
public WebLogin deleteWebLogin(@PathVariable(value="user_id") String user_id,@PathVariable(value="login_id") String login_id) throws UnknownHostException
 {
	WebLogin weblogin = new WebLogin();
	  MongoClientURI uri  = new MongoClientURI("mongodb://goutam:1215@ds045970.mongolab.com:45970/adwant"); 
	  MongoClient client = new MongoClient(uri);
	  DB db = client.getDB("adwant");
	DBCollection coll = db.getCollection("customer");
	BasicDBObject query = new BasicDBObject("_id", user_id);
	ArrayList query2 = new ArrayList();
	DBCursor cursor = coll.find(query);
	try {
		   while(cursor.hasNext() ) {
			   BasicDBObject idoc = new BasicDBObject();
			   DBObject obj = cursor.next();
			   query2 = (ArrayList) obj.get("weblogin");
			   System.out.println(query2);
			   for(int i=0;i<=query2.size()-1;i++)
			   {
				   //System.out.println(((BasicBSONObject) query2.get(i)).get("_id"));
				 if(((BasicBSONObject) query2.get(i)).get("_id").equals(login_id))
				   {
					   query2.remove(i);
				   }   
				 coll.save(obj);
			   }
		   }
		} finally {
		   cursor.close();
		}
	return weblogin;
 }

//10th API

@RequestMapping(value="/api/v1/users/{user_id}/bankaccounts",method=RequestMethod.POST)
@ResponseStatus(org.springframework.http.HttpStatus.CREATED)

public BankAccount getBankAccount(@PathVariable(value="user_id") String user_id,@Valid  @RequestBody BankAccount bankaccount) throws UnknownHostException, UnirestException
 {
	HttpResponse<JsonNode> response=Unirest.get("http://www.routingnumbers.info/api/data.json")
			.field("rn",bankaccount.getRouting_number()).asJson();
		JsonNode body =response.getBody();
		if(String.valueOf(body.getObject().get("code")).equals("200"))
		{
			bankaccount.setAccount_name(String.valueOf(body.getObject().get("customer_name")));
		}
		else
		throw new UnirestException("");
	BankAccount newUser = new BankAccount( bankaccount.getAccount_name(),bankaccount.getRouting_number(),bankaccount.getAccount_number());
	  newUser.setBa_id("b-"+String.valueOf(BankAccount.getI()));
	  
	  MongoClientURI uri  = new MongoClientURI("mongodb://goutam:1215@ds045970.mongolab.com:45970/adwant"); 
	  MongoClient client = new MongoClient(uri);
	  DB db = client.getDB("adwant");
		
	DBCollection coll = db.getCollection("customer");
	BasicDBObject query = new BasicDBObject("_id", user_id);
	DBCursor cursor = coll.find(query);
	//System.out.println(cursor.next());
	while(cursor.hasNext())
	{
		BasicDBObject idoc = new BasicDBObject();
		idoc.put("_id",newUser.getBa_id());
		idoc.put("account_name",bankaccount.getAccount_name());
		idoc.put("routing_number",newUser.getRouting_number());
		idoc.put("account_number ",newUser.getAccount_number());
		DBObject obj=cursor.next();
		if(obj.get("bankaccount")==null)
		{
			ArrayList list = new ArrayList();
			list.add(idoc);
			obj.put("bankaccount",list);
		}
		else
		{
			ArrayList list=(ArrayList) obj.get("bankaccount");
			list.add(idoc);
			obj.put("bankaccount",list);
		}
		coll.save(obj);
	}
	
	return newUser;
 }

//11th API

@RequestMapping(value="/api/v1/users/{user_id}/bankaccounts",method=RequestMethod.GET)
@ResponseStatus(org.springframework.http.HttpStatus.OK)
public ArrayList retrieveBankAccount(@PathVariable(value="user_id") String user_id) throws UnknownHostException, UnirestException
 {
	
	BankAccount bankaccount = new BankAccount();
	
	  MongoClientURI uri  = new MongoClientURI("mongodb://goutam:1215@ds045970.mongolab.com:45970/adwant"); 
	  MongoClient client = new MongoClient(uri);
	  DB db = client.getDB("adwant");
	ArrayList list= new ArrayList();
	DBCollection coll = db.getCollection("customer");
	BasicDBObject query = new BasicDBObject("_id", user_id);
	DBCursor cursor = coll.find(query);
	try {
	   while(cursor.hasNext() ) {
		   DBObject obj = cursor.next();
		   list= (ArrayList) obj.get("bankaccount");
	   }
	} finally {
	   cursor.close();
	}
	return list;
}
//12th API

@RequestMapping(value="/api/v1/users/{user_id}/bankaccounts/{ba_id}",method=RequestMethod.DELETE)
@ResponseStatus(org.springframework.http.HttpStatus.OK)
public BankAccount deleteBankAccount(@PathVariable(value="user_id") String user_id,@PathVariable(value="ba_id") String ba_id) throws UnknownHostException
 {
	BankAccount bankaccount = new BankAccount();
	  MongoClientURI uri  = new MongoClientURI("mongodb://goutam:1215@ds045970.mongolab.com:45970/adwant"); 
	  MongoClient client = new MongoClient(uri);
	  DB db = client.getDB("adwant");
	DBCollection coll = db.getCollection("customer");
	BasicDBObject query = new BasicDBObject("_id", user_id);
	ArrayList query2 = new ArrayList();
	DBCursor cursor = coll.find(query);
	try {
		   while(cursor.hasNext() ) {
			   BasicDBObject idoc = new BasicDBObject();
			   DBObject obj = cursor.next();
			   query2 = (ArrayList) obj.get("bankaccount");
			   System.out.println(query2);
			   for(int i=0;i<=query2.size()-1;i++)
			   {
				   //System.out.println(((BasicBSONObject) query2.get(i)).get("_id"));
				 if(((BasicBSONObject) query2.get(i)).get("_id").equals(ba_id))
				   {
					   query2.remove(i);
				   }   
				 coll.save(obj);
			   }
		   }
		} finally {
		   cursor.close();
		}
	return bankaccount;
 }

@ExceptionHandler
@ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
@ResponseBody
ErrorMessage handleException(MethodArgumentNotValidException ex) {
    List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
    List<String> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());
    String error;
    for (FieldError fieldError : fieldErrors) {
        error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
        errors.add(error);
    }
    for (ObjectError objectError : globalErrors) {
        error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
        errors.add(error);
    }
    return new ErrorMessage(errors);
}
@ExceptionHandler
@ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
@ResponseBody
void handle_Exception(UnirestException ex) {}
public Filter  etagFilter() {
	ShallowEtagHeaderFilter shallowEtagHeaderFilter = new ShallowEtagHeaderFilter();
	return shallowEtagHeaderFilter;
}
}