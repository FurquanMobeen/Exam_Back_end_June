package be.ucll.model;

import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Table(name = "users")
public class User {

    @NotEmpty(message = "User name is required.")
    private String name;

    @Min(value = 1, message = "User age must be between 0 and 140 (including).")
    @Max(value = 140, message = "User age must be between 0 and 140 (including).")
    private int age;

    @Email(message = "Email must be a valid email format.")
    private String email;

    public User(String name, int age, String email) {
        setName(name);
        setAge(age);
        setEmail(email);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        // if (name == null || name.isBlank()) {
        //     throw new DomainException("User name is required.");
        // }
        this.name = name;
    }

    public void setAge(int age) {
        // if (age < 0 || age > 140) {
        //     throw new DomainException("User age must be between 0 and 140 (including).");
        // }
        this.age = age;
    }

    public void setEmail(String email) {
        if (this.email != null && !this.email.equals(email)) {
            throw new DomainException("User email cannot be changed.");
        }
        this.email = email;
    }
}
