package cn.zxw.application.bean;

import java.util.ArrayList;
import java.util.List;

public class Mail {
	public static String ENCODEING="UTF-8";
	String host="";
	String receiver="";
	String sender="";
	String username="";
	String password="";
	String Subject="";
	String Message="";
	List<String> toCc=new ArrayList<>();
	String name="";
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getToCc() {
		return toCc;
	}
	public void setToCc(List<String> toCc) {
		this.toCc = toCc;
	}
	
}
