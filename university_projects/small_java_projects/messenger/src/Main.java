import models.*;

import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){


        Scanner sc=new Scanner(System.in);


        System.out.println("You have the following commands:");
        System.out.println("1. cu <user_name> <password> <email_address>    //creates user");
        System.out.println("2. cc <userFrom_name> <userTo_name>    //create a conversation");
        System.out.println("3. sm <conv_id> <userFrom> <message>   //sends a message in a conversation already created");
        System.out.println("4. fc <user>   //finds all the conversations involving the user");
        System.out.println("5. fm <conv_id>   //shows all messages from a conversation");
        System.out.println("6. du <user>   //deletes user");
        System.out.println("7. dm <conv_id> <message to delete>   //deletes message");


        String com=sc.next();
        while(com!=null){

            if(com.equals("cu")){
                String name=sc.next();
                String password=sc.next();
                String mail=sc.next();
                UserService.register(new User(name,password,mail));
                System.out.println("User added");
            }
            else if(com.equals("cc")){
                String u1= sc.next();
                String u2= sc.next();
                User user1=new User();
                User user2=new User();

                for(User user:UserService.getUserRepository().getList()){
                    if(user.getName().equals(u1)){
                        user1=user;
                    }
                    if(user.getName().equals(u2)){
                        user2=user;
                    }
                }
                try {
                    Conversation conversation=new Conversation(user2, user1, LocalDate.now());
                    ConversationService.getConversationRepository().save(conversation);
                    System.out.println("conv added,conv id: " +conversation.getId());
                }
                catch (InvalidConversationException e){
                    System.out.println("invalid conv");
                }


            }
            else if(com.equals("sm")){
                int conv=sc.nextInt();
                String sender=sc.next();
                String message= sc.next();

                User userr =new User();
                Conversation conver=new Conversation();
                for(User user:UserService.getUserRepository().getList()){
                    if(user.getName().equals(sender)){
                        userr=user;
                    }

                }
                for(Conversation conversation:ConversationService.getConversationRepository().getList()){
                    if(conversation.getId().intValue()==conv){
                        conver=conversation;
                    }
                }
                ConversationService.send(conver,new Message(message,userr));
                System.out.println("message sent");



            }
            else if(com.equals("fc")){

                String sender=sc.next();//can also be receiver
                User userr =new User();

                for(User user:UserService.getUserRepository().getList()){
                    if(user.getName().equals(sender)){
                        userr=user;
                    }

                }
                System.out.println("Conversations:");
                ArrayList<Conversation> list = ConversationService.findAllByUser(userr);
                for(int i=0;i<list.size();i++){
                    System.out.println("ID:"+list.get(i).getId()+"  To: "+ list.get(i).getToUser().getName()+ " From: "+list.get(i).getFromUser().getName()+ " " + " date: "+list.get(i).getDate());
                }


            }
            else if(com.equals("fm")){
                int c= sc.nextInt();
                Conversation conver=new Conversation();
                for(Conversation conversation:ConversationService.getConversationRepository().getList()){
                    if(conversation.getId().intValue()==c){
                        conver=conversation;
                    }
                }
                System.out.println("Messages:");
                for(Message msg:conver.getMessage()){
                    System.out.println("Sender: "+msg.getSender().getName()+" Message: "+msg.getMessage());
                }

            }
            else if(com.equals("du")){

                String u=sc.next();
                User userr =new User();

                for(User user:UserService.getUserRepository().getList()){
                    if(user.getName().equals(u)){
                        userr=user;
                    }

                }
                UserService.getUserRepository().delete(userr);
                System.out.println("user deleted");


            }
            else if(com.equals("dm")){

                int c=sc.nextInt();
                String m=sc.next();

                Conversation conver=new Conversation();
                Message messag=new Message();
                for(Conversation conversation:ConversationService.getConversationRepository().getList()){
                    if(conversation.getId().intValue()==c){
                        conver=conversation;
                    }
                }

                for(Message message:conver.getMessage()){
                    if(message.getMessage().equals(m)){
                        messag=message;
                    }
                }
                System.out.println( ConversationService.delete(conver,messag));

            }
            com= sc.next();




        }







    }
}
