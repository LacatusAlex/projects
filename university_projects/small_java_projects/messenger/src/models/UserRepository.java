package models;

import java.util.ArrayList;
import java.util.Objects;

public class UserRepository implements AppRepository<User,Long>{
    private ArrayList<User> list=new ArrayList<>();
    private Long id=1L;

    public ArrayList<User> getList() {
        return list;
    }

    public void setList(ArrayList<User> list) {
        this.list = list;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public User save(User entity) throws UsedEmailException {
        for(int i=0;i<list.size();i++){
            if(entity.getEmail().equals(list.get(i).getEmail())){
                throw new UsedEmailException();
            }
        }
        entity.setId(id);
        id++;
        list.add(entity);
        return entity;
    }

    @Override
    public User findById(Long id)  {
        for(int i=0;i<list.size();i++){
            if(Objects.equals(list.get(i).getId(), id)){
                return list.get(i);
            }

        }

        return null;
    }

    @Override
    public User delete(User entity) {
        if(list.contains(entity)){
            list.remove(entity);
            return entity;
        }
        return null;
    }
}
