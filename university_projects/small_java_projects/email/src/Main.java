import models.*;

import javax.imageio.IIOException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner myObj = new Scanner(System.in);


        //int x=myObj.nextInt();
        System.out.println("You have the following commands:");
        System.out.println("1. cu <user_name> <email_address>    //creates user");
        System.out.println("2. ce <userFrom_name> <userTo_name> <subject> <message>   //create and send an email");
        System.out.println("3. fe <user>   //find all emails involving the user");
        System.out.println("4. du <user>   //deletes the user");
        System.out.println("5. ce <subject>   //cancels an email if it was sent in the last 30 seconds");


        String com=myObj.next();
        while(com!=null ){
            if(com.equals("cu")){
                String name=myObj.next();
                String mail=myObj.next();
                UserService.register(new User(name,mail));
                System.out.println("User added");
            }
            else {
                if(com.equals("ce")){
                    String u1=myObj.next();
                    String u2=myObj.next();
                    String subject=myObj.next();
                    String message=myObj.next();
                    User user1=new User();
                    User user2= new User();
                    for(User user:UserService.getUserrepository().getList()){
                        if(user.getName().equals(u1)){
                            user1=user;
                        }
                        if(user.getName().equals(u2)){
                            user2=user;
                        }
                    }
                    EmailService.send(new Email(user1,user2,subject,message));
                    System.out.println("Email added");
                }
                else if(com.equals("fe")){
                    String u=myObj.next();
                    User userr= new User();
                    for(User user:UserService.getUserrepository().getList()){
                        if(user.getName().equals(u)){
                            userr=user;
                        }

                    }
                    ArrayList<Email> emails = EmailService.findAllByUser(userr,true,0);
                    for(Email email:emails){
                        System.out.println("From: "+ email.getFromUser().getName()+"   To:" +email.getToUser().getName() +"  Subject: "+email.getSubject()+"\n  Message: "+email.getMessage()+"\n");
                    }

                }
                else if(com.equals("du")){
                    String u=myObj.next();
                    User userr= new User();
                    for(User user:UserService.getUserrepository().getList()){
                        if(user.getName().equals(u)){
                            userr=user;
                        }

                    }
                    UserService.getUserrepository().delete(userr);

                }
                else if(com.equals("ce")){
                    String subject=myObj.next();
                    Email e=new Email();
                    for(Email email:EmailService.getEmailRepository().getList()){
                        if(email.getSubject().equals(subject)){
                            e=email;
                        }
                    }
                    EmailService.cancel(e);
                }
            }
            com=myObj.next();

        }
















    }
}
