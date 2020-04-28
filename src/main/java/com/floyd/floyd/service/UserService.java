package com.floyd.floyd.service;

import com.floyd.floyd.domain.Message;
import com.floyd.floyd.domain.Role;
import com.floyd.floyd.domain.User;
import com.floyd.floyd.repos.MessageRepo;
import com.floyd.floyd.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {




 @Autowired
    ActivationUsers activationUsers;
    @Autowired
    private UserRepo userRepo;
@Autowired
private UrlService urlService;
    @Autowired
    private MailSender mailSender;
@Autowired
private MessageRepo messageRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepo.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("User not Found");
        }
        return user;
    }
 public void addAdmin() {
     User user = userRepo.findByUsername("adm");
if(user==null) {
    User admin = new User();
    admin.setActive(true);

    admin.setPassword(passwordEncoder.encode("123"));

    admin.setUsername("adm");
    admin.setRoles(Collections.singleton(Role.ADMIN));
    admin.setEmail("admin1@gmail.com");
    userRepo.save(admin);
}


 }
    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        String code= UUID.randomUUID().toString();


        if (userFromDb != null) {
            return false;
        }

        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));

        user.setActivationCode(code);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        activationUsers.addUser(code,user);

        sendMessage(user);

        return true;
    }

    public void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sweater. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    public String changedMessages( User user){

        List<Message> messages=messageRepo.findByAuthor_Id(user.getId());

        StringBuffer text=new StringBuffer("HEHEHE");

        if(!messages.isEmpty()){

            text.append( new StringBuffer( "\n"+" Изменены:  "+ "\n") );
           String newHtml;

        for(int i=0;i<messages.size();i++){
        newHtml=urlService.getHtml(messages.get(i).getText());

        if(!newHtml.equals(  messages.get(i).getHtml()) ){
            messages.get(i).setHtml(newHtml);
         text.append(new StringBuffer(messages.get(i).getText()+"\n"));
        }

        }
        }else {
            text.append(new StringBuffer( "\n"+" Изменений нет  "+ "\n"));
        }
        String answer= text.toString();
        return answer;
    }

    public int addedMessages( ){

        List<Message> messages=messageRepo.findByAdded(true);
        for(int i=0;i<messages.size();i++) {
        messages.get(i).setAdded(false);
        }
        return messages.size();
    }


    public boolean activateUser(String code) {
        User user = activationUsers.getUser(code);

        if (user == null) {

            return false;

        }
        user.setActive(true);
        user.setActivationCode(null);

        userRepo.save(user);

        return true;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        Set<String> roles =Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());

        user.getRoles().clear();
        for(String key:form.keySet()){               // фильтруем роли?????
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }
    }

    public void updateProfile(User user, String password, String email) {

        String userEmail = user.getEmail();
        boolean isEmailChanged = email!=null&&email.equals(userEmail)|| userEmail!=null &&!userEmail.equals(email);
        if(isEmailChanged){
         user.setEmail(email);
        }
        if(StringUtils.isEmpty(email)){
            user.setActivationCode(UUID.randomUUID().toString());
        }
        if(StringUtils.isEmpty(password)){
            user.setPassword(password);
        }
        userRepo.save(user);
        if(isEmailChanged) {
            sendMessage(user);
        }
    }
}