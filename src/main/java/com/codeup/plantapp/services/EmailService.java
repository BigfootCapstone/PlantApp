package com.codeup.plantapp.services;

import java.io.IOException;
import java.util.List;

import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.codeup.plantapp.models.GardenPlant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public static void sendSimpleMessage(String accessKey, String secretKey, String userEmail,
                                         List<GardenPlant> userPlants) throws IOException {
        // Replace sender@example.com with your "From" address.
        // This address must be verified with Amazon SES.
        String FROM = "botanibuddy@gmail.com";

        // Replace recipient@example.com with a "To" address. If your account
        // is still in the sandbox, this address must be verified.
//        String TO = userEmail;

        // The configuration set to use for this email. If you do not want to use a
        // configuration set, comment the following variable and the
        // .withConfigurationSetName(CONFIGSET); argument below.
        String CONFIGSET = "botanibuddy-default";

        // The subject line for the email.
        String SUBJECT = "Amazon SES test (AWS SDK for Java)";

        // The HTML body for the email.
        StringBuilder HTMLBODY =
                new StringBuilder("<h1>Hello from BotaniBuddy,</h1>"
                        + "Your plant(s) are thirsty!");

        for (GardenPlant plant : userPlants) {
            String name = plant.getPlant().getName();
            String water = plant.getWater_tip();
            String plantNotify = "<p>" + name + water + "</p>";
            HTMLBODY.append(plantNotify);
        }


        // The email body for recipients with non-HTML email clients.
        String TEXTBODY = "This email was sent through Amazon SES "
                + "using the AWS SDK for Java.";

        try {
            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                    .withRegion(Regions.US_EAST_2)
                    .build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(new Destination().withToAddresses(userEmail))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(HTMLBODY.toString()))
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(TEXTBODY)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(SUBJECT)))
                    .withSource(FROM)
                    // Comment or remove the next line if you are not using a
                    // configuration set
                    .withConfigurationSetName(CONFIGSET);
            client.sendEmail(request);
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("The email was not sent. Error message: " + ex.getMessage());
        }
    }
}