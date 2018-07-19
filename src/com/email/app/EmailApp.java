package com.email.app;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.email.service.impl.EmailServiceImpl;

public class EmailApp {
	
	private static final String CLASS_NAME = EmailApp.class.getName();
	private static final Logger logger = Logger.getLogger(EmailApp.class.getSimpleName());
	private EmailApp(){}
	public static void main(String[] args) {

		com.email.service.EmailService emailService = new EmailServiceImpl();
		logger.logp(Level.INFO, CLASS_NAME, "main", "Email sending starts");
		
		boolean status = emailService.send("smtp.bcbsma.com", "EDI5010@BCBSMA.com", Arrays.asList("mohammed.abdulhakeem@bcbsma.com"), null, "Test mail", "This is a test mail. Please ignore");

		if(!status){
			logger.logp(Level.INFO, CLASS_NAME, "main", "Email sending failed");
		} else {
			logger.logp(Level.INFO, CLASS_NAME, "main", "Email sending completed");
		}
	}

}
