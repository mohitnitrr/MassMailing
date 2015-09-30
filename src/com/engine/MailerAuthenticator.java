package com.engine;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import com.constants.Constants;
import com.model.DatabaseRow;

// this class needed as  a part of authentication needed for mail sending.
public class MailerAuthenticator {
   
     Session authenticate(DatabaseRow databaseRow)
     {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", Constants.host_smtp);
            properties.put("mail.smtp.port", Constants.port);
            Session session = Session.getInstance(properties,  
            new javax.mail.Authenticator() {  
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {  
                return new PasswordAuthentication(databaseRow.getFrom_email_address(),databaseRow.getPassword());  
            }  
        }); 
            return session;
     }
    
    
}
