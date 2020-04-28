package com.floyd.floyd.service;

import com.floyd.floyd.domain.User;
import com.floyd.floyd.repos.MessageRepo;
import com.floyd.floyd.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class SchedulingService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private MailSender mailSender;

    private final static String TIME_URL = "http://worldtimeapi.org/api/timezone/Asia/Novosibirsk";
    private final static String TEST_EMAIL_RECEPIENT = "flintc4p@yandex.ru";


    /* @Scheduled(fixedRate = 20*60*1000)
     public void postTimer() {
         User user =userRepo.findByUsername("admin");
         TimeResponseDto response = restTemplate.getForObject(TIME_URL, TimeResponseDto.class);
         Message message=new Message(response.getTime(),"test", user  );
         message.setLocalDateTime(LocalDateTime.parse( response.getTime() ) );

         messageRepo.save(message);

     }*/


    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void changesSender() {
        String added = (  " За прошлые 24 часа были обавлены "+userService.addedMessages() + "\n"   );
        mailSender.send(TEST_EMAIL_RECEPIENT , " test", added);

        List<User> users = userRepo.findAllByGetNews(true);
        for (User user : users) {

            mailSender.send(user.getEmail(), " test", added);


        }

    }

    @Scheduled(fixedRate = 60 * 60 * 60 * 1000)
    public void postTimer() {
       userService.addAdmin();
        }
    }
