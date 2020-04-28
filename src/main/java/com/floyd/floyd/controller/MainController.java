package com.floyd.floyd.controller;

import com.floyd.floyd.domain.Message;
import com.floyd.floyd.domain.User;
import com.floyd.floyd.domain.dto.TimeResponseDto;
import com.floyd.floyd.repos.MessageRepo;
import com.floyd.floyd.service.MailSender;
import com.floyd.floyd.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
public class MainController {
    private final static String TIME_URL= "http://worldtimeapi.org/api/timezone/Asia/Novosibirsk";
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private RestTemplate restTemplate;
@Autowired
private MailSender mailSender;
@Autowired
private UrlService urlService;

    @GetMapping("/test")
    public  String test(Model model){
        model.addAttribute("num", 15);

        return "test";
    }

    @PostMapping("/test")
    public  String postTest(Map<String, Object> model){


        return "test";
    }


    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter,  @AuthenticationPrincipal User user, Model model) {
        Iterable<Message> messages ;


        if (filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }




        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "main";
    }

    @GetMapping("/main/from")
    public String mainFrom(@RequestParam(required = false, defaultValue = "")  String time, Model model) {
        Iterable<Message> messages = messageRepo.findAll();

        if (time != null && !time.isEmpty()) {
            messages = messageRepo.findByLocalDateTimeAfter( LocalDateTime.parse(time.substring(0, 19)));
        } else {
            messages = messageRepo.findAll();
        }



        model.addAttribute("messages", messages);
        model.addAttribute("time", time);

        return "main";
    }
    @PostMapping("/main/send")
    public  String send(
            @AuthenticationPrincipal User user, Model model, String time
    ){

        Iterable<Message> messages = messageRepo.findAll();

        if (time != null && !time.isEmpty()) {
            messages = messageRepo.findByLocalDateTimeAfter( LocalDateTime.parse(time.substring(0, 19)));
        } else {
            messages = messageRepo.findAll();
        }

        model.addAttribute("messages", messages);
        model.addAttribute("time", time);


return "main";
    }
    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Message message,  BindingResult bindingResult, Model model

    ) throws IOException {
        TimeResponseDto response = restTemplate.getForObject(TIME_URL, TimeResponseDto.class);

        message.setAuthor(user);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {



            message.setLocalDateTime(    LocalDateTime.parse( response.getTime() )    );
            message.setAdded(true);
            message.setHtml(urlService.getHtml(message.getText()));
            messageRepo.save(message);
        }
model.addAttribute("message", null);
        Iterable<Message> messages = messageRepo.findAll();

        model.addAttribute("messages", messages);

        return "main";
    }
    @GetMapping("/main/del/{id}")
            public String del(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ){


          messageRepo.deleteById(id);


        return "redirect:/main";
    }

    @GetMapping("/main/getNews")
    public String getNews( @AuthenticationPrincipal User user){
        user.setGetNews(! (user.isGetNews()));
        return "redirect:/main";

    }
}