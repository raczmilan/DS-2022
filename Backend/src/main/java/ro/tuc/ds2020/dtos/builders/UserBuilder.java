package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.UserDetailsDTO;
import ro.tuc.ds2020.entities.User;

public class UserBuilder {
    private UserBuilder() {
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getRole(), user.getPersonId());
    }

    public static User toEntity(UserDetailsDTO userDTO) {
        return new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getRole(), userDTO.getPersonId());
    }

    public static User toEntity(UserDTO userDTO) {
        return new User(userDTO.getId(), userDTO.getUsername(), userDTO.getPassword(), userDTO.getRole(), userDTO.getPersonId());
    }
}
