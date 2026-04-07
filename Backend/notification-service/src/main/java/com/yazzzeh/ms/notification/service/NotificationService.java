package com.yazzzeh.ms.notification.service;

import com.yazzzeh.ms.notification.event.WorkOrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    @Autowired
    private JavaMailSender javaMailSender;


    @KafkaListener(topics = "workorder-placed")
    public void listen(WorkOrderCreatedEvent workOrderCreatedEvent){
        log.info("Got Message from order-placed topic {}", workOrderCreatedEvent);
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("marsamaroc@email.com");
            messageHelper.setTo(workOrderCreatedEvent.getTechnicianEmail().toString());
            messageHelper.setSubject(String.format("Your Order with OrderNumber %s is placed successfully", workOrderCreatedEvent.getWorkOrderNumber()));
            messageHelper.setText(String.format("""
                            Hi %s,%s

                            Your order with order number %s is now placed successfully.
                            
                            Best Regards
                            Spring Shop
                            """,
                    workOrderCreatedEvent.getWorkOrderNumber()));
        };
        try {
            javaMailSender.send(messagePreparator);
            log.info("Order Notifcation email sent!!");
        } catch (MailException e) {
            log.error("Exception occurred when sending mail", e);
            throw new RuntimeException("Exception occurred when sending mail to springshop@email.com", e);
        }

    }
}
