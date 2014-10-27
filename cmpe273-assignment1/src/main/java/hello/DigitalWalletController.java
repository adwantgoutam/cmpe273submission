package hello;


import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.validation.Valid;
import javax.xml.ws.Response;

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

@RestController
public class DigitalWalletController {

ArrayList<User> user1 = new ArrayList<User>(); 

	public ArrayList<User> getUser1() {
		return user1;
	}
	public void setUser1(ArrayList<User> user1) {
		this.user1 = user1;
	}
	
ArrayList<IDCard> user2 = new ArrayList<IDCard>();
	
	
public ArrayList<IDCard> getUser2() {
		return user2;
	}
	public void setUser2(ArrayList<IDCard> user2) {
		this.user2 = user2;
	}

Map<String, List<IDCard>> map1 = new HashMap<String, List<IDCard>>();

Map<String, List<WebLogin>> map2 = new HashMap<String, List<WebLogin>>();

public Map<String, List<WebLogin>> getMap2() {
	return map2;
}
public void setMap2(Map<String, List<WebLogin>> map2) {
	this.map2 = map2;
}

ArrayList<WebLogin> user3 = new ArrayList<WebLogin>();

ArrayList<BankAccount> user4 = new ArrayList<BankAccount>();
	
Map<String, List<BankAccount>> map3 = new HashMap<String, List<BankAccount>>();


	public ArrayList<BankAccount> getUser4() {
	return user4;
}
public void setUser4(ArrayList<BankAccount> user4) {
	this.user4 = user4;
}
public Map<String, List<BankAccount>> getMap3() {
	return map3;
}
public void setMap3(Map<String, List<BankAccount>> map3) {
	this.map3 = map3;
}
	public ArrayList<WebLogin> getUser3() {
	return user3;
}
public void setUser3(ArrayList<WebLogin> user3) {
	this.user3 = user3;
}
	public Map<String, List<IDCard>> getMap1() {
	return map1;
}
public void setMap1(Map<String, List<IDCard>> map1) {
	this.map1 = map1;
}
	// 1st API
	@RequestMapping(value="/api/v1/users",method=RequestMethod.POST)
	@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
	public User getUser(@Valid @RequestBody User user)
	 {
		
		User newUser = new User( user.getEmail(),user.getPassword(),new Date().toGMTString());
		user1.add(newUser);	
		newUser.setUser_id("u-"+String.valueOf(User.getI()));
		user1.add(newUser);
		return newUser;
	 }
	
	
//2nd API	
	@RequestMapping(value="/api/v1/users/{user_id}",method=RequestMethod.GET)	
	@ResponseStatus(org.springframework.http.HttpStatus.OK)
	public User viewUser(@PathVariable(value="user_id") String user_id){	
		ListIterator<User> itr = user1.listIterator();
		 User usr = null;
		while (itr.hasNext()) {
		    
		    usr=(User)itr.next();
		    if(usr.getUser_id().equals(user_id)){
		    	break;
		    }
		    else{
		    	usr = null;
		    }
		}
		
		return usr;	
	}
	
	
	
//3rd API	 
	 
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/api/v1/users/{user_id}",method=RequestMethod.PUT) 
	@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
	public User updateUser(@Valid @RequestBody User user,@PathVariable(value="user_id") String user_id)
	{
		
		ListIterator<User> itr = user1.listIterator();
		 User usr = null;
		 while (itr.hasNext()) {
			  
			    usr=(User)itr.next();
			    if(usr.getEmail().equals(user.getEmail()))
			    {
			    	
			    	usr.setPassword(user.getPassword());
			    	usr.setCreated_at(new Date().toGMTString());
			    }
			    else{
			    	usr = null;
			    }
			}
		return usr;
		
	}
	
//4TH API	


@RequestMapping(value="/api/v1/users/{user_id}/idcards",method=RequestMethod.POST)
@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
	
