package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.UserDeleteDTO;
import ro.tuc.ds2020.dtos.UserDetailsDTO;
import ro.tuc.ds2020.services.UserService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getPersons() {
        List<UserDTO> dtos = userService.findUsers();
        for (UserDTO dto : dtos) {
            Link userLink = linkTo(methodOn(UserController.class)
                    .getUser(dto.getId())).withRel("userDetails");
            dto.add(userLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Integer> login(@Valid @RequestBody UserDTO userDTO) {

        String role = userService.findUserRole(userDTO);
        System.out.println(role);
        if(role != null) {
            if(role.equals("admin")){
                return new ResponseEntity<>(1, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(2, HttpStatus.OK);
            }
        }
        else {
            System.out.println("else");
            return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/users/insert")
    public ResponseEntity<Integer> insertUser(@Valid @RequestBody UserDetailsDTO userDTO) {
        Integer userID = userService.insert(userDTO);
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }

    @PostMapping("/users/delete")
    public ResponseEntity<Integer> deleteUser(@Valid @RequestBody UserDeleteDTO userDTO) {
        Integer userID = userService.delete(userDTO);
        if(userID == -1){
            return new ResponseEntity<>(userID, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }

    @PostMapping("/users/update")
    public ResponseEntity<Integer> updateUser(@Valid @RequestBody UserDTO userDTO) {
        Integer userID = userService.update(userDTO);
        if(userID == -1){
            return new ResponseEntity<>(userID, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Integer userId) {
        UserDTO dto = userService.findUserById(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
