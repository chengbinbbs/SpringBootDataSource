package com.daysluck.domain;

/**
 * 邮件对象
 * @author zhangcb
 *
 */
public class EmailDTO {

	private String from;		//发送者
	
	private String to;			//接受者
	
	private String subject;		//邮件主题.
	
	private String text;	    //邮件内容.

	
	public EmailDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmailDTO(String from, String to, String subject, String text) {
		super();
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.text = text;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
