package models;

public class EmailValidator {
    private  EmailRepository emailRepository=new EmailRepository();

    public  void validate(Email email) throws InvalidMailException {
        if(!UserService.getUserrepository().getList().contains(email.getToUser()) || !UserService.getUserrepository().getList().contains(email.getFromUser())
        ||email.getMessage().equals("") ||email.getSubject().equals("")){
            throw new InvalidMailException();
        }
    }

    public  EmailRepository getEmailRepository() {
        return emailRepository;
    }

    public  void setEmailRepository(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }
}
