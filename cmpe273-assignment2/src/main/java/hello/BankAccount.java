package hello;

import org.hibernate.validator.constraints.NotBlank;

public class BankAccount {
	private  String ba_id;
	@NotBlank(message = "Account name should not be blank :)")
	private String account_name;
	@NotBlank(message = "Routing Number should not be blank :)")
	private String routing_number;
	@NotBlank(message = "Account Number should not be blank :)")
	private  String account_number;
	private static int i=0;
	
	public BankAccount()
	{
		
	}
	
	public BankAccount(String account_name, String routing_number,
			String account_number) {
		super();
		i++;
		this.account_name = account_name;
		this.routing_number = routing_number;
		this.account_number = account_number;
	}
	public static int getI() {
		return i;
	}
	public static void setI(int i) {
		BankAccount.i = i;
	}
	public String getBa_id() {
		return ba_id;
	}
	public void setBa_id(String ba_id) {
		this.ba_id = ba_id;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public String getRouting_number() {
		return routing_number;
	}
	public void setRouting_number(String routing_number) {
		this.routing_number = routing_number;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	
	
	
}