package com.codeup.plantapp.services;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import com.mailgun.model.message.Message;
import com.mailgun.model.message.MessageResponse;

public class EmailService {

    public static MessageResponse sendSimpleMessage(String key, String userMail) {
        MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(key)
                .createApi(MailgunMessagesApi.class);

        Message message = Message.builder()
                .from("botanibuddy@gmail.com")
                .to(userMail)
                .subject("myfirstemail")
                .text("testing an email")
                .build();

        return mailgunMessagesApi.sendMessage("sandbox6a03e7e77c1544a6a86cde30a21314df.mailgun.org", message);
    }


}