package com.utils;

import org.apache.commons.mail.HtmlEmail;

import com.claire.util.VerifyCodeUtil;
import com.entity.Email;
import com.entity.UserEntity;

public class EmailUtil {
	 public static boolean sendEmail(UserEntity user){
	        try {
	        	Email from = SpringUtil.getBean(Email.class);
	        	 String emailServiceCode = VerifyCodeUtil.generateVerifyCode();//Generates a random CAPTCHA
	        	 user.setCode(emailServiceCode);
	            HtmlEmail email = new HtmlEmail();//Don't have to change
	            email.setHostName("smtp.163.com");//It needs to be modified. The 126 mailbox is smtp.126.com, the 163 mailbox is 163.smtp.com, and the QQ mailbox is smtp.qq.com
	            email.setCharset("UTF-8");
	            email.addTo(user.getEmail());// Receives an address
	 
	            email.setFrom(from.getFrom(), "workshop");//Enter email address and user name here. User name can be filled in arbitrarily
	 
	            email.setAuthentication(from.getFrom(), from.getPassword());//Enter email address and client authorization code here
	 
	            email.setSubject("Registration verification code");//Please fill in the email name here. The email name is optional
	            email.setMsg("Dear users, your verification code is:\n" + emailServiceCode);//Fill in the email here
//	            email.setSSLOnConnect(false);
	            //Enable SSL encryption
	            email.setSSLOnConnect(true);
	//Use port 465 (optional, SSL default is 465)
	            email.setSslSmtpPort("465");
	            email.send();
	            return true;
	        }
	        catch(Exception e){
	            e.printStackTrace();
	            return false;
	        }
	    }
	 public static boolean sendRemindEmail(String to,String reminder){
	        try {
	        	Email from = SpringUtil.getBean(Email.class);
	            HtmlEmail email = new HtmlEmail();//Don't have to change
	            email.setHostName("smtp.163.com");//It needs to be modified. The 126 mailbox is smtp.126.com, the 163 mailbox is 163.smtp.com, and the QQ mailbox is smtp.qq.com
	            email.setCharset("UTF-8");
	            email.addTo(to);// Receives an address
	 
	            email.setFrom(from.getFrom(), "workshop");//Enter email address and user name here. User name can be filled in arbitrarily
	 
	            email.setAuthentication(from.getFrom(), from.getPassword());//Enter email address and client authorization code here
	 
	            email.setSubject("Task manager");//Please fill in the email name here. The email name is optional
	            email.setMsg("Dear users, you have an agent task:\n" + reminder);//Fill in the email here
//	            email.setSSLOnConnect(false);
	            //Enable SSL encryption
	            email.setSSLOnConnect(true);
	//Use port 465 (optional, SSL default is 465)
	            email.setSslSmtpPort("465");
	            email.send();
	            return true;
	        }
	        catch(Exception e){
	            e.printStackTrace();
	            return false;
	        }
	    }
	 
	 public static void main(String[] args) {
		UserEntity user = new UserEntity();
		user.setEmail("346670424@qq.com");
		try {
        	Email from = new Email();
        	from.setFrom("z4x8u7@163.com");
        	from.setPassword("TEBXZSJDXJXCNPVM");
        	 String emailServiceCode = VerifyCodeUtil.generateVerifyCode();//Generates a random CAPTCHA
            HtmlEmail email = new HtmlEmail();//Don't have to change
            email.setHostName("smtp.163.com");//It needs to be modified. The 126 mailbox is smtp.126.com, the 163 mailbox is 163.smtp.com, and the QQ mailbox is smtp.qq.com
            email.setCharset("UTF-8");
            email.addTo(user.getEmail());// Receives an address
 
            email.setFrom(from.getFrom(), "workshop");//Enter email address and user name here. User name can be filled in arbitrarily
 
            email.setAuthentication(from.getFrom(), from.getPassword());//Enter email address and client authorization code here
 
            email.setSubject("Registration verification code");//Please fill in the email name here. The email name is optional
            email.setMsg("Dear users, your verification code is:\n" + emailServiceCode);//Fill in the email here
//            email.setSSLOnConnect(false);
            //Enable SSL encryption
            email.setSSLOnConnect(true);
//Use port 465 (optional, SSL default is 465)
            email.setSslSmtpPort("465");
            email.send();
        }
        catch(Exception e){
            e.printStackTrace();
        }
		
	}

}
