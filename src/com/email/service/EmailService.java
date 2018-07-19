package com.email.service;

import java.util.List;

public interface EmailService {
	
	/**
	 * Simply sends smtp email without any attachment
	 * @param host
	 * @param from
	 * @param toRecipients
	 * @param ccRecipients
	 * @param subject
	 * @param text
	 * @return
	 */
	public boolean send(String host, String from, List<String> toRecipients, List<String> ccRecipients, String subject,
			String text);
	
	/**
	 * Simply sends smtp email with attachment
	 * @param host
	 * @param from
	 * @param toRecipients
	 * @param ccRecipients
	 * @param subject
	 * @param text
	 * @param list of full path of file names as attachments
	 * @return
	 */
	public boolean send(String host, String from, List<String> toRecipients, List<String> ccRecipients, String subject,
			String text, List<String> attachments);

}
