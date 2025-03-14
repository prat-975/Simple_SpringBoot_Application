package io.aiven.spring.mysql.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/demo")
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/add")
    public @ResponseBody ResponseEntity<?> addUser(@RequestParam String name, @RequestParam String email) {
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Name cannot be empty");
        }
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Email cannot be empty");
        }

        User springUser = new User();
        springUser.setName(name);
        springUser.setEmail(email);
        userRepository.save(springUser);
        return ResponseEntity.ok(springUser);
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody ResponseEntity<?> deleteUser(@RequestParam Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User with ID " + id + " deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Error: User not found.");
        }
    }

    @GetMapping(path = "/getUser")
    public @ResponseBody ResponseEntity<?> getUserById(@RequestParam Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(u -> ResponseEntity.ok("Name: " + u.getName() + ", Email: " + u.getEmail()))
                .orElse(ResponseEntity.badRequest().body("Error: User not found."));
    }
}
