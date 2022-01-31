package models;

public class UserService {
    private static UserRepository userRepository=new UserRepository();
    public static User register(User user){
        try {
            userRepository.save(user);
        }
        catch (UsedEmailException e){
            System.out.println("email already used");
            return null;
        }
        return user;
    }

    public static UserRepository getUserRepository() {
        return userRepository;
    }

    public static void setUserRepository(UserRepository userRepository) {
        UserService.userRepository = userRepository;
    }

}
