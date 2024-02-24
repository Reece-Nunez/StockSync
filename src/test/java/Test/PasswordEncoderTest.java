package Test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "Kelsie";

        // The encoded password from your database
        String encodedPasswordFromDatabase = "$2a$10$AXspAuOxjGfQ9kBWbRDsNOH11oJ67CvscMzUzNukq.dyB/yTfZkHS"; // Replace with the actual stored password

        // Check if the raw password, when encoded, matches the stored encoded password
        if (encoder.matches(rawPassword, encodedPasswordFromDatabase)) {
            System.out.println("Password matches.");
        } else {
            System.out.println("Password does not match.");
        }
    }
}
