
package test;


import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
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
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	DateFormat dateFormatter2 = new SimpleDateFormat("dd-MM-yyyy");




	/***
	 *  Configuring the properties for the authentication and logging in
	 */
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

	/***
	 *  Format and prepare the email for sending
	 * @param senderMail Sender's Email address
	 * @param senderName Sender's Name
	 * @param receiverAddresses Receiver's Email address
	 * @param subject	Subject
	 * @param message   Message
	 * @throws MessagingException
	 * @throws IllegalStateException
	 * @throws UnsupportedEncodingException
	 */

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

	/*** Sending an appointment request to the Physician
	 *
	 * @param appointment  Patient appointment object
	 *
	 */


	public void sendAppointmentRequest(Appointment appointment,Date alternativeDate1,String note,Date alternativeDate2,String time1,String time2){

		String senderName=appointment.getPatient().getFirstName()+appointment.getPatient().getLastName();
		String receiverMail=appointment.getPhysician().getEmailAddress();
		String subject="Appointment request";
		String message="You have a new appointment request  on : "+dateFormatter2.format(alternativeDate1)+" "+time1+" and alternatively on : "+dateFormatter2.format(alternativeDate2)+" "+time2+"\n  Note :  "+note;
		Mail newMsg = new Mail();
		newMsg.login();
		try {

			newMsg.send("amaramkooooo@gmail.com",senderName,receiverMail, subject,message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/***
	 * Sending an alternative mail request to the Physician
	 * @param appointment  Physician appointment object
	 */

	public void sendAlternativeMail(Appointment appointment,Date alternativeDate,String note){

		String senderName=appointment.getPhysician().getFirstName()+appointment.getPhysician().getLastName();
		String receiverMail=appointment.getPatient().getEmailAddress();
		String subject="Appointment reschedule request";
		String message="Due to some unforeseen circumstances , we are afraid that we will not be able to keep the appointment we made for the "+dateFormatter.format(appointment.getDate())+" ."+
				" Kindly reschedule the appointment to "+dateFormatter2.format(alternativeDate)+ " at the same time. I am really sorry for the inconvenience it may cause you. Let me know if the new date and time are suitable for your schedule."+note;

		Mail newMsg = new Mail();
		newMsg.login();

		try {

			newMsg.send("amaramkooooo@gmail.com",senderName,receiverMail, subject,message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/***
	 * Sending an Appointment confirmation mail request to the Patient
	 *
	 * @param appointment Physician appointment object
	 */

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


    
    
    

