package hello;



import org.hibernate.validator.constraints.NotBlank;

public class IDCard {
	private String card_id;
	@NotBlank(message = "Card Name should not be blank :)")
	private String card_name;
	@NotBlank(message = "Card Number should not be blank :)")
	private  String card_number;
	private  String expiration_date;
	public static Integer i=0;
	
	public IDCard()
	{
		
		
	}
	
	
	
	public IDCard(String card_name, String card_number, String string1) {
		super();
		i++;
		this.card_name = card_name;
		this.card_number = card_number;
		this.expiration_date = string1;
	}
	public String getCard_id() {
		return card_id;
	}
	public String setCard_id(String card_id) {
		return this.card_id = card_id;
	}
	public String getCard_name() {
		return card_name;
	}
	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public String getExpiration_date() {
		return expiration_date;
	}
	public void setExpiration_date(String expiration_date) {
		this.expiration_date = expiration_date;
	}
	public static Integer getI() {
		return i;
	}
	public static void setI(Integer i) {
		IDCard.i = i;
	}



	
}
