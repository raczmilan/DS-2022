package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.UserDeleteDTO;
import ro.tuc.ds2020.dtos.UserDetailsDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserBuilder::toUserDTO)
                .collect(Collectors.toList());
    }

    public String findUserRole(UserDTO userDTO){
        User user = userRepository.findByUsername(userDTO.getUsername()).orElse(null);
        if(user != null){
            return user.getRole();
        }
        return null;
    }

    public UserDTO findUserById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            LOGGER.error("User with id {} was not found in db", id);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        }
        return UserBuilder.toUserDTO(userOptional.get());
    }

    public Integer insert(UserDetailsDTO userDTO) {
        User user = UserBuilder.toEntity(userDTO);
        user = userRepository.save(user);
        LOGGER.debug("User with id {} was inserted in db", user.getId());
        return user.getId();
    }

    public Integer delete(UserDeleteDTO userDTO){
        Integer id = userDTO.getId();
        if(id == null){
            return -1;
        }
        userRepository.deleteById(id);
        LOGGER.debug("User with id {} was deleted from db", id);
        return id;
    }

    public Integer update(UserDTO userDTO){
        User user = UserBuilder.toEntity(userDTO);
        if(user.getId() == null){
            return -1;
        }
        User userBeforeUpdate = userRepository.findById(user.getId()).orElse(null);
        if(userBeforeUpdate == null) {
            LOGGER.debug("User with id {} was not updated", user.getId());
            return -1;
        }
        if(user.getUsername().equals("")){
            user.setUsername(userBeforeUpdate.getUsername());
        }
        if(user.getPassword().equals("")){
            user.setPassword(userBeforeUpdate.getPassword());
        }
        if(user.getRole().equals("")){
            user.setRole(userBeforeUpdate.getRole());
        }
        if(user.getPersonId() == null){
            user.setPersonId(userBeforeUpdate.getPersonId());
        }
        userRepository.save(user);
        LOGGER.debug("User with id {} was updated in db", user.getId());
        return user.getId();
    }
}
