package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class UserDTO extends RepresentationModel<UserDTO> {
    private Integer id;
    private String username;
    private String password;
    private String role;
    private Integer personId;

    public UserDTO() {
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO(String username, String password, String role, Integer personId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.personId = personId;
    }

    public UserDTO(Integer id, String username, String password, String role, Integer personId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.personId = personId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return personId == userDTO.personId && Objects.equals(username, userDTO.username)
                && Objects.equals(password, userDTO.password) && Objects.equals(role, userDTO.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, role, personId);
    }
}
