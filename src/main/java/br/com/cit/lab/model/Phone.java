package br.com.cit.lab.model;

import java.io.Serializable;

public class Phone implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1161846803496573440L;
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Phone(String phone) {
		super();
		this.phone = phone;
	}
	
	public Phone() {
		// TODO Auto-generated constructor stub
	}
}
