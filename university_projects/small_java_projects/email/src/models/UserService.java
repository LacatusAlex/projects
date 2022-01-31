package models;

public class UserService {
    private static UserRepository userrepository=new UserRepository();
    public static User register(User user)  {
        try {
            userrepository.save(user);
            return user;
        }
        catch (InvalidUserException e){
            System.out.println("Invalid User");
        }
        return null;
    }

    public static UserRepository getUserrepository() {
        return userrepository;
    }

    public static void setUserrepository(UserRepository userrepository) {
        UserService.userrepository = userrepository;
    }
}
