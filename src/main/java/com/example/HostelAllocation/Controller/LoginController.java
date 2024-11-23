package com.example.HostelAllocation.Controller;

import com.example.HostelAllocation.DTO.LoginDTO;
import com.example.HostelAllocation.Entities.Student;
import com.example.HostelAllocation.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class LoginController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) throws Exception {
        String who = loginDTO.getWho();
        String username = loginDTO.getEmail();
        String password = loginDTO.getPassword();
        if (who.equals("admin")) {
            String user = "admin@iiitb.ac.in";
            String pass = "$2a$10$zl/grlqw8ERKwYoIBVDk3.0AlxyQ/NtDf8M4twiHy2tJ/3d/NknOq";
            boolean valid = passwordEncoder.matches(password, pass) && user.equals(username);
            if (valid) {
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.status(500).body("There is an Exception.");
            }
        }

        if (who.equals("student")) {
            String want = studentRepository.findStudentByEmail(username).getPass();
            boolean valid = passwordEncoder.matches(password, want);

            if (valid) {
                Student student = studentRepository.findStudentByEmail(username);
                return ResponseEntity.of(Optional.of(student));
            } else {
                return ResponseEntity.status(500).body("There is an Exception.");
            }
        }
        return ResponseEntity.status(500).build();
    }
}
