package models;

import java.util.ArrayList;

public class ConversationRepository implements AppRepository<Conversation,Long>{
    private ArrayList<Conversation> list =new ArrayList<>();
    private Long id=1L;
    @Override
    public Conversation save(Conversation entity) throws  InvalidConversationException {
        if(UserService.getUserRepository().getList().contains(entity.getToUser()) &&
                UserService.getUserRepository().getList().contains(entity.getFromUser())
                &&entity.getFromUser()!= entity.getToUser()){

            for(int i=0;i<list.size();i++){
                if(list.get(i).getToUser()==entity.getFromUser() && list.get(i).getFromUser()==entity.getToUser()){
                    throw new InvalidConversationException();
                }
            }
            list.add(entity);
            entity.setId(id);
            id++;
            return entity;
        }
        throw new InvalidConversationException();
    }

    @Override
    public Conversation findById(Long id)  {
        for(int i=0;i<list.size();i++){
            if(list.get(i).getId()==id){
                return list.get(i);
            }

        }

        return null;
    }


    @Override
    public Conversation delete(Conversation entity) {
        if(list.contains(entity)){
            list.remove(entity);
            return entity;
        }
        return null;

    }

    public ArrayList<Conversation> getList() {
        return list;
    }

    public void setList(ArrayList<Conversation> list) {
        this.list = list;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
