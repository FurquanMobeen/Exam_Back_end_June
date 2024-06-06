package be.ucll.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import be.ucll.model.User;
import be.ucll.service.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class UserTest {
    
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void givenUsers_whenUserIsTrue_thenUserIsReturned() {
        User user = Mockito.mock(User.class);
        when(user.getName()).thenReturn("Test1");
        when(user.getAge()).thenReturn(5);
        when(user.getEmail()).thenReturn("test1@ucll.be");

        assertEquals("Test1", user.getName());
        assertEquals(5, user.getAge());
        assertEquals("test1@ucll.be", user.getEmail());
    }

    @Test
    public void givenUsers_whenNameIsEmpty_thenValidationFails() {
        User user = new User("", 56, "john@ucll.be");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
        assertEquals("User name is required.", violations.iterator().next().getMessage());
    }

    @Test
    public void givenUsers_whenAgeIsLessThen1_thenValidationFails() {
        User user = new User("John Doe", -56, "john@ucll.be");
        user.setAge(-56);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
        assertEquals("User age must be between 0 and 140 (including).", violations.iterator().next().getMessage());
    }

    @Test
    public void givenUsers_whenAgeIsMoreThen140_thenValidationFails() {
        User user = new User("John Doe", 141, "john@ucll.be");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
        assertEquals("User age must be between 0 and 140 (including).", violations.iterator().next().getMessage());
    }

    @Test
    public void givenUsers_whenInvalidEmailFormat_thenValidationFAils(){
        User user = new User("John Doe", 56, "john1234");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
        assertEquals("Email must be a valid email format.", violations.iterator().next().getMessage());
    }
}

