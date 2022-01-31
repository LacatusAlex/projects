package models;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmailService {

    private static EmailValidator emailValidator =new EmailValidator();
    private static EmailRepository emailRepository=emailValidator.getEmailRepository();
    public static boolean send(Email email)  {
        try {
            emailValidator.validate(email);
        }
        catch (InvalidMailException e){
            System.out.println("Invalid email");
        }
        email.setTime(LocalDateTime.now());
        if(emailRepository.save(email)!=null){
            return true;
        }
        return false;
    }

    public static ArrayList<Email> findAllByUser(User user,boolean userKind ,int ordered){


        ArrayList<Email> emails=new ArrayList<>();
        if(!userKind){
            for(Email email:emailRepository.getList()){
              if(email.getToUser()==user || email.getFromUser()==user){
                 emails.add(email);
             }
            }
        }
        else {
            for(Email email:emailRepository.getList()){
                if(email.getToUser()==user ){
                    emails.add(email);
                }
            }

        }


        if(ordered==0) return emails;
        Collections.sort(emails);
        ArrayList<Email> emailsReverse= new ArrayList<>();
        for(int i=emails.size()-1;i>=0;i--){
            emailsReverse.add(emails.get(i));
        }
        if(ordered==2){
            return emailsReverse;
        }
        return emails;
    }

    public static String cancel(Email email){
        if(email!=null && emailRepository.getList().contains(email)){
            if(email.getTime().isAfter(LocalDateTime.now().minusSeconds(30))){
                emailRepository.getList().remove(email);
                return "Email successfully canceled";
            }
            return "Email cannot be canceled anymore";
        }
        return "Email does not exist";
    }

    public static EmailValidator getEmailValidator() {
        return emailValidator;
    }

    public static void setEmailValidator(EmailValidator emailValidator) {
        EmailService.emailValidator = emailValidator;
    }

    public static EmailRepository getEmailRepository() {
        return emailRepository;
    }

    public static void setEmailRepository(EmailRepository emailRepository) {
        EmailService.emailRepository = emailRepository;
    }
}
