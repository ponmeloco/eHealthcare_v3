
package test;


import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.text.*;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;





public class Mail {

	protected Session mailSession;
	private String username = "amaramkooooo@gmail.com";
	private   String password = "Mnb12345!";
	private String smtpHost="smtp.gmail.com";
	private String smtPort="587";
	DateFormat dateFormatter = new SimpleDateFormat("E yyyy/MM/dd/  HH:mm:ss");





	private void login() {
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);

		props.put("mail.smtp.starttls.enable", "true");

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", smtPort);

		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};

		this.mailSession = Session.getDefaultInstance(props, auth);
		System.out.println("Eingeloggt.");
	}

	private void send(String senderMail, String senderName, String receiverAddresses, String subject, String message)
			throws MessagingException, IllegalStateException, UnsupportedEncodingException {
		if (mailSession == null) {
			throw new IllegalStateException("Du musst dich zuerst einloggen (login()-Methode)");
		}

		MimeMessage msg = new MimeMessage(mailSession);
		msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		msg.addHeader("format", "flowed");
		msg.addHeader("Content-Transfer-Encoding", "8bit");

		msg.setFrom(new InternetAddress(senderMail, senderName));
		msg.setReplyTo(InternetAddress.parse(senderMail, false));
		msg.setSubject(subject, "UTF-8");
		msg.setText(message, "UTF-8");
		msg.setSentDate(new Date());

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverAddresses, false));

		System.out.println("Versende E-Mail...");
		Transport.send(msg);
		System.out.println("E-Mail versendet.");
	}

	public void sendAppointmentRequest(Appointment appointment){

		String senderName=appointment.getPatient().getFirstName()+appointment.getPatient().getLastName();
		String receiverMail=appointment.getPhysician().getEmailAddress();
		String subject="Appointment request";
		String message="You have a new appointment request on : "+appointment.getDate();
		Mail newMsg = new Mail();
		newMsg.login();
		try {

			newMsg.send("amaramkooooo@gmail.com",senderName,receiverMail, subject,message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	public void sendAlternativeMail(Appointment appointment){

		String senderName=appointment.getPhysician().getFirstName()+appointment.getPhysician().getLastName();
		String receiverMail=appointment.getPatient().getEmailAddress();
		String subject="Appointment reschedule request";
		String message="Due to some unforeseen circumstances , we are afraid that we will not be able to keep the appointment we made for the"+appointment.getDate()+" . Kindly reschedule the appointment to (Insert date from Texfield phyisician GUI here). I am really sorry for the inconvenience it may cause you. Let me know if the new date and time are suitable for your schedule.";

		Mail newMsg = new Mail();
		newMsg.login();

		try {

			newMsg.send("amaramkooooo@gmail.com",senderName,receiverMail, subject,message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void sendAppointmentConfirm(Appointment appointment){
		String senderName=appointment.getPhysician().getFirstName()+appointment.getPhysician().getLastName();
		String receiverMail=appointment.getPatient().getEmailAddress();
		String subject="Appointment confirmation";
		String message="Your appointment has been successfully confirmed ! We are looking forward to meeting you on : "+appointment.getDate();
		Mail newMsg = new Mail();
		newMsg.login();

		try {

			newMsg.send("amaramkooooo@gmail.com",senderName,receiverMail, subject,message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


    
    
    

