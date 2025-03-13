package io.aiven.spring.mysql.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/demo")

public class MainController {
    @Autowired
    private  UserRepository userRepository;
    @PostMapping(path="/add")
    public  @ResponseBody  User addUser(@RequestParam String name,@RequestParam String email){
        User springUser = new User();
        springUser.setName(name);
        springUser.setEmail(email);
        userRepository.save(springUser);
        return springUser;
    }
    @GetMapping(path="/all")
    public  @ResponseBody Iterable<User> findAll(){
        return userRepository.findAll();
    }


    @DeleteMapping(path="/delete")
    public @ResponseBody String deleteUser(@RequestParam Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return "User with ID " + id + " deleted successfully.";
        } else {
            return "User not found.";
        }
    }
}

