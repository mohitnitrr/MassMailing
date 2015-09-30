package com.engine;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.model.DatabaseRow;

// this class is responsible to send mail one by one and contains api call of javax.mail jar.
// this class takes help of MakeMessage class to get next mail information.
// MakeMessage class is basically a producer of DataBaserow.
public class SendMessage {

	DatabaseRow databaseRow;
	MimeMessage message;
	Session session;

	void sendMessage() {

		MakeMessage makeMessageObject = new MakeMessage();
		while (!makeMessageObject.finishedJobsFlag) {
			try {

				while (true) {
					databaseRow = makeMessageObject.getNextMessage();
					if (databaseRow != null) {
						session = new MailerAuthenticator()
								.authenticate(databaseRow);
						message = new MimeMessage(session);
						message.setFrom(new InternetAddress(databaseRow
								.getFrom_email_address()));
						message.setSubject(databaseRow.getSubject());
						message.setText(databaseRow.getBody());
						message.setRecipient(
								Message.RecipientType.TO,
								new InternetAddress(databaseRow
										.getTo_email_address()));

						Transport.send(message);
						System.out.println("Sent Successfully");
					}

					else if (databaseRow == null || makeMessageObject.finishedJobsFlag)
						break;
				}

				

			} catch (MessagingException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}
