package be.ucll.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import be.ucll.model.User;
import jakarta.annotation.PostConstruct;

@Component
public class DbInitializer {

    @Autowired
    private UserRepository userRepository;

    public DbInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void initialize() {

        User user1 = new User("John Doe", 25, "john.doe@ucll.be");
        User user2 = new User("Jane Toe", 30, "jane.toe@ucll.be");
        User user3 = new User("Jane Toe", 30, "jane.koe@ucll.be");


        userRepository.addUser(user1);
        userRepository.addUser(user2);
        userRepository.addUser(user3);
    }
}
