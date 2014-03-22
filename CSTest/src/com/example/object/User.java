package com.example.object;


public class User {
	private String name = null;
	private String password = null;
	private String school = null;
	private String address = null;
	private String info = null;

	public User() {
	}
	
	public User(String name,String password){
		this.name=name;
		this.password=password;
	}

	public User(String name, String password, String school, String address,
			String info) {
		this.address = address;
		this.name = name;
		this.school = school;
		this.info = info;
		this.password = password;
	}
	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

}
