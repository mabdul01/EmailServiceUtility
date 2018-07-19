package com.email.service.impl;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailServiceImpl implements com.email.service.EmailService {

	private final String thisClassName = EmailServiceImpl.class.getName();
	private Logger logger = Logger.getLogger(thisClassName);

	/**
	 * Sends an email message with attachments.
	 *
	 * @param host
	 *            host address from which the mail will be sent.
	 * @param from
	 *            email address from which the message will be sent.
	 * @param toRecipients
	 *            list of strings containing the to recipients of the message.
	 * @param ccRecipients
	 *            list of strings containing the CC recipients of the message.
	 * @param subject
	 *            subject header field.
	 * @param text
	 *            content of the message.
	 * @return boolean true or false
	 */
	@Override
	public boolean send(String host, String from, List<String> toRecipients, List<String> ccRecipients, String subject,
			String text) {
		return send(host, from, toRecipients, ccRecipients, subject, text, null);
	}

	/**
	 * Sends an email message with attachments.
	 *
	 * @param host
	 *            host address from which the mail will be sent.
	 * @param from
	 *            email address from which the message will be sent.
	 * @param toRecipients
	 *            list of strings containing the to recipients of the message.
	 * @param ccRecipients
	 *            list of strings containing the CC recipients of the message.
	 * @param subject
	 *            subject header field.
	 * @param text
	 *            content of the message.
	 * @param attachments
	 *            attachments to be included with the message.
	 * @return boolean true or false
	 */
	@Override
	public boolean send(String host, String from, List<String> toRecipients, List<String> ccRecipients, String subject,
			String text, List<String> attachments) {
		String thisMethodName = "send";

		// load email configuration from properties file
		Properties properties = new Properties();
		properties.setProperty("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);
		MimeMessage mimeMessage = new MimeMessage(session);

		try {
			mimeMessage.setFrom(new InternetAddress(from));
			setRecipients(toRecipients, Message.RecipientType.TO, mimeMessage);
			setRecipients(ccRecipients, Message.RecipientType.CC, mimeMessage);
			mimeMessage.setSubject(subject);
			mimeMessage.setText(text);
			addAttachments(attachments, mimeMessage);
			Transport.send(mimeMessage);

			logger.logp(Level.INFO, thisClassName, thisMethodName, " Mail sent successfully");
			return true;
		} catch (MessagingException e) {
			logger.logp(Level.SEVERE, thisClassName, thisMethodName, "  Exception occured while mailing. ", e);
		}

		logger.logp(Level.INFO, thisClassName, thisMethodName, " Exit  " + thisMethodName);
		return false;
	}

	/**
	 * 
	 * @param attachments
	 *            attachments to be included with the message.
	 * @param mimeMessage
	 *            mimeMessage object to add the attachments.
	 * @throws MessagingException
	 */
	private void addAttachments(List<String> attachments, MimeMessage mimeMessage) throws MessagingException {
		if (null != attachments && !attachments.isEmpty()) {
			Multipart multipart = new MimeMultipart("mixed");
			for (String filePath : attachments) {
				MimeBodyPart messageBodyPart = new MimeBodyPart();
				DataSource dataSource = new FileDataSource(filePath);
				messageBodyPart.setDataHandler(new DataHandler(dataSource));
				messageBodyPart.setFileName(dataSource.getName());
				multipart.addBodyPart(messageBodyPart);
			}
			mimeMessage.setContent(multipart);
		}
	}

	/**
	 * 
	 * @param recipients
	 *            list of strings containing the recipients of the message.
	 * @param type
	 *            RecipientType
	 * @param mimeMessage
	 *            mimeMessage object to add the attachments.
	 * @throws MessagingException
	 */
	private void setRecipients(List<String> recipients, RecipientType type, MimeMessage mimeMessage)
			throws MessagingException {
		if (null != recipients && !recipients.isEmpty()) {
			Address[] addresses = new Address[recipients.size()];
			for (int i = 0, length = recipients.size(); i < length; i++) {
				addresses[i] = new InternetAddress(recipients.get(i));
			}
			mimeMessage.addRecipients(type, addresses);
		}

	}

}
