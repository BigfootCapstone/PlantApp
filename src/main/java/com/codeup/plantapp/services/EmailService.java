//package com.codeup.plantapp.services;
//import com.codeup.plantapp.models.posts;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.MailException;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//
//@Service("mailService")
//public class EmailService {
//
//    @Autowired
//    public JavaMailSender emailSender;
//
//    @Value("${plant.mail.from}")
//    private String from;
//
//    public void prepareAndSend(posts posts, String title, String body) {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setFrom(from);
//        msg.setTo(posts.getUser().getEmail());
//        msg.setSubject(title);
//        msg.setText(body);
//        try{
//            this.emailSender.send(msg);
//        }
//        catch (MailException ex) {
//            System.err.println(ex.getMessage());
//        }
//    }
//}