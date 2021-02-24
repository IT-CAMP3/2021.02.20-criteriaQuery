package pl.camp.it.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.camp.it.dao.IUserDAO;
import pl.camp.it.model.User;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    IUserDAO userDAO;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        this.userDAO.persist(user);

        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = this.userDAO.getUserById(id);

        if(user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
