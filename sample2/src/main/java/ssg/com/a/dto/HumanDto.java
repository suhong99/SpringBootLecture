package ssg.com.a.dto;

public class HumanDto {
	private int number;
	private String name;
	private String address;
	
	public HumanDto() {
		
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "HumanDto [number=" + number + ", name=" + name + ", address=" + address + "]";
	}

	public HumanDto(int number, String name, String address) {
		super();
		this.number = number;
		this.name = name;
		this.address = address;
	}
	
	
}

