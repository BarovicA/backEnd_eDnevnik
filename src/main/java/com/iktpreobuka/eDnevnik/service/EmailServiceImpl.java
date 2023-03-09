package com.iktpreobuka.eDnevnik.service;

import java.text.SimpleDateFormat;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.MarkEntity;


@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired 
	MarkService markService;

    public void EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    } 
    
    	@Override
        public void sendMarkEmail(MarkEntity mark) {
    		try {
                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(markService.getStudentsParent(mark).getEmail());
                helper.setSubject("Nova ocena.");
                String voucherTable = createMarkTableHtml(mark);
                helper.setText(voucherTable, true);
                emailSender.send(message);
            } catch (MessagingException e) {
            	e.printStackTrace();
                throw new RuntimeException("An error occurred while sending the email", e);
            }
        }
    	@Override
        public String createMarkTableHtml(MarkEntity mark) {
    		
    		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
    	    String formattedDate = dateFormat.format(mark.getDate());

            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html>");
            sb.append("<html>");
            sb.append("<head>");
            sb.append("<title>My Table</title>");
            sb.append("<style>");
            sb.append("table.GeneratedTable {");
            sb.append("width: 101%;");
            sb.append("background-color: #ffffff;");
            sb.append("border-collapse: collapse;");
            sb.append("border-width: 2px;");
            sb.append("border-color: #ffcc00;");
            sb.append("border-style: solid;");
            sb.append("color: #000000;");
            sb.append("}");

            sb.append("table.GeneratedTable td, table.GeneratedTable th {");
            sb.append("border-width: 2px;");
            sb.append("border-color: #ffcc00;");
            sb.append("border-style: solid;");
            sb.append("padding: 3px;");
            sb.append("}");

            sb.append("table.GeneratedTable thead {");
            sb.append("background-color: #ffcc00;");
            sb.append("}");
            sb.append("</style>");
            sb.append("</head>");
            sb.append("<body>");

            sb.append("<table class=\"GeneratedTable\">");
            sb.append("<thead>");
            sb.append("<tr>");
            sb.append("<th>Ucenik</th>");
            sb.append("<th>Predmet</th>");
            sb.append("<th>OCENA</th>");
            sb.append("<th>Nastavnik</th>");
            sb.append("<th>Datum:</th>");
            sb.append("</tr>");
            sb.append("</thead>");
            sb.append("<tbody>");
            sb.append("<tr>");
            sb.append("<td>" + markService.getStudentFromMark(mark).getFirstName()
            		 + " " +" "+markService.getStudentFromMark(mark).getLastName() +"</td>");
            sb.append("<td>" + markService.getSubjectFromMark(mark).getName() + "</td>");
            sb.append("<td>"+ mark.getMarkValue().toString() +"</td>");
            sb.append("<td>"  + markService.getTeacherFromMark(mark).getFirstName() 
            		+ " " + markService.getTeacherFromMark(mark).getLastName() +"</td>");
            sb.append("<td>"+ formattedDate +"</td>");
            sb.append("</tr>");
            sb.append("</tbody>");
            sb.append("</table>");
            return sb.toString();
        }    
}
