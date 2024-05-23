package mike.nl.Eindopdracht.controllers;

import mike.nl.Eindopdracht.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private Long currentId = 1l;
    private List<User> userList = new ArrayList<User>();

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setId(currentId++);
        userList.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        var optionalUser = findUserById(id);
        if (optionalUser==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(optionalUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userList);
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        var existingUser = findUserById(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        existingUser.setAddress(user.getAddress());
        existingUser.setName(user.getName());
        return ResponseEntity.ok(existingUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        var optionalUser = findUserById(id);
        if (optionalUser == null) {
            return ResponseEntity.notFound().build();
        }
        userList.remove(optionalUser);
        return ResponseEntity.noContent().build();
    }

    private User findUserById(Long id) {
        for (User user : userList) {
            if (user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }



}
