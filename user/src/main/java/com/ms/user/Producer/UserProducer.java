package com.ms.user.Producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ms.user.dtos.EmailDto;
import com.ms.user.models.User;

@Component
public class UserProducer {
    
    final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(User user) {
        var emailDto = new EmailDto();
        emailDto.setUserId(user.getUserId());
        emailDto.setEmailTo(user.getEmail());
        emailDto.setSubject("Registration completed successfully!");
        emailDto.setText(user.getName() + ", Welcome to! \nThank you for registering, enjoy all the features of our platform now!");

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }
}