	public List<IDCard> getCard(@PathVariable(value="user_id") String user_id,@Valid @RequestBody IDCard idcard)
	 {
		
		IDCard newUser = new IDCard( idcard.getCard_name(),idcard.getCard_number(),new Date().toLocaleString());
		//user2.add(newUser);	
		newUser.setCard_id("c-"+String.valueOf(IDCard.getI()));
		user2.add(newUser);
		map1.put(user_id, user2); 
		
		return user2;
	 }

//5THAPI



@RequestMapping(value="/api/v1/users/{user_id}/idcards",method=RequestMethod.GET)
@ResponseStatus(org.springframework.http.HttpStatus.OK)

public List<IDCard> retrieveCard(@PathVariable(value="user_id") String user_id)
 {
	List<IDCard> user5 = new ArrayList<IDCard>();
	ListIterator<User> itr = user1.listIterator();
	 User usr = null;
	 while (itr.hasNext()) {
		    
		    usr=(User)itr.next();
		    if(usr.getUser_id().equals(user_id))
		    {
		    	
		    	user5=map1.get(user_id);
		    	break;
		    }
		    else{
		    	user5 = null;
		    }
		}
	return user5;
}

//6THAPI

@RequestMapping(value="/api/v1/users/{user_id}/idcards/{card_id}",method=RequestMethod.DELETE)
@ResponseStatus(org.springframework.http.HttpStatus.OK)

public IDCard deleteCard(@PathVariable(value="card_id") String card_id)
 {
	ListIterator<IDCard> itr = user2.listIterator();
	 IDCard ctr = null;
	while (itr.hasNext()) {
	    
	    ctr=(IDCard)itr.next();
	    if(ctr.getCard_id().equals(card_id)){
	    	
	    	user2.remove(ctr);
	    	break;
	    }
	    else{
	    	ctr = null;
	    }
	
 }
	return ctr;
 }


//7th API


@RequestMapping(value="/api/v1/users/{user_id}/weblogins",method=RequestMethod.POST)
@ResponseStatus(org.springframework.http.HttpStatus.CREATED)


public List<WebLogin> getWebLogin(@PathVariable(value="user_id") String user_id,@Valid @RequestBody WebLogin weblogin)
 {
	
	WebLogin newUser = new WebLogin( weblogin.getUrl(),weblogin.getLogin(),weblogin.getPassword());

	newUser.setLogin_id("i-"+String.valueOf(WebLogin.getI()));
	user3.add(newUser);
	map2.put(user_id, user3); 
	
	return user3;
 }

//8th API

@RequestMapping(value="/api/v1/users/{user_id}/weblogins",method=RequestMethod.GET)
@ResponseStatus(org.springframework.http.HttpStatus.OK)
public List<WebLogin> retrieveWebLogin(@PathVariable(value="user_id") String user_id)
 {
	List<WebLogin> user5 = new ArrayList<WebLogin>();
	ListIterator<User> itr = user1.listIterator();
	 User usr = null;
	 while (itr.hasNext()) {
		    
		    usr=(User)itr.next();
		    if(usr.getUser_id().equals(user_id))
		    {
		    	
		    	user5=map2.get(user_id);
		    	break;
		    }
		    else{
		    	user5 = null;
		    }
		}
	return user5;
}

//9th API

@RequestMapping(value="/api/v1/users/{user_id}/weblogins/{login_id}",method=RequestMethod.DELETE)
@ResponseStatus(org.springframework.http.HttpStatus.OK)
public WebLogin deleteWebLogin(@PathVariable(value="login_id") String login_id)
 {
	ListIterator<WebLogin> itr = user3.listIterator();
	WebLogin ctr = null;
	while (itr.hasNext()) {
	    
	    ctr=(WebLogin)itr.next();
	    if(ctr.getLogin_id().equals(login_id)){
	    	
	    	user3.remove(ctr);
	    	break;
	    }
	    else{
	    	ctr = null;
	    }
	
 }
	return ctr;
 }

//10th API

@RequestMapping(value="/api/v1/users/{user_id}/bankaccounts",method=RequestMethod.POST)
@ResponseStatus(org.springframework.http.HttpStatus.CREATED)

public List<BankAccount> getBankAccount(@PathVariable(value="user_id") String user_id,@Valid  @RequestBody BankAccount bankaccount)
 {
	
BankAccount newUser = new BankAccount( bankaccount.getAccount_name(),bankaccount.getRouting_number(),bankaccount.getAccount_number());

	newUser.setBa_id("b-"+String.valueOf(BankAccount.getI()));
	user4.add(newUser);
	map3.put(user_id,user4); 
	
	return user4;
 }

//11th API

@RequestMapping(value="/api/v1/users/{user_id}/bankaccounts",method=RequestMethod.GET)
@ResponseStatus(org.springframework.http.HttpStatus.OK)
public List<BankAccount> retrieveBankAccount(@PathVariable(value="user_id") String user_id)
 {
	List<BankAccount> user5 = new ArrayList<BankAccount>();
	ListIterator<User> itr = user1.listIterator();
	 User usr = null;
	 while (itr.hasNext()) {
		    
		    usr=(User)itr.next();
		    if(usr.getUser_id().equals(user_id))
		    {
		    	
		    	user5=map3.get(user_id);
		    	break;
		    }
		    else{
		    	user5 = null;
		    }
		}
	return user5;
}
//12th API

@RequestMapping(value="/api/v1/users/{user_id}/bankaccounts/{ba_id}",method=RequestMethod.DELETE)
@ResponseStatus(org.springframework.http.HttpStatus.OK)
public BankAccount deleteBankAccount(@PathVariable(value="ba_id") String ba_id)
 {
	ListIterator<BankAccount> itr = user4.listIterator();
	BankAccount ctr = null;
	while (itr.hasNext()) {
	    
	    ctr=(BankAccount)itr.next();
	    if(ctr.getBa_id().equals(ba_id)){
	    	
	    	user4.remove(ctr);
	    	break;
	    }
	    else{
	    	ctr = null;
	    }
	
 }
	return ctr;
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
public Filter  etagFilter() {
	ShallowEtagHeaderFilter shallowEtagHeaderFilter = new ShallowEtagHeaderFilter();
	return shallowEtagHeaderFilter;
}
}


