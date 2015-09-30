package com.model;


// it can all the information for single mail.
// basically converting database row into java object.
// Every thread has queue of objects of this objects
// so thread will pop objects from queue and send mail one by one.
public class DatabaseRow {

	int id;
	String from_email_address;
	String to_email_address;
	String subject;
	String body;
	String password;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public DatabaseRow(int _id, String from_email_address2,
			String to_email_address2, String subject2, String body2, String password) {
		this.id =_id;
		this.from_email_address=from_email_address2;
		this.to_email_address=to_email_address2;
		this.subject=subject2;
		this.body=body2;
		this.password=password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFrom_email_address() {
		return from_email_address;
	}
	public void setFrom_email_address(String from_email_address) {
		this.from_email_address = from_email_address;
	}
	public String getTo_email_address() {
		return to_email_address;
	}
	public void setTo_email_address(String to_email_address) {
		this.to_email_address = to_email_address;
	}
	public String getSubject() {
		return this.subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return this.body;
	}
	public void setBody(String body) {
		this.body = body;
	}

}
