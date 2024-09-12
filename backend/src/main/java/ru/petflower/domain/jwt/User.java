package ru.petflower.domain.jwt;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Role> roles;

}
