package member.model.vo;

import javax.mail.PasswordAuthentication;

import javax.mail.Authenticator;


public class SMTPAuthenticator extends Authenticator {

    public SMTPAuthenticator() {

        super();

    }

 

    public PasswordAuthentication getPasswordAuthentication() {

        String username = "khclassking@gmail.com";

        String password = "khacademy1@";

        return new PasswordAuthentication(username, password);

    }

}