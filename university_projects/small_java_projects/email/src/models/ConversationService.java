package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ConversationService {
    private static ConversationRepository conversationRepository= new ConversationRepository();
    public static boolean send(Conversation conversation,Message message){
        if(conversation!=null &&!message.getMessage().equals("")
                && (message.getSender()==conversation.getFromUser()||message.getSender()==conversation.getToUser())){

            conversation.getMessage().add(message);
            return true;
        }
        return false;
    }
    public static String delete(Conversation conversation,Message message){
        if(conversation!=null) {
            if (conversation.getMessage().contains(message)) {
                conversation.getMessage().remove(message);
                return "Mesaj sters cu succes";

            }
            return "Mesajul nu există";
        }
        return "Conversatia nu există";
    }

    public static ArrayList<Conversation> findAllByUser(User user){
        ArrayList<Conversation> list =new ArrayList<>();
        for(int i=0;i<conversationRepository.getList().size();i++){
            if(conversationRepository.getList().get(i).getFromUser()==user || conversationRepository.getList().get(i).getToUser()==user){

                list.add(conversationRepository.getList().get(i));
            }
        }
        Collections.sort(list);
        return list;
    }

    public static ConversationRepository getConversationRepository() {
        return conversationRepository;
    }

    public static void setConversationRepository(ConversationRepository conversationRepository) {
        ConversationService.conversationRepository = conversationRepository;
    }
}
