package models;

import java.util.ArrayList;

public class UserRepository implements AppRepository<User,Long>{

    private ArrayList<User> list =new ArrayList<>();
    private Long id=1L;
    @Override
    public User save(User entity) throws InvalidUserException {
        for(User user:list){
            if(user.getEmail().equals(entity.getEmail())){
                throw new InvalidUserException();

            }
        }
        entity.setId(id);
        id++;
        list.add(entity);
        return entity;
    }

    @Override
    public User findById(Long id) {
        for(User user:list){
            if(user.getId()==id){
                return user;

            }
        }
        return null;
    }

    @Override
    public User delete(User entity) {
        if (entity != null) {
            if (list.contains(entity)) {
                list.remove(entity);
                return entity;
            }
            return null;
        }
        return null;
    }

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
}
