package be.ucll.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import be.ucll.model.User;
import be.ucll.repository.UserRepository;
import be.ucll.service.ServiceException;
import be.ucll.service.UserService;

public class UserServiceTest {
    
    @Test
    public void givenUserInTheDatabase_whenGetAllUsers_thenAllUsersAreReturned() {

        UserRepository mockRepository = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(mockRepository);
        User user1 = new User("Test1", 5, "test1@ucll.be");
        User user2 = new User("Test2", 10, "test2@ucll.be");
        List<User> expectedUsers = Arrays.asList(user1, user2);
        when(mockRepository.getAllUsers()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.getAllUsers();

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void givenUserInTheDatabase_whenUserDoesNotExist_thenUserIsAdded() {
        UserRepository mockRepo = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(mockRepo);
        User user = new User("Test1", 5, "test1@ucll.be");
        when(mockRepo.getUserByEmail(user.getEmail())).thenReturn(null);

        User result = userService.addUser(user);

        assertEquals(user, result);
    }

    @Test
    public void givenUserInTheDatabase_whenUserDoesExist_thenExceptionIsThrown() {
        UserRepository mockRepo = Mockito.mock(UserRepository.class);
        UserService userService = new UserService(mockRepo);
        User user = new User("Test1", 5, "test1@ucll.be");
        when(mockRepo.getUserByEmail(user.getEmail())).thenReturn(user);

        Exception userExists = assertThrows(
            ServiceException.class, () -> userService.addUser(user)
        );

        assertEquals("User already exists.", userExists.getMessage());
    }
}
