package br.com.cit.lab.model;

import java.io.Serializable;
import java.util.Date;

public class LogDataApp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4594303360059562246L;
	
	private String user;
	private Date date;
	private String type;
	
	public LogDataApp() {
	}
	
	public LogDataApp(String user, Date date, String type) {
		super();
		this.user = user;
		this.date = date;
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return getUser()+" :: "+getType()+" :: "+getDate();
	}


}
