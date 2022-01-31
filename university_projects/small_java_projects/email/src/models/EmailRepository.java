package models;

import java.util.ArrayList;

public class EmailRepository implements AppRepository<Email,Long>{
    private ArrayList<Email> list=new ArrayList<>();
    private Long id=1L;
    @Override
    public Email save(Email entity)  {

        entity.setId(id);
        id++;
        list.add(entity);
        return entity;
    }

    @Override
    public Email findById(Long id) {
        for(Email email:list){
            if(email.getId()==id){
                return email;

            }
        }
        return null;
    }

    @Override
    public Email delete(Email entity) {
        if (entity != null) {
            if (list.contains(entity)) {
                list.remove(entity);
                return entity;
            }
            return null;
        }
        return null;
    }

    public ArrayList<Email> getList() {
        return list;
    }

    public void setList(ArrayList<Email> list) {
        this.list = list;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
