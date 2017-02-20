package com.daysluck.service;

import javax.mail.MessagingException;

import com.daysluck.domain.EmailDTO;

public interface EmailSendService {

	public int sendSimpleEmail(EmailDTO email);
	
	public int sendAttachmentsEmail(EmailDTO email) throws MessagingException;
	
	public int sendInlineMail(EmailDTO email) throws MessagingException;
	
	public int sendTemplateMail(EmailDTO email) throws Exception;
}
